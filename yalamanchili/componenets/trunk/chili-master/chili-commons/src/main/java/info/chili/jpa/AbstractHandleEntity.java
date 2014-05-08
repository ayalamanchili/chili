/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.hibernate.annotations.Index;

/**
 * this class is used for generic entities that can be associated with many
 * entities of various types. eg: Comment can be associated with Task, Ticket
 * etc
 *
 * @author ayalamanchili
 */
public abstract class AbstractHandleEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @Index(name = "TGT_ENTY_NM")
    protected String targetEntityName;
    /**
     *
     */
    @Index(name = "TGT_ENTY_ID")
    protected Long targetEntityId;

    public AbstractHandleEntity() {
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

    public static <T extends AbstractHandleEntity> T save(EntityManager em, T source, AbstractEntity target) {
        source.setTargetEntityName(target.getClass().getCanonicalName());
        source.setTargetEntityId(target.getId());
        return (T) em.merge(source);
    }

    public static <T extends AbstractEntity> T find(EntityManager em, T source, T target) {
        Query query = em.createQuery("select * from" + source.getClass().getCanonicalName() + " s where s.target.targetEntityName=:targetEntityNameParam and s.targetEntityId=:targetEntityIdParam", target.getClass());
        query.setParameter("targetEntityNameParam", target.getClass().getCanonicalName());
        query.setParameter("targetEntityIdParam", target.getId());
        if (query.getResultList().size() > 0) {
            return (T) query.getResultList().get(0);
        } else {
            return null;
        }
    }

    public static <T extends AbstractEntity> List<T> findAll(EntityManager em, T source, T target) {
        Query query = em.createQuery("select * from" + source.getClass().getCanonicalName() + " s where s.target.targetEntityName=:targetEntityNameParam and s.targetEntityId=:targetEntityIdParam", target.getClass());
        query.setParameter("targetEntityNameParam", target.getClass().getCanonicalName());
        query.setParameter("targetEntityIdParam", target.getId());
        return query.getResultList();
    }
}
