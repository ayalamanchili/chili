/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.security.dao;

import info.chili.commons.EntityQueryUtils;
import info.chili.dao.CRUDDao;
import info.chili.security.domain.CRole;
import info.chili.spring.SpringContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author anuyalamanchili
 */
@Component("croleDao")
@Scope("prototype")
public class CRoleDao extends CRUDDao<CRole> {

    public CRole createRole(String roleName) {
        CRole storeRole = EntityQueryUtils.findEntity(em, CRole.class, "rolename", roleName);
        if (storeRole == null) {
            storeRole = new CRole();
            storeRole.setRolename(roleName);
            storeRole = em.merge(storeRole);
        }
        return storeRole;
    }
    
    @PersistenceContext
    protected EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public CRoleDao() {
        super(CRole.class);
    }

    public static CRoleDao instance() {
        return (CRoleDao) SpringContext.getBean("croleDao");
    }
}
