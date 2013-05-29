/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.security.domain.acl;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "acl_class")
public class AclClass implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    
    @Column(name = "class", nullable = false, unique = true)
    private String clazz;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }
    
}