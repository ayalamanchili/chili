/**
 * System Soft Technolgies Copyright (C) 2013 ayalamanchili@sstech.mobi
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.gwt.crud;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 *
 * @author anuyalamanchili
 */
public abstract class GenericTableRowOptionsWidget extends Composite {

    protected HorizontalPanel panel = new HorizontalPanel();
    protected String entityId;

    public GenericTableRowOptionsWidget(String entityId) {
        this.entityId = entityId;
        panel.setSpacing(10);
        initWidget(panel);
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
}
