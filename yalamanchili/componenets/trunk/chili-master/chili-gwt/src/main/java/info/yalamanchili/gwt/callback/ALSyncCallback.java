package info.yalamanchili.gwt.callback;

import info.yalamanchili.gwt.widgets.LoadingWidget;
import info.yalamanchili.gwt.widgets.ResponseStatusWidget;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Class ALSyncCallback.
 */
public abstract class ALSyncCallback<T> implements AsyncCallback<T> {

    Logger logger = Logger.getLogger(ALSyncCallback.class.getName());
    /**
     * The loading widget.
     */
    protected LoadingWidget loadingWidget = new LoadingWidget();

    /**
     * Instantiates a new aL sync callback.
     */
    public ALSyncCallback() {
        loadingWidget.show();
    }

    /**
     * On response.
     *
     * @param response the response
     */
    public abstract void onResponse(T response);

    /**
     * Post response.
     *
     * @param response the response
     */
    public abstract void postResponse(T response);

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable
     * )
     */
    public void onFailure(Throwable arg0) {
        new ResponseStatusWidget().show("call to server failed");
        loadingWidget.hide();
        logger.log(Level.SEVERE, arg0.getLocalizedMessage());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
     */
    public void onSuccess(T arg0) {
        loadingWidget.hide();
        onResponse(arg0);
        postResponse(arg0);
    }
}
