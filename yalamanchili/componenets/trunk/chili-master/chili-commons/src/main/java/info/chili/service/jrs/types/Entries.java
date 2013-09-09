/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.service.jrs.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author ayalamanchili
 */
@XmlRootElement
@XmlType
public class Entries {

    @XmlElement
    protected List<Entry> vars;

    public List<Entry> getEntries() {
        if (this.vars == null) {
            this.vars = new ArrayList<Entry>();
        }
        return vars;
    }

    public void setEntries(List<Entry> vars) {
        this.vars = vars;
    }

    public void addEntry(Entry entry) {
        getEntries().add(entry);
    }
}
