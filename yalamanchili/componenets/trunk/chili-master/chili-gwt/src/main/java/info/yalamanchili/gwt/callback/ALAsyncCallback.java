package info.yalamanchili.gwt.callback;

import info.yalamanchili.gwt.widgets.LoadingWidget;
import info.yalamanchili.gwt.widgets.ResponseStatusWidget;

import java.util.logging.Logger;

import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Class ALAsyncCallback.
 */
public abstract class ALAsyncCallback<T> implements AsyncCallback<T> {

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
    public ALAsyncCallback() {
        loadingWidget.show();
    }

    public void onFailure(Throwable arg0) {
        logger.info(arg0.getLocalizedMessage());
        loadingWidget.hide();
        new ResponseStatusWidget().show("call to server failed");
    }

    public abstract void onResponse(T arg0);

    public void onSuccess(T arg0) {
        loadingWidget.hide();
        onResponse(arg0);
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}
