/**
 * System Soft Technolgies Copyright (C) 2013 ayalamanchili@sstech.mobi
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.gwt.crud;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import info.chili.gwt.composite.ALComposite;
import info.chili.gwt.composite.LocalStorage;
import info.chili.gwt.fields.ListBoxField;
import info.chili.gwt.utils.Alignment;
import info.chili.gwt.utils.JSONUtils;
import info.chili.gwt.utils.Utils;
import info.chili.gwt.widgets.DocumentationWidget;
import info.chili.gwt.widgets.GenericPopup;
import info.chili.gwt.widgets.ResponseStatusWidget;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author anuyalamanchili
 */
public abstract class ReadAllComposite<T extends GenericTableRowOptionsWidget> extends ALComposite implements ClickHandler, ChangeHandler {

    private static Logger logger = Logger.getLogger(CRUDReadAllComposite.class.getName());
    /*
     * Parent entityId if any
     */
    protected String parentId;
    protected FlowPanel basePanel = new FlowPanel();
    protected CaptionPanel captionPanel = new CaptionPanel();
    /**
     * The mainPanel.
     */
    protected FlowPanel mainPanel = new FlowPanel();
    /**
     * The paging mainPanel.
     */
    protected HorizontalPanel pagingPanel = new HorizontalPanel();
    /**
     * The table mainPanel.
     */
    protected FlowPanel tablePanel = new FlowPanel();
    /**
     * The data table.
     */
    protected FlexTable table = new FlexTable();
    /**
     * The number of records.
     */
    public Long numberOfRecords = new Long(0);
    /**
     * The page size.
     */
    protected Integer pageSize = 10;// default
    /**
     * The number of pages.
     */
    protected Integer numberOfPages;
    /**
     * create/add button to add new record this is set to not visible by
     * default. To be seen need to override the configure createButton(
     */
    protected Button createButton = new Button("Create");
    /**
     * The class canonical entityName.
     */
    protected String classCanonicalName;
    /**
     * The constants.
     */
    protected ConstantsWithLookup constants;
    /**
     * The go to page.
     */
    protected ListBoxField goToPage = new ListBoxField("Page: ", false, Alignment.HORIZONTAL);
    /**
     * The no of results l.
     */
    protected Label noOfResultsL = new Label("Total Results:");
    /*
     * Set of entityId and widgets respectively
     */
    protected Map<String, T> optionsWidgetMap = new HashMap<String, T>();

    protected void initTable(String className, ConstantsWithLookup constants) {
        this.classCanonicalName = className;
        this.constants = constants;
        init(basePanel);
        preFetchData();
    }

    protected void preFetchData() {
        preFetchTable(0);
    }

    /*
     *
     */
    protected void initTable(String className, JSONArray entities, ConstantsWithLookup constants) {
        this.classCanonicalName = className;
        this.constants = constants;
        this.entities = entities;
        init(basePanel);
        setTotalResults(entities.size());
        createTableHeader();
        fillData(entities);
    }
    /*
     * (non-Javadoc)
     *
     * @see info.yalamanchili.gwt.composite.ALComposite#configure()
     */

    @Override
    protected void configure() {
        configureTable();
        pagingPanel.setSpacing(5);
        captionPanel.addStyleName("readAllCompositeCaptionPanel");
        pagingPanel.addStyleName("y-gwt-PagingBar");
        mainPanel.addStyleName("y-gwt-ReadAllPanel");
        tablePanel.addStyleName("y-gwt-TablePanel");
        configureCreateButton();
    }

    protected void configureTable() {
        table.addStyleName("y-gwt-Table");
        table.getRowFormatter().addStyleName(0, "y-gwt-TableHeader");
        table.setBorderWidth(0);
        table.setCellSpacing(0);
    }

    @Override
    protected void addListeners() {
        goToPage.addChangeHandler(this);
        table.addClickHandler(this);
        createButton.addClickHandler(this);
    }

    @Override
    protected void addWidgets() {
        pagingPanel.add(goToPage);
        pagingPanel.add(noOfResultsL);
        tablePanel.add(table);
        mainPanel.add(createButton);
        mainPanel.add(pagingPanel);
        mainPanel.add(tablePanel);
        captionPanel.setContentWidget(mainPanel);
        captionPanel.setCaptionHTML(classCanonicalName);
        if (showDocumentationLink()) {
            configureDocumentationLink();
        }
        basePanel.add(captionPanel);
    }

    /**
     * Inits the load.
     *
     * @param noOfRecords the no of records
     */
    public void initPaging(Long noOfRecords) {
        pageSize = new Integer(10);
        numberOfRecords = noOfRecords;
        setTotalResults(noOfRecords.intValue());
        createPageLinks();
    }

    public void setTotalResults(Integer noOfRecords) {
        noOfResultsL.setText("Total Results:" + noOfRecords.toString());
    }

    /**
     * Creates the page links.
     */
    protected void createPageLinks() {
        if (numberOfPages == null || numberOfPages == 0) {
            numberOfPages = (numberOfRecords.intValue() / pageSize) + 1;
            for (int i = 1; i <= numberOfPages; i++) {
                goToPage.addValue(new Long(i), new Integer(i).toString());
            }
        }
    }

    public abstract void preFetchTable(int start);

    public void postFetchTable(String tableObjString) {
        if (tableObjString == null) {
            return;
        }
        //Temp fix
        if (tableObjString.equals("NOT_SHARED")) {
            tablePanel.add(new HTML("User has choosen to not share this data"));
            return;
        }
        JSONObject table = (JSONObject) JSONParser.parseLenient(tableObjString);
        if (table.get("size") != null) {
            JSONString size = (JSONString) table.get("size");
            initPaging(new Long(size.stringValue()));
        }
        createTableHeader();
        if (table.get("entities") != null) {
            entities = JSONUtils.toJSONArray(table.get("entities"));
            fillData(entities);
            addRowStyles(entities.size());
        }
        if (autoShowDocumentation()) {
            showDocumentationFrame();
        }
    }

    //TODO improve this do this with css
    protected void addRowStyles(int size) {
        HTMLTable.RowFormatter rf = table.getRowFormatter();
        for (int row = 1; row <= size; ++row) {
            rf.addStyleName(row, "y-gwt-ReadAllComposite-Row");
            if ((row % 2) != 0) {
                rf.addStyleName(row, "y-gwt-ReadAllComposite-OddRow");
            } else {
                rf.addStyleName(row, "y-gwt-ReadAllComposite-EvenRow");
            }
        }
    }
    protected JSONArray entities;

    public abstract void createTableHeader();

    protected abstract void createOptionsWidget(T rowOptionsWidget, int row, String id);

    protected String getEntityId(int row) {
        return table.getWidget(row, 0).getTitle();
    }

    protected JSONObject getEntity(String entityId) {
        for (int i = 0; i < entities.size(); i++) {
            JSONObject entity = (JSONObject) entities.get(i);
            if (entityId.equals(JSONUtils.toString(entity, "id"))) {
                return entity;
            }
        }
        return null;
    }

    public abstract void fillData(JSONArray entities);

    /*
     * Override this and call this in fill data method to call
     * createOptionsWidget based on roles and permissions
     */
    protected abstract void addOptionsWidget(int row, JSONObject entity);

    @Override
    public void onClick(ClickEvent event) {
        if (event.getSource().equals(createButton)) {
            createButtonClicked();
        }
        HTMLTable.Cell src = table.getCellForEvent(event);
        if (src != null) {
            Integer rowIndex = src.getRowIndex();
            if (rowIndex != null) {
                T rowWidget = optionsWidgetMap.get(String.valueOf(rowIndex));
                onOptionsSelected(event, rowWidget);
            }
        }
    }

    public abstract void onOptionsSelected(ClickEvent event, T t);

    /**
     * override this to make the create button visible and update its name etc
     */
    protected void configureCreateButton() {
        createButton.setVisible(false);
    }

    /**
     * override this method to perform logic to handle create button
     */
    protected void createButtonClicked() {
    }

    @Override
    public void onChange(ChangeEvent event) {
        if (event.getSource() == goToPage.getListBox()) {
            //TODO improve this
            table.removeAllRows();
            configureTable();
            preFetchTable((goToPage.getValue().intValue() * pageSize) - 10);
        }
    }

    // TODO move to pakage gwt.utils.Utils.java
    protected String getKeyValue(String id) {
        return Utils.getKeyValue(id, constants);
    }

    public void refresh() {
        table.removeAllRows();
        preFetchTable(0);
    }
//TODO move to commons

    protected void handleErrorResponse(Throwable err) {
        //TODO enhance to show generic error messages
        logger.info(err.getMessage());
        if (!err.getMessage().isEmpty() && err.getMessage().contains("Error")) {
            try {
                JSONValue errors = JSONParser.parseLenient(err.getMessage());
                processValidationErrors(errors);
            } catch (Exception e) {
                new ResponseStatusWidget().show("Call Failed");
            }
        } else {
            new ResponseStatusWidget().show("Call Failed");
        }
    }

    protected void processValidationErrors(JSONValue errorsObj) {
        JSONArray errorsArray = JSONUtils.toJSONArray(errorsObj.isObject().get("Error"));
        String genericErrorMessage = null;
        for (int i = 0; i < errorsArray.size(); i++) {
            JSONObject err = (JSONObject) errorsArray.get(i);
            genericErrorMessage = new String();
            genericErrorMessage = genericErrorMessage.concat("Error:");
            genericErrorMessage = genericErrorMessage.concat(err.get("source").isString().stringValue() + ":" + err.get("description").isString().stringValue());
        }
        if (genericErrorMessage != null) {
            new ResponseStatusWidget().show(genericErrorMessage);
        }
    }

    protected void configureDocumentationLink() {
        captionPanel.setCaptionHTML(captionPanel.getCaptionHTML() + Utils.getInformationIconHtml(getDocumentationLink()));
    }

    protected boolean showDocumentationLink() {
        return false;
    }

    protected String getDocumentationLink() {
        return "";
    }

    protected boolean autoShowDocumentation() {
        return false;
    }

    protected String getHideDocKey() {
        return "ReadAll" + classCanonicalName + "_hideDoc";
    }

    protected void showDocumentationFrame() {
        if (LocalStorage.getValue(getHideDocKey()) == null || !LocalStorage.getValue(getHideDocKey()).equals("true")) {
            new GenericPopup(new DocumentationWidget(getHideDocKey(), getDocumentationLink())).show();
        }
    }

    /**
     * @param row table row
     * @param column table column
     * @param entity actual object
     * @param enumClassName eg: Sex
     * @param attributeName eg: sex
     */
    protected void setEnumColumn(int row, int column, JSONObject entity, String enumClassName, String attributeName) {
        table.setText(row, column, Utils.getAttributeLabel(JSONUtils.toString(entity, attributeName), enumClassName, constants));
    }

    protected void setValueFromConstants(int row, int column, JSONObject entity, String className, String attributeName) {
        table.setText(row, column, Utils.getAttributeLabel(attributeName, className, constants));
    }

    /**
     * Used to replacing table columns values with custom values eg: sex_MALE=M
     *
     * @param entity Table row returned from server eg: Employee
     * @param key The key(column) value used to search and replace eg: sex_MALE
     * format: column name_value
     * @param customValues map used to store the custom values
     * @return
     */
    protected String getCustomValue(JSONValue entity, String key, HashMap<String, String> customValues) {
        key = key + "_" + JSONUtils.toString(entity, key);
        return (customValues.get(key)) == null ? key : customValues.get(key);
    }
}
