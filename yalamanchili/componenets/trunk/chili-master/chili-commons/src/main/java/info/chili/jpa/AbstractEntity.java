/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.jpa;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Default Parent/root entity for all jpa entities to serve id,version
 * properties and extends from LightEntity to suppport GWT-GILEAD
 *
 * @author ayalamanchili
 */
// TODO move to non gwt package since not needed with request factory
@XmlType
@MappedSuperclass
@XmlRootElement
public abstract class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Version
    private Integer version;
    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    @XmlElement
    public void setId(Long id) {
        this.id = id;
    }

    @XmlAttribute
    public Integer getVersion() {
        return version;
    }
}
