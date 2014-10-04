/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.security;

import info.chili.spring.SpringContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.springframework.stereotype.Component;

/**
 *
 * @author ayalamanchili
 */
@Component
public class SecurityService {

    protected HashMap<String, KeyStore> keyStores = new HashMap<String, KeyStore>();

    public PrivateKey getPrivateKey(String keyStoreName, String keyAlias, String keyPassword) {
        try {
            return (PrivateKey) keyStores.get(keyStoreName).getKey(keyAlias, keyPassword.toCharArray());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Certificate[] getCertificateChain(String keyStoreName, String certName) {
        try {
            return keyStores.get(keyStoreName).getCertificateChain(certName);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public KeyStore getKeyStore(String keyStoreName) {
        if (keyStores.containsKey(keyStoreName)) {
            return keyStores.get(keyStoreName);
        } else {
            throw new RuntimeException("no keystore loaded. please call initKeystore before the call:" + keyStoreName);
        }
    }

//Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    public void initSecurityProvider(Provider provider) {
        Security.addProvider(provider);
    }

    public void initKeyStore(String keyStoreType, String name, String keyStorePassword, String keyStoreFilePath) {
        try {
            KeyStore ks = KeyStore.getInstance(keyStoreType);
            ks.load(new FileInputStream(keyStoreFilePath), keyStorePassword.toCharArray());
            if (keyStores.containsKey(name)) {
                keyStores.remove(name);
            }
            keyStores.put(name, ks);
        } catch (Exception ex) {
            //TODO 
            ex.printStackTrace();
//            throw new RuntimeException(ex);
        }
    }

    public void createKeyStore(String keyStoreType, String storeName, String keyStorePassword) {
        try {
            KeyStore ks = KeyStore.getInstance(keyStoreType);
            ks.load(null, keyStorePassword.toCharArray());
            FileOutputStream fos = new FileOutputStream(storeName);
            ks.store(fos, keyStorePassword.toCharArray());
            fos.close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void test() {
        X509Principal issuer = new X509Principal("CN=cn, O=o, L=L, ST=il, C= c");
        X509Principal subject = new X509Principal("CN=cn, O=o, L=L, ST=il, C= c");
        String signatureAlgorithm = "SHA256WithRSAEncryption";
        String keyAlgorithm = "RSA";
        Integer keySize = 1024;
    }

    public void createAndSaveCertToKS(String keyStoreName, String keyStoreFilePath, String certAlias, Date notBefore, Date notAfter, String keyStorePW, X509Principal issuer, X509Principal subject, String signatureAlgorithm, String keyAlgorithm, Integer keySize) {
        try {
            KeyStore keyStore = getKeyStore(keyStoreName);
            Certificate existingCert = keyStore.getCertificate(certAlias);
            if (existingCert != null) {
                return;
            }
            KeyPair keyPair = generateKeyPair(keyAlgorithm, keySize);
            X509Certificate cert = generateCertificate(keyPair, issuer, subject, signatureAlgorithm, notBefore, notAfter);
            keyStore.setKeyEntry(certAlias, keyPair.getPrivate(), keyStorePW.toCharArray(), new java.security.cert.Certificate[]{cert});
            File file = new File(keyStoreFilePath);
            keyStore.store(new FileOutputStream(file), keyStorePW.toCharArray());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public X509Certificate generateCertificate(KeyPair keyPair, X509Principal issuer, X509Principal subject, String signatureAlgorithm, Date notBefore, Date notAfter) throws SecurityException, SignatureException, InvalidKeyException {
        X509V3CertificateGenerator v3CertGen = new X509V3CertificateGenerator();
        v3CertGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
        v3CertGen.setIssuerDN(issuer);
        //TODO 
        v3CertGen.setNotBefore(notBefore);
        v3CertGen.setNotAfter(notAfter);
        v3CertGen.setSubjectDN(subject);
        v3CertGen.setPublicKey(keyPair.getPublic());
        v3CertGen.setSignatureAlgorithm(signatureAlgorithm);
        return v3CertGen.generateX509Certificate(keyPair.getPrivate());
    }

    public KeyPair generateKeyPair(String algorithm, Integer keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(keySize, new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    public static SecurityService instance() {
        return SpringContext.getBean(SecurityService.class);
    }
}
