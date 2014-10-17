/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.commons.pdf;

import info.chili.security.Signature;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author ayalamanchili
 */
public class PdfDocumentData {

    /**
     *
     */
    protected String templateUrl;
    /**
     *
     */
    protected HashMap<String, String> data = new HashMap<String, String>();
    /**
     *
     */
    protected List<Signature> signatures = new ArrayList<Signature>();

    /**
     *
     */
    protected String keyStoreName;

    public PdfDocumentData() {
    }

    public String getTemplateUrl() {
        return templateUrl;
    }

    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }

    public List<Signature> getSignatures() {
        return signatures;
    }

    public void setSignatures(List<Signature> signatures) {
        this.signatures = signatures;
    }

    public String getKeyStoreName() {
        return keyStoreName;
    }

    public void setKeyStoreName(String keyStoreName) {
        this.keyStoreName = keyStoreName;
    }

}
