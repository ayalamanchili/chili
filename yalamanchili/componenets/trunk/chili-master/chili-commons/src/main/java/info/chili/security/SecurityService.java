/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.security;

import info.chili.spring.SpringContext;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.HashMap;
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
            keyStores.put(name, ks);
        } catch (Exception ex) {
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

    public static SecurityService instance() {
        return SpringContext.getBean(SecurityService.class);
    }
}
