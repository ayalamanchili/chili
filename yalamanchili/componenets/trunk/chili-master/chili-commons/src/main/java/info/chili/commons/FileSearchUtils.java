/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.commons;

import com.google.common.io.Files;
import java.io.File;
import java.io.FileInputStream;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.extractor.ExtractorFactory;

/**
 *
 * @author ayalamanchili
 */
public class FileSearchUtils {

    public static String extractFileContents(File file) {
        if (isMSDoc(file)) {
            return extractMSDocFileContents(file);
        } else if (isPdfDoc(file)) {
            return extractPDFFileContents(file);
        }
        throw new UnsupportedOperationException("unknown file format cannot extract contents:" + getFileExtension(file));
    }

    public static boolean isPdfDoc(File file) {
        if (getFileExtension(file).equalsIgnoreCase("pdf")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * return true for word, excel, and powerpoint
     *
     * @param file
     * @return
     */
    public static boolean isMSDoc(File file) {
        String ext = getFileExtension(file);
        if (ext.contains("doc") || ext.contains("xls") || ext.contains("ppt")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * extracts Microsoft based document contents
     *
     * @param file
     * @return
     */
    public static String extractMSDocFileContents(File file) {
        String content = "";
        try {
            content = ExtractorFactory.createExtractor(file).getText();
        } catch (Exception e) {
            throw new RuntimeException("error extractiong file contents", e);
        }
        return content;
    }

    public static String extractPDFFileContents(File file) {
        try {
            PDFParser parser = new PDFParser(new FileInputStream(file));
            parser.parse();
            COSDocument cd = parser.getDocument();
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(new PDDocument(cd));
        } catch (Exception e) {
            throw new RuntimeException("error extractiong PDF file contents", e);
        }
    }

    public static String getFileExtension(File file) {
        return Files.getFileExtension(file.getName());
    }
}
