/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.commons;

import com.google.common.base.Strings;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.PrivateKeySignature;
import info.chili.security.SecurityService;
import java.io.ByteArrayOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Calendar;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 *
 * @author ayalamanchili
 */
public class PDFUtils {

    public static byte[] signPdf(byte[] pdfIn, String location, Calendar signatureDate, String keyStoreName, String certName, String pkAlias, String keyPassword) {
        try {
            SecurityService securityService = SecurityService.instance();
            KeyStore keyStore = securityService.getKeyStore(keyStoreName);
            PrivateKey pk = (PrivateKey) keyStore.getKey(pkAlias, keyPassword.toCharArray());
            Certificate[] chain = keyStore.getCertificateChain(certName);
            PdfReader reader = new PdfReader(pdfIn);
            ByteArrayOutputStream pdfOut = new ByteArrayOutputStream();
            PdfStamper stamper = PdfStamper.createSignature(reader, pdfOut, '\0');
            PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
            if (!Strings.isNullOrEmpty(location)) {
                appearance.setLocation(location);
            }
            if (signatureDate != null) {
                appearance.setSignDate(signatureDate);
            }
//            appearance.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC_AND_DESCRIPTION);
//            appearance.setSignatureGraphic(Image.getInstance("http://sstech.us/images/systemsoft-logo.jpg"));
            appearance.setVisibleSignature(new Rectangle(100, 250, 300, 200), 1, null);
            ExternalSignature es = new PrivateKeySignature(pk, "SHA-256", "BC");
            ExternalDigest digest = new BouncyCastleDigest();
            MakeSignature.signDetached(appearance, digest, es, chain, null, null, null, 0, CryptoStandard.CMS);
            return pdfOut.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
            return pdfIn;
            //TODO
//            throw new RuntimeException(ex);
        }
    }

    public static byte[] convertToSignedPDF(String html, String location, Calendar signatureDate, String keyStoreName, String certName, String pkAlias, String keyPassword) {
        return signPdf(convertToPDF(html), location, signatureDate, keyStoreName, certName, pkAlias, keyPassword);
    }

    public static byte[] convertToPDF(String html) {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            renderer.createPDF(baos);
            return baos.toByteArray();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
