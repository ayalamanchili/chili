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
public class AbstractHandleEntityDao<T extends AbstractHandleEntity> {

    private static final Log log = LogFactory.getLog(AbstractHandleEntityDao.class);
    protected Class entityCls;

    public AbstractHandleEntityDao(Class cls) {
        this.entityCls = cls;
    }

    @Validate
    public T save(EntityManager em, T source, AbstractEntity target) {
        if (target.getId() == null) {
            throw new RuntimeException("target id cannot be null");
        }
        source.setTargetEntityName(target.getClass().getCanonicalName());
        source.setTargetEntityId(target.getId());
        return (T) em.merge(source);
    }

    public T find(EntityManager em, AbstractEntity target) {
        TypedQuery<T> query = em.createQuery("from " + entityCls.getCanonicalName() + " where targetEntityName=:targetEntityNameParam and targetEntityId=:targetEntityIdParam", entityCls);
        query.setParameter("targetEntityNameParam", target.getClass().getCanonicalName());
        query.setParameter("targetEntityIdParam", target.getId());
        if (query.getResultList().size() > 0) {
            return query.getResultList().get(0);
        } else {
            //TODO throw exception
            return null;
        }
    }

    public List<T> findAll(EntityManager em, Class entityCls, AbstractEntity target) {
        TypedQuery<T> query = em.createQuery("from " + entityCls.getCanonicalName() + " where targetEntityName=:targetEntityNameParam and targetEntityId=:targetEntityIdParam", entityCls);
        query.setParameter("targetEntityNameParam", target.getClass().getCanonicalName());
        query.setParameter("targetEntityIdParam", target.getId());
        return query.getResultList();
    }

    public static AbstractHandleEntityDao instance() {
        return SpringContext.getBean(AbstractHandleEntityDao.class);
    }
}
