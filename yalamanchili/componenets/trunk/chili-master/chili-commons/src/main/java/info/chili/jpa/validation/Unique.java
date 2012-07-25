/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.jpa.validation;

import info.chili.jpa.validation.impl.UniqueIDValidator;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author ayalamanchili
 */
@Target({ElementType.TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueIDValidator.class)
@Documented
public @interface Unique {

    String message() default "{not.unique.msg}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * The mapped hibernate/jpa entity class
     */
    Class<?> entity();

    /**
     * The property of the entity we want to validate for uniqueness. Default
     * name is "id"
     */
    String[] fields();

    String emfName() default "emf";
    //TODO add entitymanager name

    String idName() default "id";

    Class idClass() default Long.class;
}