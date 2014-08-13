/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.commons;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.FdfReader;
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
import java.io.FileOutputStream;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 *
 * @author ayalamanchili
 */
public class PDFUtils {

    public void signPdf(byte[] pdfIn, String keyStoreName, String certName, String pkAlias, String keyPassword) {
        try {
            PdfReader reader = new FdfReader(pdfIn);
            FileOutputStream os = new FileOutputStream("signed.pdf");
            PdfStamper.createSignature(reader, os, '\0');
            PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0');
            PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
            appearance.setReason("I've written this.");
            appearance.setLocation("Foobar");
            appearance.setVisibleSignature(new Rectangle(100, 100, 200, 200), 1, "first");
            ExternalSignature es = new PrivateKeySignature(SecurityService.instance().getPrivateKey(keyStoreName, pkAlias, keyPassword), "SHA-256", "BC");
            ExternalDigest digest = new BouncyCastleDigest();
            MakeSignature.signDetached(appearance, digest, es, SecurityService.instance().getCertificateChain(keyStoreName, keyStoreName), null, null, null, 0, CryptoStandard.CMS);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
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
