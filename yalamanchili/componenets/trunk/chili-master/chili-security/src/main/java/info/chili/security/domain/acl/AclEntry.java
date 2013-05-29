/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.security.domain.acl;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "acl_entry", uniqueConstraints = {
    @UniqueConstraint(columnNames = {
        "acl_object_identity", "ace_order"})})
//TODO this shoudl inherit org.springframework.security.acls.model.AccessControlEntry
public class AclEntry implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @ManyToOne(targetEntity = AclObjectIdentity.class)
    @JoinColumn(name = "acl_object_identity")
    private AclObjectIdentity acl_object_identity;
    @NotNull
    private Integer ace_order;
    @NotNull
    @ManyToOne(targetEntity = AclSid.class)
    @JoinColumn(name = "sid")
    private AclSid sid;
    @NotNull
    private Integer mask;
    @NotNull
    private boolean granting;
    @NotNull
    private boolean audit_success;
    @NotNull
    private boolean audit_failure;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AclObjectIdentity getAcl_object_identity() {
        return acl_object_identity;
    }

    public void setAcl_object_identity(AclObjectIdentity acl_object_identity) {
        this.acl_object_identity = acl_object_identity;
    }

    public Integer getAce_order() {
        return ace_order;
    }

    public void setAce_order(Integer ace_order) {
        this.ace_order = ace_order;
    }

    public AclSid getSid() {
        return sid;
    }

    public void setSid(AclSid sid) {
        this.sid = sid;
    }

    public Integer getMask() {
        return mask;
    }

    public void setMask(Integer mask) {
        this.mask = mask;
    }

    public boolean isGranting() {
        return granting;
    }

    public void setGranting(boolean granting) {
        this.granting = granting;
    }

    public boolean isAudit_success() {
        return audit_success;
    }

    public void setAudit_success(boolean audit_success) {
        this.audit_success = audit_success;
    }

    public boolean isAudit_failure() {
        return audit_failure;
    }

    public void setAudit_failure(boolean audit_failure) {
        this.audit_failure = audit_failure;
    }
}