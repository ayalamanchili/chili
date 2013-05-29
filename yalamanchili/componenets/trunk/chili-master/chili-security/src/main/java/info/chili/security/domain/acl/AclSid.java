/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.security.domain.acl;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "acl_sid", uniqueConstraints = {
    @UniqueConstraint(columnNames = {
        "sid", "principal"})})
public class AclSid implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private boolean principal;
    @NotNull
    private String sid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}