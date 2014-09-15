/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.dao;

import info.chili.jpa.AbstractEntity;
import info.chili.jpa.AbstractHandleEntity;
import info.chili.jpa.validation.Validate;
import info.chili.spring.SpringContext;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ayalamanchili
 * @param <T>
 */
@Repository
@Scope("prototype")
public abstract class AbstractHandleEntityDao<T extends AbstractHandleEntity> {

    private static final Log log = LogFactory.getLog(AbstractHandleEntityDao.class);
    protected Class entityCls;

    public AbstractHandleEntityDao(Class cls) {
        this.entityCls = cls;
    }

    @Validate
    public T save(T entity, Long id, String targetClassName) {
        entity.setTargetEntityName(targetClassName);
        entity.setTargetEntityId(id);
        //TODO use bean mapper for merge
        return (T) getEntityManager().merge(entity);
    }

    @Validate
    public T save(T entity, AbstractEntity target) {
        if (target.getId() == null) {
            throw new RuntimeException("target id cannot be null");
        }
        entity.setTargetEntityName(target.getClass().getCanonicalName());
        entity.setTargetEntityId(target.getId());
        //TODO use bean mapper for merge
        return (T) getEntityManager().merge(entity);
    }

    @Validate
    public T save(T entity, AbstractEntity source, AbstractEntity target) {
        if (source.getId() == null) {
            throw new RuntimeException("source id cannot be null");
        }
        if (target.getId() == null) {
            throw new RuntimeException("target id cannot be null");
        }
        entity.setSourceEntityName(source.getClass().getCanonicalName());
        entity.setSourceEntityId(source.getId());
        entity.setTargetEntityName(target.getClass().getCanonicalName());
        entity.setTargetEntityId(target.getId());
        //TODO use bean mapper for merge
        return (T) getEntityManager().merge(entity);
    }

    public T find(AbstractEntity target) {
        TypedQuery<T> query = getEntityManager().createQuery("from " + entityCls.getCanonicalName() + " where targetEntityName=:targetEntityNameParam and targetEntityId=:targetEntityIdParam", entityCls);
        query.setParameter("targetEntityNameParam", target.getClass().getCanonicalName());
        query.setParameter("targetEntityIdParam", target.getId());
        if (query.getResultList().size() > 0) {
            return query.getResultList().get(0);
        } else {
            //TODO throw exception
            return null;
        }
    }

    public T find(AbstractEntity source, AbstractEntity target) {
        TypedQuery<T> query = getEntityManager().createQuery("from " + entityCls.getCanonicalName() + " where sourceEntityName=:sourceEntityNameParam and sourceEntityId=:sourceEntityIdParam and targetEntityName=:targetEntityNameParam and targetEntityId=:targetEntityIdParam", entityCls);
        query.setParameter("sourceEntityNameParam", source.getClass().getCanonicalName());
        query.setParameter("sourceEntityIdParam", source.getId());
        query.setParameter("targetEntityNameParam", target.getClass().getCanonicalName());
        query.setParameter("targetEntityIdParam", target.getId());
        if (query.getResultList().size() > 0) {
            return query.getResultList().get(0);
        } else {
            //TODO throw exception
            return null;
        }
    }

    public List<T> findAll(Long id, String targetClassName) {
        TypedQuery<T> query = getEntityManager().createQuery("from " + entityCls.getCanonicalName() + " where targetEntityName=:targetEntityNameParam and targetEntityId=:targetEntityIdParam", entityCls);
        query.setParameter("targetEntityNameParam", targetClassName);
        query.setParameter("targetEntityIdParam", id);
        return query.getResultList();
    }

    public List<T> findAll(AbstractEntity source, AbstractEntity target) {
        TypedQuery<T> query = getEntityManager().createQuery("from " + entityCls.getCanonicalName() + " where sourceEntityName=:sourceEntityNameParam and sourceEntityId=:sourceEntityIdParam and targetEntityName=:targetEntityNameParam and targetEntityId=:targetEntityIdParam", entityCls);
        query.setParameter("sourceEntityNameParam", source.getClass().getCanonicalName());
        query.setParameter("sourceEntityIdParam", source.getId());
        query.setParameter("targetEntityNameParam", target.getClass().getCanonicalName());
        query.setParameter("targetEntityIdParam", target.getId());
        return query.getResultList();
    }

    public abstract EntityManager getEntityManager();

    public static AbstractHandleEntityDao instance() {
        return SpringContext.getBean(AbstractHandleEntityDao.class);
    }
}
