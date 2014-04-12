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

    @Test
    public void testIndexDocFile() {
        File f = new File("C:\\Users\\ayalamanchili\\Desktop\\test.doc");
        System.out.println(FileSearchUtils.extractFileContents(f));
    }
}
