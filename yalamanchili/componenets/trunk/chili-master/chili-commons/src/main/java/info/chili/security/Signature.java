/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.security;

import java.util.Calendar;

/**
 *
 * @author ayalamanchili
 */
public class Signature {

    protected String certAlias;
    protected String privateKeyAlias;
    protected String privateKeyPassword;
    protected Boolean visible = true;
    protected String signatureField;
    protected Calendar signatureDate;
    protected String contact;
    protected String location;

    public Signature(String certAlias, String privateKeyAlias, String privateKeyPassword, Boolean visible, String signatureField, Calendar signatureDate, String contact, String location) {
        this.certAlias = certAlias;
        this.visible = visible;
        this.privateKeyAlias = privateKeyAlias;
        this.privateKeyPassword = privateKeyPassword;
        this.signatureField = signatureField;
        this.signatureDate = signatureDate;
        this.contact = contact;
        this.location = location;
    }

    public String getCertAlias() {
        return certAlias;
    }

    public void setCertAlias(String certAlias) {
        this.certAlias = certAlias;
    }

    public String getPrivateKeyAlias() {
        return privateKeyAlias;
    }

    public void setPrivateKeyAlias(String privateKeyAlias) {
        this.privateKeyAlias = privateKeyAlias;
    }

    public String getPrivateKeyPassword() {
        return privateKeyPassword;
    }

    public void setPrivateKeyPassword(String privateKeyPassword) {
        this.privateKeyPassword = privateKeyPassword;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getSignatureField() {
        return signatureField;
    }

    public void setSignatureField(String signatureField) {
        this.signatureField = signatureField;
    }

    public Calendar getSignatureDate() {
        return signatureDate;
    }

    public void setSignatureDate(Calendar signatureDate) {
        this.signatureDate = signatureDate;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
