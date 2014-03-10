/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.commons;

import java.io.ByteArrayOutputStream;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 *
 * @author ayalamanchili
 */
public class FileIOUtils {

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
