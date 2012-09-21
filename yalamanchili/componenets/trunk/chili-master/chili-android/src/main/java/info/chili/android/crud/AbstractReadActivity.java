package info.chili.android.crud;

import info.chili.android.http.AsyncHttpGet;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public abstract class AbstractReadActivity extends AbstractCRUDActivity {
	protected Button done;
	protected Button update;
	protected Button delete;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(contentViewId());
		customizeTitle();
		done = (Button) findViewById(doneButtonId());
		done.setOnClickListener(this);
		update = (Button) findViewById(updateButtonId());
		update.setOnClickListener(this);
		delete = (Button) findViewById(deleteButtonId());
		delete.setOnClickListener(this);
		assignFields();
	}

	/* set the create panel id here */
	protected abstract int contentViewId();

	/* set the create button id here */
	protected abstract int updateButtonId();

	protected abstract int doneButtonId();

	protected abstract int deleteButtonId();

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

	protected abstract String entityName();

	public void onClick(View arg0) {
		if (arg0 == done) {
			finish();
		}
		if (arg0 == update) {
			Intent intent = new Intent(this, updateClass());
			intent.putExtra("entity", entity.toString());
			startActivity(intent);
		}
		if (arg0 == delete) {
			Toast.makeText(getApplicationContext(), "aaa", Toast.LENGTH_SHORT);
		}
	}

	public abstract Class<?> updateClass();

	public abstract String getReadURL();

	@Override
	public void onStart() {
		super.onStart();
		Log.d("chili-android", "in start");
		new AsyncHttpGet(this) {
			@Override
			protected void onResponse(String result) {
				try {
					entity = (JSONObject) new JSONObject(result)
							.get(entityName());
					populateFieldsFromEntity();
				} catch (JSONException e) {
					e.printStackTrace();
					// TODO
				}
			}
		}.execute(getReadURL());
	}
}
