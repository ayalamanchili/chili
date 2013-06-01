/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.security.dao;

import info.chili.dao.CRUDDao;
import info.chili.security.domain.CUser;
import info.chili.spring.SpringContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author ayalamanchili
 */
@Component("cuserDao")
@Scope("prototype")
public class CUserDao extends CRUDDao<CUser> {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public CUserDao() {
        super(CUser.class);
    }

    public static CUserDao instance() {
        return (CUserDao) SpringContext.getBean("cuserDao");
    }
}
