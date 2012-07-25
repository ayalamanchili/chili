/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.jpa.validation.impl;

import info.chili.jpa.validation.Unique;
import info.chili.spring.SpringContext;
import info.yalamanchili.commons.ReflectionUtils;
import info.yalamanchili.http.HttpHelper;
import info.yalamanchili.jpa.AbstractEntity;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

/**
 *
 * @author ayalamanchili
 */
@Component
public class UniqueIDValidator implements ConstraintValidator<Unique, Serializable> {

    private final static Logger logger = Logger.getLogger(UniqueIDValidator.class.getName());
    private static boolean enabled = true;
    private Class<?> entityClass;
    private String[] uniqueFields;
    private String emfName;
    private String idName;
    private String message;
    //TODO support multiple entitymanagers

    @Override
    public void initialize(Unique unique) {
        this.entityClass = unique.entity();
        this.uniqueFields = unique.fields();
        this.emfName = unique.emfName();
        this.idName = unique.idName();
        this.message = unique.message();
    }

    @Override
    public boolean isValid(Serializable entityInstance, ConstraintValidatorContext cvContext) {
        if (isEnabled()) {
            EntityManagerFactory emf = (EntityManagerFactory) SpringContext.getBean(emfName);
            Query findRecords = emf.createEntityManager().createQuery(getUniqueQueryString(entityInstance));
            Long count = (Long) findRecords.getResultList().get(0);
            if (count > 0) {
                cvContext.disableDefaultConstraintViolation();
                //TODO 
                cvContext.buildConstraintViolationWithTemplate(message).addNode(uniqueFields[0]).addConstraintViolation();
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
        //TODO close em
    }

    public String getUniqueQueryString(Serializable entityInstance) {
        StringBuilder queryString = new StringBuilder();
        queryString.append("select count(*) from " + entityClass.getCanonicalName() + " where ");
        int i = 0;
        for (String field : uniqueFields) {
            queryString.append("LOWER(" + field + ") ='" + ReflectionUtils.callGetter(entityInstance, field) + "'");
            i++;
            if (i < uniqueFields.length) {
                queryString.append(" and ");
            }
        }
        Object id = ReflectionUtils.callGetter(entityInstance, idName);
        if (id != null) {
            queryString.append(" and id !=" + id.toString());
        }
        logger.info("unique validator query:" + queryString.toString());
        return queryString.toString();
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void enable() {
        enabled = true;
    }

    public static void disable() {
        enabled = false;
    }

    protected void addParameter(StringBuffer b, Map<String, Object> parameters,
            String alias, String name, String operator, Object value) {
        if (b.length() == 0) {
            b.append(" where ");
        } else {
            b.append(" and ");
        }
        b.append(alias);
        b.append(".");
        b.append(name);
        b.append(" ");
        if (value == null) {
            b.append("is null");
        } else {
            b.append(operator);
            b.append(" :");
            b.append(name);
            parameters.put(name, value);
        }
    }
}
