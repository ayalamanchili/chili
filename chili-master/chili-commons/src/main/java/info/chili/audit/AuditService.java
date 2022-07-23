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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.lang.time.DateUtils;
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

    private static Logger logger = Logger.getLogger(AuditService.class.getName());

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

    public Object getVersion(Class cls, Long id, Integer version) {
       List<Number> revNumbers = getAuditReader().getRevisions(cls, id);
       logger.info("getVersion  Versionnnnnnnnnn" + revNumbers);
       if (revNumbers.size() >= 2) {
           Object o = getAuditReader().find(cls, id, revNumbers.get(revNumbers.size() - 2));
           logger.info("getVersion  Object" + o);
           return o;
       } else {
           return null;
       }
   }
//TODO is this getting the current or second recent?

    public Object mostRecentVersion(Class cls, Long id) {
        List<Number> revNumbers = getAuditReader().getRevisions(cls, id);
        logger.info("Versionnnnnnnnnn1111111" + revNumbers);
        if (revNumbers.size() > 1) {
            return getAuditReader().find(cls, id, revNumbers.get(revNumbers.size() - 2));
        } else {
            return null;
        }
    }

    public Object mostRecentVersion2(Class cls, Long id) {
       List<Number> revNumbers = getAuditReader().getRevisions(cls, id);
       logger.info("Versionnnnnnnnnn222222" + revNumbers);
       if (revNumbers.size() > 1) {
           return getAuditReader().find(cls, id, revNumbers.get(revNumbers.size() - 1));
       } else {
           return null;
       }
   }

    public Object mostRecentVersion3(Class cls, Long id) {
        List<Number> revNumbers = getAuditReader().getRevisions(cls, id);
        logger.info("Versionnnnnnnnnn33333" + revNumbers);
        if (revNumbers.size() > 0) {
            return getAuditReader().find(cls, id, revNumbers.get(revNumbers.size() - 1));
        } else {
            return null;
        }
    }

    public Object previousVersion(Class cls, Long id) {
        List<Number> revNumbers = getAuditReader().getRevisions(cls, id);
        logger.info("previousVersion" + revNumbers);
        if (revNumbers.size() >= 2) {
            return getAuditReader().find(cls, id, revNumbers.get(revNumbers.size() - 2));
        } else {
            return null;
        }
    }

    public String buildChangesTable(List<AuditChangeDto> changes) {
        StringBuilder changesTable = new StringBuilder();
        changesTable.append("<table border='2px'>");
        changesTable.append("<tr>");
        changesTable.append("<td><b>");
        changesTable.append("Field");
        changesTable.append("</td>");
        changesTable.append("<td><b>");
        changesTable.append("Old Value");
        changesTable.append("</td>");
        changesTable.append("<td><b>");
        changesTable.append("New Value");
        changesTable.append("</td>");
        changesTable.append("</tr>");
        for (AuditChangeDto dto : changes) {
            changesTable.append("<tr>");
            changesTable.append("<td>");
            changesTable.append(toLowerCase(dto.getPropertyName()));
            changesTable.append("</td>");
            changesTable.append("<td>");
            changesTable.append(dto.getOldValue());
            changesTable.append("</td>");
            changesTable.append("<td>");
            changesTable.append(dto.getNewValue());
            changesTable.append("</td>");
            changesTable.append("</tr>");
        }
        changesTable.append("</table>");
        return changesTable.toString();
    }

    public static StringBuilder toLowerCase(String inputString) {
        StringBuilder str = new StringBuilder();
        String[] tokens = inputString.split("\\s");// Can be space,comma or hyphen
        for (String token : tokens) {
            str.append(Character.toUpperCase(token.charAt(0))).append(token.substring(1)).append(" ");
        }
        str.toString().trim(); // Trim trailing space
        StringBuilder s = str;
        StringBuilder out = new StringBuilder(s);
        Pattern p = Pattern.compile("[A-Z]");
        Matcher m = p.matcher(s);
        int extraFeed = 0;
        while (m.find()) {
            if (m.start() != 0) {
                out = out.insert(m.start() + extraFeed, " ");
                extraFeed++;
            }
        }
        return out;
    }

    public List<AuditChangeDto> compare(Object previousVersion, Object currentVersion, String... ignoreFields) {
        return compare(previousVersion, currentVersion, true, ignoreFields);
    }

    public List<AuditChangeDto> compare(Object previousVersion, Object currentVersion, boolean addStyle, String... ignoreFields) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy ");
        //sdf.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
           List<AuditChangeDto> changes = new ArrayList();
        ignoreFieldsList.addAll(Arrays.asList(ignoreFields));
        if (previousVersion == null) {
            return changes;
        }
        Map<String, Object> valuesMap = ReflectionUtils.getFieldsDataFromEntity(currentVersion, currentVersion.getClass(), true);
        Map<String, Object> previousValuesMap = ReflectionUtils.getFieldsDataFromEntity(previousVersion, previousVersion.getClass(), true);
        logger.info("Current Map" + valuesMap);
        logger.info("Previous Map" + previousValuesMap);
        for (Map.Entry<String, Object> entry : valuesMap.entrySet()) {
            if (ignoreFieldsList.contains(entry.getKey())) {
                continue;
            }
            if (previousValuesMap.get(entry.getKey()) == null && entry.getValue() != null) {
                AuditChangeDto dto = new AuditChangeDto();
                dto.setPropertyName(entry.getKey());
                dto.setOldValue("");
                if (addStyle) {
                    dto.setNewValue("<font style=\"BACKGROUND-COLOR: yellow\">" + entry.getValue().toString() + "</font>");
                } else {
                    dto.setNewValue(entry.getValue().toString());
                }
                changes.add(dto);

                continue;
            }
            if (previousValuesMap.get(entry.getKey()) != null && entry.getValue() == null) {
                AuditChangeDto dto = new AuditChangeDto();
                dto.setPropertyName(entry.getKey());
                dto.setOldValue(previousValuesMap.get(entry.getKey()).toString());
                dto.setNewValue("");
                changes.add(dto);
                continue;
            }
            if (previousValuesMap.get(entry.getKey()) != null && entry.getValue() != null && !previousValuesMap.get(entry.getKey()).equals(entry.getValue())) {
                if (entry.getValue() instanceof Date) {
                    if (!DateUtils.isSameDay(((Date) entry.getValue()), ((Date) previousValuesMap.get(entry.getKey())))) {
                        AuditChangeDto dto = new AuditChangeDto();
                        dto.setPropertyName(entry.getKey());
                        Date oldDate = (Date) previousValuesMap.get(entry.getKey());
                        dto.setOldValue(sdf.format(oldDate));
                        Date newDate = (Date) entry.getValue();
                        if (addStyle) {
                            dto.setNewValue("<font style=\"BACKGROUND-COLOR: yellow\">" + sdf.format(newDate) + "</font>");
                        } else {
                            dto.setNewValue(sdf.format(newDate));
                        }
                        changes.add(dto);
                    }
                } else {
                    AuditChangeDto dto = new AuditChangeDto();
                    dto.setPropertyName(entry.getKey());
                    dto.setOldValue((previousValuesMap.get(entry.getKey()).toString()));
                    if (addStyle) {
                        dto.setNewValue("<font style=\"BACKGROUND-COLOR: yellow\">" + (entry.getValue().toString()) + "</font>");
                    } else {
                        dto.setNewValue(entry.getValue().toString());
                    }
                    changes.add(dto);
                }
                }
            }
        return changes;
    }

    public List<AuditChangeDto> compareWithRecentVersion(Object entity, Long id, String... ignoreFields) {
        Object previousVersion = AuditService.instance().getVersion(entity.getClass(), id, 1);
        return compare(previousVersion, entity, false, ignoreFields);
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

    public static void checkForChanges(Map.Entry<String, Object> entry, info.chili.service.jrs.types.Entry e, Map<String, Object> previousValuesMap) {
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

    protected static void highLightChanges(info.chili.service.jrs.types.Entry e) {
        e.setValue("<font style=\"BACKGROUND-COLOR: yellow\">" + e.getValue() + "</font>");
    }

    public static String highLightChanges(String str) {
        return "<font style=\"BACKGROUND-COLOR: yellow\">" + str + "</font>";
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
