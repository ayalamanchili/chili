/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.gwt.crud;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import info.chili.gwt.callback.ALAsyncCallback;
import info.chili.gwt.rpc.HttpService;
import info.chili.gwt.utils.JSONUtils;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 *
 * @author anuyalamanchili
 */
//TODO move this to commons had to issue with moving the get URL to commons for url root
public class ReadAllAuditDataPanel extends ReadAllComposite {

    private Logger logger = Logger.getLogger(ReadAllAuditDataPanel.class.getName());
    protected String url;

    public ReadAllAuditDataPanel(String url) {
        this.url = url;
        initTable("Audit", null);
    }

    @Override
    public void preFetchTable(int start) {
        // TODO externalize the limit size for read all
        HttpService.HttpServiceAsync.instance().doGet(url, new HashMap<String,String>(), true,
                new ALAsyncCallback<String>() {
            @Override
            public void onResponse(String result) {
                if (result != null || !result.isEmpty()) {
                    JSONObject resultObj = (JSONObject) JSONParser.parseLenient(result);
                    JSONArray data = JSONUtils.toJSONArray(resultObj.get("entityAuditData"));
                    populateHeaders((JSONObject) data.get(0));
                    populateAuditData(data);
                }
            }
        });

    }

    protected void populateHeaders(JSONObject entity) {
        JSONArray values = entity.get("vars").isArray();
        for (int i = 0; i <= values.size(); i++) {
            JSONObject prop = (JSONObject) values.get(i);
            table.setText(0, i, JSONUtils.toString(prop, "id"));
        }
    }

    protected void populateAuditData(JSONArray entities) {
        for (int i = 1; i <= entities.size(); i++) {
            JSONObject entity = (JSONObject) entities.get(i - 1);
            JSONArray values = entity.get("vars").isArray();
            for (int j = 0; j <= values.size(); j++) {
                JSONObject prop = (JSONObject) values.get(j);
                table.setText(i, j, JSONUtils.toString(prop, "value"));
            }
        }
    }

    @Override
    public void createTableHeader() {
    }

    @Override
    protected void createOptionsWidget(GenericTableRowOptionsWidget rowOptionsWidget, int row, String id) {
    }

    @Override
    public void fillData(JSONArray entities) {
    }

    @Override
    protected void addOptionsWidget(int row, JSONObject entity) {
    }

    @Override
    public void onOptionsSelected(ClickEvent event, GenericTableRowOptionsWidget t) {
    }
//    protected String getEntityAuditDataUrl() {
//        return OfficeWelcome.instance().constants.root_url() + "audit/changes-for-entity/" + entityClass + "/" + entityId;
//    }
}
