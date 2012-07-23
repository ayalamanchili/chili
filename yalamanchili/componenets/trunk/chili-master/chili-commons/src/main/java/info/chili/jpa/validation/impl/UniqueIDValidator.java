/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.jpa.validation.impl;

import info.chili.jpa.validation.Unique;
import info.chili.spring.SpringContext;
import java.io.Serializable;
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

    private boolean enabled = true;
    private Class<?> entityClass;
    private String uniqueField;
    private String emfName;
    //TODO support multiple entitymanagers

    @Override
    public void initialize(Unique unique) {
        entityClass = unique.entity();
        uniqueField = unique.property();
        emfName = unique.emfName();
    }

    @Override
    public boolean isValid(Serializable property, ConstraintValidatorContext cvContext) {
        if (isEnabled()) {
            String query = String.format("select count(*) from " + entityClass.getCanonicalName() + " where LOWER(" + uniqueField + ")='" + property.toString().toLowerCase() + "'");
            EntityManagerFactory emf = (EntityManagerFactory) SpringContext.getBean(emfName);
            Query findRecords = emf.createEntityManager().createQuery(query);
            Long count = (Long) findRecords.getResultList().get(0);
            if (count > 0) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
        //TODO close em
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
