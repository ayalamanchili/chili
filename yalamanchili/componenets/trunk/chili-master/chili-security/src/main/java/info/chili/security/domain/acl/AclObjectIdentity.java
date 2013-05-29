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
@Table(name = "acl_object_identity", uniqueConstraints = {
    @UniqueConstraint(columnNames = {
        "object_id_class", "object_id_identity"})})
public class AclObjectIdentity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @ManyToOne(targetEntity = AclClass.class)
    @JoinColumn(name = "object_id_class")
    private AclClass object_id_class;
    @NotNull
    private Long object_id_identity;
    @ManyToOne(targetEntity = AclObjectIdentity.class)
    @JoinColumn(name = "parent_object")
    private AclObjectIdentity parent_object;
    @NotNull
    @ManyToOne(targetEntity = AclSid.class)
    @JoinColumn(name = "owner_sid")
    private AclSid owner_sid;
    @NotNull
    private boolean entries_inheriting;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AclClass getObject_id_class() {
        return object_id_class;
    }

    public void setObject_id_class(AclClass object_id_class) {
        this.object_id_class = object_id_class;
    }

    public Long getObject_id_identity() {
        return object_id_identity;
    }

    public void setObject_id_identity(Long object_id_identity) {
        this.object_id_identity = object_id_identity;
    }

    public AclObjectIdentity getParent_object() {
        return parent_object;
    }

    public void setParent_object(AclObjectIdentity parent_object) {
        this.parent_object = parent_object;
    }

    public AclSid getOwner_sid() {
        return owner_sid;
    }

    public void setOwner_sid(AclSid owner_sid) {
        this.owner_sid = owner_sid;
    }

    public boolean isEntries_inheriting() {
        return entries_inheriting;
    }

    public void setEntries_inheriting(boolean entries_inheriting) {
        this.entries_inheriting = entries_inheriting;
    }
}