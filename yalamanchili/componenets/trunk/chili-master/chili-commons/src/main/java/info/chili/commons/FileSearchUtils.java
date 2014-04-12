/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.commons;

import java.io.File;
import org.apache.poi.extractor.ExtractorFactory;

/**
 *
 * @author ayalamanchili
 */
public class FileSearchUtils {

    public static String extractFileContents(File file) {
        String content = "";
        try {
            content = ExtractorFactory.createExtractor(file).getText();
        } catch (Exception e) {
            throw new RuntimeException("error extractiong file contents", e);
        }
        return content;
    }
}
