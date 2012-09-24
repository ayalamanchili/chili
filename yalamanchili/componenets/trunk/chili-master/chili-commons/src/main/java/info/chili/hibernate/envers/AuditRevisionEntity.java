/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.hibernate.envers;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

/**
 *
 * @author ayalamanchili
 */
@Entity
@RevisionEntity(AuditRevisionListner.class)
public class AuditRevisionEntity implements Serializable {

    @Id
    @GeneratedValue
    @RevisionNumber
    protected Long id;
    @RevisionTimestamp
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    protected Date updatedTimeStamp;
    protected String updatedUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getUpdatedTimeStamp() {
        return updatedTimeStamp;
    }

    public void setUpdatedTimeStamp(Date updatedTimeStamp) {
        this.updatedTimeStamp = updatedTimeStamp;
    }

    public String getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(String updatedUserId) {
        this.updatedUserId = updatedUserId;
    }
}
