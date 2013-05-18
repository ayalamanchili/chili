/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.security;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
/**
 *
 * @author ayalamanchili
 */
@Documented
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RUNTIME)
public @interface Obfuscated {
    
}
