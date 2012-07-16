/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.jpa.validation.impl;

import info.chili.jpa.validation.Unique;
import java.io.Serializable;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author ayalamanchili
 */
public class UniqueIDValidator implements ConstraintValidator<Unique, Serializable> {

    private Class<?> entityClass;
    private String uniqueField;

    @Override
    public void initialize(Unique unique) {
        entityClass = unique.entity();
        uniqueField = unique.property();
    }

    @Override
    public boolean isValid(Serializable property, ConstraintValidatorContext cvContext) {

        String query = String.format("from %s where %s = ? ", entityClass.getName(), uniqueField);
        //TODO implement this
        return false;
    }
}
