/**
 * System Soft Technolgies Copyright (C) 2013 ayalamanchili@sstech.mobi
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.service.jrs.types;

import info.chili.service.jrs.types.Entries;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author anuyalamanchili
 */
@XmlRootElement
@XmlType
//TODO move to commons
public class EntityAuditDataTbl implements Serializable {

    protected List<Entries> entityAuditData;

    @XmlElement
    public List<Entries> getEntityAuditData() {
        if (this.entityAuditData == null) {
            this.entityAuditData = new ArrayList<Entries>();
        }
        return entityAuditData;
    }

    public void setEntityAuditData(List<Entries> entityAuditData) {
        this.entityAuditData = entityAuditData;
    }

    public void addAuditData(Entries data) {
        getEntityAuditData().add(data);
    }
}
