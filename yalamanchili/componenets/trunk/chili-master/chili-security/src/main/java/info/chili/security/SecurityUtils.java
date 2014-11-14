/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.security;

import info.chili.commons.EntityQueryUtils;
import info.chili.security.domain.acl.AclClass;
import info.chili.security.domain.acl.AclSid;
import info.chili.spring.SpringContext;
import javax.persistence.EntityManager;
import org.jasypt.digest.StringDigester;
import org.jasypt.hibernate.encryptor.HibernatePBEStringEncryptor;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author ayalamanchili
 */
@Component
public class SecurityUtils {

    public static final String OBFUSCATED_STR = "*********";

    /**
     * this is a method that takes unencrypted password and returns encrypted
     * one
     */
    public static String encodePassword(String password, String salt) {
        ShaPasswordEncoder encoder = (ShaPasswordEncoder) SpringContext.getBean("passwordEncoder");
        return encoder.encodePassword(password, null);
    }

    /*
     * used for hasing encrypted string properties used for querying data
     */
    public static String hash(String string) {
        StringDigester officeStringDigester = (StringDigester) SpringContext.getBean("officeStringDigester");
        return officeStringDigester.digest(string);
    }

    public static String encrypt(String string) {
        HibernatePBEStringEncryptor encryptor = (HibernatePBEStringEncryptor) SpringContext.getBean("hibernateStringEncryptor");
        return encryptor.encrypt(string);
    }

    public static String decrypt(String string) {
        HibernatePBEStringEncryptor encryptor = (HibernatePBEStringEncryptor) SpringContext.getBean("hibernateStringEncryptor");
        return encryptor.decrypt(string);
    }

    public static String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static AclClass getAclClassFor(EntityManager em, Class cls) {
        //TODO cache this
        return EntityQueryUtils.findEntity(em, AclClass.class, "clazz", cls.getCanonicalName());
    }

    public static AclSid getCurrentAclSid(EntityManager em) {
        return EntityQueryUtils.findEntity(em, AclSid.class, "sid", getCurrentUser());
    }
    
}
