/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.service.jrs.types;

import java.io.Serializable;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author yphanikumar
 */
@XmlRootElement
@XmlType
public class Entry implements Serializable {

    @Transient
    private static final long serialVersionUID = 1L;
    protected String id;
    protected String value;

    public Entry() {
    }

    public Entry(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Entry{" + "id=" + id + ", value=" + value + '}';
    }
}
