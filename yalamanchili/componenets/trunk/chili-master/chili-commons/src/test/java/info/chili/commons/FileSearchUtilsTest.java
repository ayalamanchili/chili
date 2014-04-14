/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.commons;

import java.io.File;
import org.junit.Test;

/**
 *
 * @author ayalamanchili
 */
public class FileSearchUtilsTest {

  //  @Test
    public void testGetFileExtension() {
        File f = new File("C:\\Users\\ayalamanchili\\Desktop\\test.pdf");
        System.out.println(FileSearchUtils.getFileExtension(f));
    }

//    @Test
    public void testIndexDocFile() {
        File f = new File("C:\\Users\\ayalamanchili\\Desktop\\test.doc");
        System.out.println(FileSearchUtils.extractMSDocFileContents(f));
    }

//    @Test
    public void testIndexPDFFile() {
        File f = new File("C:\\Users\\ayalamanchili\\Desktop\\test.pdf");
        System.out.println(FileSearchUtils.extractPDFFileContents(f));
    }

}
