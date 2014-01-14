/**
 * System Soft Technolgies Copyright (C) 2013 ayalamanchili@sstech.mobi
 */
package info.chili.gwt.crud;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import info.chili.gwt.resources.ChiliImages;
import info.chili.gwt.widgets.ClickableImage;
import info.chili.gwt.widgets.GenericPopup;

public class TableRowOptionsWidget extends GenericTableRowOptionsWidget implements MouseOverHandler, MouseOutHandler {
    
    ClickableImage readLink = new ClickableImage("view", ChiliImages.INSTANCE.viewIcon_16_16());
    ClickableImage updateLink = new ClickableImage("update", ChiliImages.INSTANCE.updateIcon_16_16());
    ClickableImage deleteLink = new ClickableImage("delete", ChiliImages.INSTANCE.deleteIcon_16_16());
    ClickableImage quickViewLink;
    
    public enum OptionsType {
        
        READ, UPDATE, DELETE, READ_UPDATE, READ_UPDATE_DELETE, READ_DELETE, UPDATE_DELETE
    };
    protected OptionsType type;
    
    public TableRowOptionsWidget(OptionsType type, String id) {
        super(id);
        this.type = type;
        addWidgets();
        configure();
        addListeners();
    }
    
    protected void addListeners() {
        
    }
    
    protected void initListeners(ClickHandler handler) {
        readLink.addClickHandler(handler);
        updateLink.addClickHandler(handler);
        deleteLink.addClickHandler(handler);
    }
    
    protected void configure() {
        readLink.addStyleName("readL");
        updateLink.addStyleName("updateLink");
        deleteLink.addStyleName("deleteLink");
    }
    
    protected void configureQuickViewLink() {
        quickViewLink = new ClickableImage("quick view", ChiliImages.INSTANCE.quickViewIcon_16_16());
        quickViewLink.addMouseOverHandler(this);
        quickViewLink.addMouseOutHandler(this);
        panel.insert(quickViewLink, 0);
    }
    
    protected void addWidgets() {
        if (OptionsType.READ.equals(type)) {
            panel.add(readLink);
        }
        if (OptionsType.UPDATE.equals(type)) {
            panel.add(updateLink);
        }
        if (OptionsType.DELETE.equals(type)) {
            panel.add(deleteLink);
        }
        if (OptionsType.READ_UPDATE.equals(type)) {
            panel.add(readLink);
            panel.add(updateLink);
        }
        if (OptionsType.READ_UPDATE_DELETE.equals(type)) {
            panel.add(readLink);
            panel.add(updateLink);
            panel.add(deleteLink);
        }
        if (OptionsType.READ_DELETE.equals(type)) {
            panel.add(readLink);
            panel.add(deleteLink);
        }
        if (OptionsType.UPDATE_DELETE.equals(type)) {
            panel.add(updateLink);
            panel.add(deleteLink);
        }
    }
    
    public ClickableImage getReadLink() {
        return readLink;
    }
    
    public ClickableImage getUpdateLink() {
        return updateLink;
    }
    
    public ClickableImage getDeleteLink() {
        return deleteLink;
    }
    
    @Override
    public void onMouseOver(MouseOverEvent event) {
        if (event.getSource().equals(quickViewLink)) {
            onQuickView();
        }
    }
    
    @Override
    public void onMouseOut(MouseOutEvent event) {
        GenericPopup.instance().hide();
    }
    
    protected void onQuickView() {
        
    }
}
