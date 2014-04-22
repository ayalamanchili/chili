/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.dao;

import info.chili.jpa.QueryUtils;
import info.chili.service.jrs.exception.ServiceException;
import info.chili.jpa.AbstractEntity;
import java.util.Arrays;
import info.chili.commons.BeanMapper;
import info.chili.commons.DataType;
import info.chili.commons.ReflectionUtils;
import info.chili.commons.SearchUtils;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ayalamanchili
 */
@Repository
@Scope("prototype")
public abstract class CRUDDao<T> {

    private static final Log log = LogFactory.getLog(CRUDDao.class);
    protected Class entityCls;

    public CRUDDao(Class cls) {
        this.entityCls = cls;
    }

    @Transactional(readOnly = true)
    public T findById(Long id) {
        return (T) getEntityManager().find(entityCls, id);
    }

    @Transactional(readOnly = true)
    public List<T> query(int start, int limit) {
        Query findAllQuery = getEntityManager().createQuery("from " + entityCls.getCanonicalName(), entityCls);
        findAllQuery.setFirstResult(start);
        findAllQuery.setMaxResults(limit);
        return findAllQuery.getResultList();
    }

    /**
     * we don’t want it to be enlisted in a transaction at all. The reason is
     * that we don’t need the result to be managed by the underlying JPA
     * EntityManager. Instead, we just want to get the count and forget about
     * it.
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, String> getEntityStringMapByParams(int start, int limit, String... params) {
        return QueryUtils.getEntityStringMapByParams(getEntityManager(), entityCls, start, limit, params);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public <T> List<String> getSuggestionsForName(String name, Class<?> entityCls, Integer start, Integer limit) {
        Query query = getEntityManager().createQuery(QueryUtils.getSuggestionsQueryForName(name, entityCls));
        query.setFirstResult(start);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public T save(T entity) {
        if (entity instanceof AbstractEntity) {
            if (((AbstractEntity) entity).getId() != null) {
                entity = (T) BeanMapper.merge(entity, findById(((AbstractEntity) entity).getId()));
            }
        }
        return getEntityManager().merge(entity);
    }

    public void delete(Long id) {
        delete(findById(id));
    }

    public void delete(T entity) {
        try {
            getEntityManager().remove(entity);
            getEntityManager().flush();
        } catch (javax.persistence.PersistenceException e) {
            throw new ServiceException(ServiceException.StatusCode.INVALID_REQUEST, "DELETE", "SQLError", e.getMessage());
        }
    }

    public void deleteAll(List<T> entities) {
        for (T t : entities) {
            delete(t);
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Long size() {
        Query sizeQuery = getEntityManager().createQuery("select count (*) from " + entityCls.getCanonicalName());
        return (Long) sizeQuery.getSingleResult();
    }

    @Transactional(readOnly = true)
    public List<T> search(String searchText, int start, int limit, List<String> columns, boolean useSQLSearch) {
        if (searchText.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        if (useSQLSearch) {
            return sqlSearch(searchText, start, limit, columns);
        } else {
            return hibernateSearch(searchText, start, limit);
        }
    }

    @Transactional(readOnly = true)
    public List<T> sqlSearch(String searchText, int start, int limit, List<String> columns) {
        Query searchQ = getEntityManager().createQuery(SearchUtils.getSearchQueryString(entityCls, searchText, columns));
        searchQ.setFirstResult(start);
        searchQ.setMaxResults(limit);
        return searchQ.getResultList();
    }

    @Transactional(readOnly = true)
    public List<T> hibernateSearch(String searchText, int start, int limit, String... columns) {
        FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
                .getFullTextEntityManager(getEntityManager());
        String[] fields;
        if (columns != null && columns.length > 0) {
            fields = columns;
        } else {
            fields = ReflectionUtils.getBeanProperties(entityCls, DataType.STRING);
        }
        QueryBuilder qb = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(entityCls).get();
        org.apache.lucene.search.Query luceneSearchQuery = qb
                .keyword()
                .onFields(fields)
                .matching(searchText)
                .createQuery();
        javax.persistence.Query jpaSearchQuery
                = fullTextEntityManager.createFullTextQuery(luceneSearchQuery, entityCls);
        jpaSearchQuery.setFirstResult(start);
        jpaSearchQuery.setMaxResults(limit);
        return jpaSearchQuery.getResultList();
    }

    @Transactional(readOnly = true)
    public List<T> search(T entity, int start, int limit) {
        Query searchQuery = getEntityManager().createQuery(SearchUtils.getSearchQuery(entity), entityCls);
        searchQuery.setFirstResult(start);
        searchQuery.setMaxResults(limit);
        return searchQuery.getResultList();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Long searchSize(T entity) {
        Query sizeQuery = getEntityManager().createQuery(SearchUtils.getSearchSizeQuery(entity));
        return (Long) sizeQuery.getSingleResult();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Long searchSize(String searchText, List<String> columns) {
        Query sizeQuery = getEntityManager().createQuery(SearchUtils.getSearchSizeQuery(entityCls, searchText, columns));
        return (Long) sizeQuery.getSingleResult();
    }

    public abstract EntityManager getEntityManager();
}
