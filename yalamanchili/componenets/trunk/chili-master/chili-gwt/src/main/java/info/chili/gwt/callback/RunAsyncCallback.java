/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.gwt.callback;

import com.google.gwt.user.client.rpc.AsyncCallback;
import info.chili.gwt.widgets.LoadingWidget;
import info.chili.gwt.widgets.ResponseStatusWidget;
import java.util.logging.Logger;

/**
 *
 * @author anuyalamanchili
 */
public abstract class RunAsyncCallback implements com.google.gwt.core.client.RunAsyncCallback {

    Logger logger = Logger.getLogger(ALAsyncCallback.class.getName());
    /**
     * The is completed.
     */
    protected Boolean isCompleted = false;
    /**
     * The loading widget.
     */
    LoadingWidget loadingWidget = new LoadingWidget();

    /**
     * Instantiates a new aL async callback.
     */
    public RunAsyncCallback() {
        loadingWidget.show();
    }

    @Override
    public void onFailure(Throwable reason) {
        logger.info(reason.getLocalizedMessage());
        loadingWidget.hide();
        new ResponseStatusWidget().show("call failed");
    }

    @Override
    public void onSuccess() {
        loadingWidget.hide();
        onResponse();
    }

    public abstract void onResponse();
}
