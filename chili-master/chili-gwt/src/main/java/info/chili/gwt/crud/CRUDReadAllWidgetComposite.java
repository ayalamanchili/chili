/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.gwt.crud;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.user.client.Window;
import info.chili.gwt.widgets.GenericPopup;

/**
 *
 * @author Ramana.Lukalapu
 */
public abstract class CRUDReadAllWidgetComposite extends ReadAllMatrixWidgetComposite {

//temp method need to update all implementing sub classes
    protected void createOptionsWidget(TableRowOptionsWidget.OptionsType type, final int row, final String id) {
        TableRowOptionsWidget rowOptionsWidget = new TableRowOptionsWidget(type, id) {
            @Override
            protected void onQuickView() {
                if (GenericPopup.instance() != null) {
                    GenericPopup.instance().hide();
                }
                CRUDReadAllWidgetComposite.this.onQuickView(row, id);
            }

            @Override
            public void onMouseOut(MouseOutEvent event) {
                if (GenericPopup.instance() != null && !enablePersistedQuickView()) {
                    GenericPopup.instance().hide();
                }
            }
        };
        createOptionsWidget(rowOptionsWidget, row, id);
    }
    
    @Override
    public void createOptionsWidget(TableRowOptionsWidget rowOptionsWidget, int row, String id) {
        if (enableQuickView()) {
            rowOptionsWidget.configureQuickViewLink();
        }
        rowOptionsWidget.initListeners(this);
        table.setWidget(row, 0, rowOptionsWidget);
        optionsWidgetMap.put(String.valueOf(row), rowOptionsWidget);
    }

    protected void createOptionsWidget(final String id, final int row, TableRowOptionsWidget.OptionsType... types) {
        TableRowOptionsWidget rowOptionsWidget = new TableRowOptionsWidget(id, types) {
            @Override
            protected void onQuickView() {
                if (GenericPopup.instance() != null) {
                    GenericPopup.instance().hide();
                }
                CRUDReadAllWidgetComposite.this.onQuickView(row, id);
            }

            @Override
            public void onMouseOut(MouseOutEvent event) {
                if (GenericPopup.instance() != null && !enablePersistedQuickView()) {
                    GenericPopup.instance().hide();
                }
            }
        };
        if (enableQuickView()) {
            rowOptionsWidget.configureQuickViewLink();
        }
        rowOptionsWidget.initListeners(this);
        table.setWidget(row, 0, rowOptionsWidget);
        optionsWidgetMap.put(String.valueOf(row), rowOptionsWidget);
    }

    protected boolean enableQuickView() {
        return false;
    }

    @Override
    public void onOptionsSelected(ClickEvent event, TableRowOptionsWidget rowOptionsWidget) {
        if (event.getSource().equals(rowOptionsWidget.getReadLink())) {
            viewClicked(rowOptionsWidget.getEntityId());
        }
        if (event.getSource().equals(rowOptionsWidget.getUpdateLink())) {
            updateClicked(rowOptionsWidget.getEntityId());
        }
        if (event.getSource().equals(rowOptionsWidget.getDeleteLink())) {
            preDelete(rowOptionsWidget.getEntityId());
        }
        if (event.getSource().equals(rowOptionsWidget.getPrintLink())) {
            printClicked(rowOptionsWidget.getEntityId());
        }
        if (event.getSource().equals(rowOptionsWidget.getCopyLink())) {
            copyClicked(rowOptionsWidget.getEntityId());
        }
        if (event.getSource().equals(rowOptionsWidget.getCancelLink())) {
            cancelClicked(rowOptionsWidget.getEntityId());
        }
        if (event.getSource().equals(rowOptionsWidget.getMarkAsReadLink())) {
            markAsReadClicked(rowOptionsWidget.getEntityId());
        }
    }

//TODO make this non abstract
    public abstract void viewClicked(String entityId);

    public void preDelete(String entityId) {
        if (Window.confirm("Are you sure? you want to Delete")) {
            deleteClicked(entityId);
        }
    }
    /*
     * add logic to support deleting the record with the input entityId
     */

    //TODO make this non abstract
    public abstract void deleteClicked(String entityId);

    /*
     * add logic (eg:navigation) on what to happen after succesfuull deleting
     * the row
     */
    //TODO make this non abstract
    public abstract void postDeleteSuccess();

    /**
     * override this to add logic to perform on update row clicked
     */
    //TODO make this non abstract
    public abstract void updateClicked(String entityId);

    protected void onQuickView(int row, String id) {

    }

    public void printClicked(String entityId) {

    }

    public void copyClicked(String entityId) {

    }

    public void cancelClicked(String entityId) {

    }

    public void markAsReadClicked(String entityId) {
        
    }
     
    protected boolean enablePersistedQuickView() {
        return false;
    }
}
