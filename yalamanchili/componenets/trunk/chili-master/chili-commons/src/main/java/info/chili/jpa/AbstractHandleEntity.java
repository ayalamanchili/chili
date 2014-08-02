/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.jpa;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import org.hibernate.annotations.Index;
import org.hibernate.envers.Audited;

/**
 * this class is used for generic entities that can be associated with many
 * entities of various types. eg: Comment can be associated with Task, Ticket
 * etc
 *
 * @author ayalamanchili
 */
@Entity
@Audited
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractHandleEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @Version
    private Integer version;
    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    /**
     *
     */
    @Index(name = "TGT_ENTY_NM")
    @NotNull
    protected String targetEntityName;
    /**
     *
     */
    @Index(name = "TGT_ENTY_ID")
    @NotNull
    protected Long targetEntityId;

    public AbstractHandleEntity() {
    }

    public Long getId() {
        return id;
    }

    @XmlElement
    public void setId(Long id) {
        this.id = id;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @XmlAttribute
    public Integer getVersion() {
        return version;
    }

    public String getTargetEntityName() {
        return targetEntityName;
    }

    public void setTargetEntityName(String targetEntityName) {
        this.targetEntityName = targetEntityName;
    }

    public Long getTargetEntityId() {
        return targetEntityId;
    }

    public void setTargetEntityId(Long targetEntityId) {
        this.targetEntityId = targetEntityId;
    }
}
