package info.yalamanchili.gwt.widgets;

import info.yalamanchili.gwt.composite.ALComposite;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ImageUploadPanel extends ALComposite implements ClickHandler {
	private Logger logger = Logger.getLogger(ImageUploadPanel.class.getName());
	VerticalPanel panel = new VerticalPanel();
	FormPanel formPanel = new FormPanel();
	FileUpload fileUpload = new FileUpload();
	Button submit = new Button("Upload");

	// ClickableLink addImage = new ClickableLink("add more..");

	public ImageUploadPanel() {
		init(formPanel);
	}

	@Override
	protected void addListeners() {
		submit.addClickHandler(this);
		// addImage.addClickHandler(this);
	}

	@Override
	protected void addWidgets() {
		panel.add(fileUpload);
		panel.add(submit);
		// panel.add(addImage);
		formPanel.add(panel);
	}

	@Override
	protected void configure() {
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.setMethod(FormPanel.METHOD_POST);
		fileUpload.setName("Upload Image");
		formPanel.setAction(GWT.getModuleBaseURL() + "imageupload");

		formPanel.addSubmitHandler(new FormPanel.SubmitHandler() {
			public void onSubmit(SubmitEvent event) {
				logger.info("form action:" + formPanel.getAction());
				logger.info("Image name:" + fileUpload.getFilename());
			}
		});
		formPanel.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			public void onSubmitComplete(SubmitCompleteEvent event) {
				// TODO display confirmation
			}
		});
	}

	public void onClick(ClickEvent event) {
		if (event.getSource() == submit) {
			submit();
		}
	}

	public void submit() {
		formPanel.submit();
	}

	// TODO HTML5 issue
	// http://acidmartin.wordpress.com/2009/06/09/the-mystery-of-cfakepath-unveiled/
	public String getImageName() {
		return getPlainImageName(fileUpload.getFilename());
	}

	private String getPlainImageName(String name) {
		int start = name.lastIndexOf("\\");
		return name.substring(start + 1, name.length());
	}
}
