/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.jpa.validation;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author ayalamanchili
 */
public class ValidationUtils {

    public static Set<ConstraintViolation<Object>> validate(Object entity) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(entity);
    }
}
