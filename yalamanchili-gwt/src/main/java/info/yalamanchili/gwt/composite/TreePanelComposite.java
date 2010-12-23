package info.yalamanchili.gwt.composite;

import info.yalamanchili.gwt.callback.ALAsyncCallback;
import info.yalamanchili.gwt.rpc.GWTService.GwtServiceAsync;
import info.yalamanchili.gwt.widgets.ClickableLink;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

// TODO: Auto-generated Javadoc
/**
 * The Class TreePanelComposite.
 */
public abstract class TreePanelComposite<T> extends Composite implements
		ClickHandler {
	Logger logger = Logger.getLogger(TreePanelComposite.class.getName());
	protected Map<String, ClickableLink> links = new HashMap<String, ClickableLink>();
	/** The entity. */
	protected T entity;

	/** The panel. */
	protected FlowPanel panel = new FlowPanel();

	/** The tree. */
	protected Tree tree = new Tree();
	protected TreeItem rootNode = new TreeItem("root");

	/**
	 * Gets the entity.
	 * 
	 * @return the entity
	 */
	public T getEntity() {
		return entity;
	}

	public TreePanelComposite() {
		initWidget(panel);
	}

	public void initTreePanelComposite(String className) {
		panel.add(tree);
		panel.addStyleName("y-gwt-TreePanel");
		rootNode.setText(getClassSimpleName(className));
		tree.addItem(rootNode);
		GwtServiceAsync.instance().getClassRelations(className,
				new ALAsyncCallback<List<String>>() {

					@Override
					public void onResponse(List<String> classes) {
						for (String className : classes) {
							addFirstChildLink(className);
						}
					}

				});
		addListeners();
		configure();
		addWidgets();

	}

	protected void addFirstChildLink(String name) {
		ClickableLink link = new ClickableLink(getClassSimpleName(name));
		rootNode.addItem(link);
		links.put(name, link);
		link.addClickHandler(this);
	}

	protected void removeFirstChildLink(String className) {
		if (links.containsKey(className)) {
			links.remove(className);
		} else {
			logger.log(Level.INFO, "link not present:" + className);
		}
	}

	/**
	 * Adds the listeners.
	 */
	protected abstract void addListeners();

	/**
	 * Configure.
	 */
	protected abstract void configure();

	/**
	 * Adds the widgets.
	 */
	protected abstract void addWidgets();

	public void onClick(ClickEvent event) {
		entity = loadEntity();

		for (String link : links.keySet()) {
			if (event.getSource().equals(links.get(link))) {
				linkClicked(link);
			}
		}

	}

	public abstract void linkClicked(String entiyName);

	/**
	 * Load entity.
	 * 
	 * @param id
	 *            the id
	 */
	public abstract T loadEntity();

	protected String getClassSimpleName(String name) {
		return (name.substring(name.lastIndexOf(".") + 1)).toLowerCase();
	}
}