package info.chili.android.crud;

import java.util.Map;

import info.chili.android.http.AsyncHttpPut;
import info.chili.android.http.HttpRequest;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public abstract class AbstractCreateActivity extends AbstractCRUDActivity {
	protected Button create;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(contentViewId());
		customizeTitle();
		create = (Button) findViewById(createButtonId());
		create.setOnClickListener(this);
		assignFields();
	}

	/* set the create panel id here */
	protected abstract int contentViewId();

	/* set the create button id here */
	protected abstract int createButtonId();

	protected abstract void assignFields();

	// TODO move to parent
	public void customizeTitle() {
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, appTitleBarId());
		TextView mytitletext = (TextView) findViewById(appTitleBArTextViewId());
		mytitletext.setText(titleText());
	}

	/* set the custom title bar */
	// TODO can be defined from main of welcome class
	protected abstract int appTitleBarId();

	protected abstract int appTitleBArTextViewId();

	/* set the custom title text */
	protected abstract String titleText();

	public void onClick(View view) {

		if (view == create) {
			preCreate();
			onCreate();
		}
	}

	protected abstract void preCreate();

	protected void onCreate() {
		HttpRequest request = new HttpRequest(createURL(),
				populateEntityFromFields( entity).toString(),
				headers());
		new AsyncHttpPut(this) {
			@Override
			protected void onResponse(String result) {
				Toast.makeText(AbstractCreateActivity.this, "created",
						Toast.LENGTH_LONG);
				finish();
			}

			@Override
			protected void onValidationErrors(String errorsString) {
				processValidationErrors(errorsString);
			}
		}.execute(request);
	}

	protected abstract Map<String, String> headers();

	// TODO make this generic
	protected abstract String createURL();

}
