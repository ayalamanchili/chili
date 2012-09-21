package info.chili.android.crud;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import info.chili.android.http.AsyncHttpPut;
import info.chili.android.http.HttpRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public abstract class AbstractUpdateActivity extends AbstractCRUDActivity {
	protected Button update;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(contentViewId());
		customizeTitle();
		update = (Button) findViewById(updateButtonId());
		update.setOnClickListener(this);
		assignFields();
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.d("ichili-android", "in update start");
		try {
			entity = new JSONObject(getIntent().getSerializableExtra("entity")
					.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		populateFieldsFromEntity();
	}

	/* set the create panel id here */
	protected abstract int contentViewId();

	/* set the create button id here */
	protected abstract int updateButtonId();

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

		if (view == update) {
			preUpdate();
			onUpdate();
		}
	}

	/* set the entity from the parent entity is exists */
	/*
	 * eg: if (getIntent().getSerializableExtra("entity") != null) { dealer =
	 * (JSONObject) getIntent() .getSerializableExtra("entity"); } else { dealer
	 * = new JSONObject(); }
	 */
	protected abstract void preUpdate();

	protected void onUpdate() {
		HttpRequest request = new HttpRequest(updateURL(),
				populateEntityFromFields(entity).toString(),
				headers());
		new AsyncHttpPut(this) {
			@Override
			protected void onResponse(String result) {
				Toast.makeText(AbstractUpdateActivity.this, "updated",
						Toast.LENGTH_LONG);
				finish();
			}

			@Override
			protected void onValidationErrors(String errorsString) {
				processValidationErrors(errorsString);
			}
		}.execute(request);
	}

	protected abstract Map<String,String> headers();
	
	// TODO make this generic
	protected abstract String updateURL();

}
