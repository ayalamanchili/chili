/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.commons.pdf;

import com.google.common.base.Strings;
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
import info.chili.commons.DateUtils;
import info.chili.security.SecurityService;
import info.chili.security.Signature;
import java.io.ByteArrayOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Date;
import java.util.Map;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 *
 * @author ayalamanchili
 */
public class PDFUtils {

    public static byte[] generatePdf(PdfDocumentData data) {
        byte[] pdfWithSignature = generatePdfWithFields(data);
        int i = 1;
        for (Signature signature : data.getSignatures()) {
            pdfWithSignature = signDocument(pdfWithSignature, data.getKeyStoreName(), signature);
        }
        ByteArrayOutputStream pdfOut = new ByteArrayOutputStream();
        try {
            PdfReader reader = new PdfReader(pdfWithSignature);
            PdfStamper stamper = new PdfStamper(reader, pdfOut);
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
        } catch (Exception e) {
        }
        return pdfOut.toByteArray();
    }

    public static byte[] signDocument(byte[] input, String keyStoreName, Signature signature) {
        ByteArrayOutputStream pdfOut = new ByteArrayOutputStream();
        try {
            SecurityService securityService = SecurityService.instance();
            KeyStore keyStore = securityService.getKeyStore(keyStoreName);
            PdfReader reader = new PdfReader(input);
            PdfStamper stamper = PdfStamper.createSignature(reader, pdfOut, '\0');
            PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
            appearance.setContact(signature.getContact());
            if (!Strings.isNullOrEmpty(signature.getLocation())) {
                appearance.setLocation(signature.getLocation());
            }
            if (signature.getSignatureDate() != null) {
                appearance.setSignDate(signature.getSignatureDate());
            } else {
                appearance.setSignDate(DateUtils.dateToCalendar(new Date()));
            }
            if (signature.getVisible()) {
                appearance.setVisibleSignature(signature.getSignatureField());
            }
            PrivateKey pk = (PrivateKey) keyStore.getKey(signature.getPrivateKeyAlias(), signature.getPrivateKeyPassword().toCharArray());
            ExternalSignature es = new PrivateKeySignature(pk, "SHA-256", "BC");
            ExternalDigest digest = new BouncyCastleDigest();
            Certificate[] chain = keyStore.getCertificateChain(signature.getCertAlias());
            MakeSignature.signDetached(appearance, digest, es, chain, null, null, null, 0, CryptoStandard.CMS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pdfOut.toByteArray();
    }

    public static byte[] generatePdfWithFields(PdfDocumentData data) {
        ByteArrayOutputStream pdfOut = new ByteArrayOutputStream();
        try {
            PdfReader pdfTemplate = new PdfReader(data.getTemplateUrl());
            PdfStamper stamper = new PdfStamper(pdfTemplate, pdfOut);
            for (Map.Entry<String, String> fieldName : data.getData().entrySet()) {
                stamper.getAcroFields().setField(fieldName.getKey(), fieldName.getValue());
            }
            stamper.close();
            pdfTemplate.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pdfOut.toByteArray();
    }

    public static byte[] signPdf(byte[] pdfIn, String keyStoreName, Signature signature) {
        try {
            SecurityService securityService = SecurityService.instance();
            KeyStore keyStore = securityService.getKeyStore(keyStoreName);
            PrivateKey pk = (PrivateKey) keyStore.getKey(signature.getPrivateKeyAlias(), signature.getPrivateKeyPassword().toCharArray());
            Certificate[] chain = keyStore.getCertificateChain(signature.getCertAlias());
            PdfReader reader = new PdfReader(pdfIn);
            ByteArrayOutputStream pdfOut = new ByteArrayOutputStream();
            PdfStamper stamper = PdfStamper.createSignature(reader, pdfOut, '\0');
            PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
            appearance.setContact(signature.getContact());
            if (!Strings.isNullOrEmpty(signature.getLocation())) {
                appearance.setLocation(signature.getLocation());
            }
            if (signature.getSignatureDate() != null) {
                appearance.setSignDate(signature.getSignatureDate());
            } else {
                appearance.setSignDate(DateUtils.dateToCalendar(new Date()));
            }
//            appearance.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC_AND_DESCRIPTION);
//            appearance.setSignatureGraphic(Image.getInstance("http://sstech.us/images/systemsoft-logo.jpg"));
            if (signature.getVisible()) {
                appearance.setVisibleSignature(new Rectangle(100, 250, 300, 200), 1, null);
            }
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

    public static byte[] convertToSignedPDF(String html, String keyStoreName, Signature... signatures) {
        byte[] pdf = convertToPDF(html);
        for (Signature signature : signatures) {
            pdf = signPdf(pdf, keyStoreName, signature);
        }
        return pdf;
    }

    public static byte[] convertToSignedPDF(String html, String keyStoreName, Signature signature) {
        return signPdf(convertToPDF(html), keyStoreName, signature);
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
