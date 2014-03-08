/**
 * System Soft Technolgies Copyright (C) 2013 ayalamanchili@sstech.mobi
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.audit;

import info.chili.commons.ReflectionUtils;
import info.chili.hibernate.envers.AuditRevisionEntity;
import info.chili.service.jrs.types.Entries;
import info.chili.service.jrs.types.Entry;
import info.chili.spring.SpringContext;
import info.chili.service.jrs.types.EntityAuditDataTbl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author anuyalamanchili
 */
@Component
@Scope("prototype")
public class AuditService {

    protected AuditReader auditReader;
    //TODO get this only on demand
    @PersistenceContext
    protected EntityManager em;

    public AuditReader getAuditReader() {
        if (auditReader == null) {
            auditReader = AuditReaderFactory.get(em);
        }
        return auditReader;
    }

    //get recent changes on a entity
    public EntityAuditDataTbl getRecentChanges(String className, Long id, List<String> ignoreFields) {
        ignoreFieldsList.addAll(ignoreFields);
        EntityAuditDataTbl table = new EntityAuditDataTbl();
        Class entityCls;
        try {
            entityCls = Class.forName(className);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Invalid Class Name", ex);
        }
        Map<String, Object> previousValuesMap = null;
        for (Number revNumber : getAuditReader().getRevisions(entityCls, id)) {
            Entries auditData = new Entries();
            AuditRevisionEntity revEntity = getAuditReader().findRevision(AuditRevisionEntity.class, revNumber);
            auditData.addEntry(new Entry("UPDATED-BY", revEntity.getUpdatedUserId()));
            auditData.addEntry(new Entry("UPDATED-AT", revEntity.getUpdatedTimeStamp().toString()));
            Object entity = getAuditReader().find(entityCls, id, revNumber);
            if (entity == null) {
                continue;
            }
            Map<String, Object> valuesMap = ReflectionUtils.getFieldsDataFromEntity(entity, entityCls, true);
            for (Map.Entry<String, Object> entry : valuesMap.entrySet()) {
                if (ignoreFields.contains(entry.getKey())) {
                    continue;
                }
                Entry e = new Entry();
                e.setId(entry.getKey());
                if (entry.getValue() != null) {
                    e.setValue(entry.getValue().toString());
                    checkForChanges(entry, e, previousValuesMap);
                } else {
                    e.setValue("");
                }
                auditData.addEntry(e);
            }
            table.addAuditData(auditData);
            previousValuesMap = valuesMap;
        }
        return table;
    }

    protected void checkForChanges(Map.Entry<String, Object> entry, info.chili.service.jrs.types.Entry e, Map<String, Object> previousValuesMap) {
        if (null != previousValuesMap) {
            if (previousValuesMap.get(entry.getKey()) == null && entry.getValue() != null) {
                highLightChanges(e);
            }
            if (previousValuesMap.get(entry.getKey()) != null && entry.getValue() == null) {
                highLightChanges(e);
            }
            if (previousValuesMap.get(entry.getKey()) != null && entry.getValue() != null && !previousValuesMap.get(entry.getKey()).toString().equals(entry.getValue().toString())) {
                highLightChanges(e);
            }
        }
    }

    //TODO add a css style ?
    protected void highLightChanges(info.chili.service.jrs.types.Entry e) {
        e.setValue("<font style=\"BACKGROUND-COLOR: yellow\">" + e.getValue() + "</font>");
    }
    protected static final List<String> ignoreFieldsList = new ArrayList<String>();

    static {
        ignoreFieldsList.add("id");
        ignoreFieldsList.add("version");
    }

    public static AuditService instance() {
        return SpringContext.getBean(AuditService.class);
    }
}
