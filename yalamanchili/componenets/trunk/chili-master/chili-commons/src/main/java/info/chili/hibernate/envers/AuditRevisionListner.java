/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.hibernate.envers;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author ayalamanchili
 */
public class AuditRevisionListner implements RevisionListener {

    @Override
    public void newRevision(Object o) {
        AuditRevisionEntity revEntity = (AuditRevisionEntity) o;
        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth.getName().isEmpty()) {
                revEntity.setUpdatedUserId("SYSTEM");
            } else {
                revEntity.setUpdatedUserId(auth.getName());
            }
        }
    }
}
