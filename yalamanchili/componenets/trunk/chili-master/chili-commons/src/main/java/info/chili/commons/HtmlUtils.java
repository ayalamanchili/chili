/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.commons;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.w3c.tidy.Tidy;

/**
 *
 * @author ayalamanchili
 */
public class HtmlUtils {

    public static String cleanData(String data) {
        if (data == null) {
            return "";
        }
        Tidy tidy = new Tidy();
        tidy.setPrintBodyOnly(true);
        tidy.setXmlOut(true);
        ByteArrayInputStream inputStream;
        try {
            inputStream = new ByteArrayInputStream(data.getBytes("UTF-8"));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            tidy.parseDOM(inputStream, outputStream);
            return outputStream.toString("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String htmlToText(String html) {
        Document doc = Jsoup.parse(html);
        return doc.body().text();
    }
}
