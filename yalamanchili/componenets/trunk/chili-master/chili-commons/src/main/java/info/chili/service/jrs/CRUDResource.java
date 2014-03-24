/**
 * System Soft Technolgies Copyright (C) 2013 ayalamanchili@sstech.mobi
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.service.jrs;

import info.chili.service.jrs.types.Entry;
import info.chili.dao.CRUDDao;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.springframework.transaction.annotation.Propagation;

import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ayalamanchili
 */
@Produces("application/json")
@Consumes("application/json")
@Transactional
public abstract class CRUDResource<T> {

    @GET
    @Path("/{id}")
    @Transactional(readOnly = true)
    public T read(@PathParam("id") Long id) {
        return (T) getDao().findById(id);
    }

    @PUT
    public T save(T entity) {
        return (T) getDao().save(entity);
    }

    @PUT
    @Path("/delete/{id}")
    public void delete(@PathParam("id") Long id) {
        getDao().delete(id);
    }

    @GET
    @Path("/dropdown/{start}/{limit}")
    @Transactional(propagation = Propagation.NEVER)
    public List<Entry> getDropDown(@PathParam("start") int start, @PathParam("limit") int limit,
            @QueryParam("column") List<String> columns) {
        List<Entry> result = new ArrayList<Entry>();
        Map<String, String> values = getDao().getEntityStringMapByParams(start, limit, columns.toArray(new String[columns.size()]));
        for (String key : values.keySet()) {
            result.add(new Entry(key, values.get(key)));
        }
        return result;
    }

    @GET
    @Path("/size")
    @Transactional(propagation = Propagation.NEVER)
    public long size() {
        return getDao().size();
    }

    @GET
    @Path("/search/{searchText}/{start}/{limit}")
    @Transactional(propagation = Propagation.NEVER)
    public List<T> search(@PathParam("searchText") String searchText, @PathParam("start") int start,
            @PathParam("limit") int limit, @QueryParam("column") List<String> columns) {
        return getDao().search(searchText, start, limit, columns, true);
    }

    @GET
    @Path("/search_size/{searchText}")
    @Transactional(propagation = Propagation.NEVER)
    public String searchSize(@PathParam("searchText") String searchText) {
        return getDao().searchSize(searchText).toString();
    }

    @PUT
    @Path("/search/{start}/{limit}")
    @Transactional(propagation = Propagation.NEVER)
    public List<T> search(T entity, @PathParam("start") int start, @PathParam("limit") int limit) {
        return getDao().search(entity, start, limit);
    }

    @PUT
    @Path("/search_size")
    @Transactional(propagation = Propagation.NEVER)
    public String searchSize(T entity) {
        return getDao().searchSize(entity).toString();
    }

    public abstract CRUDDao getDao();
}
