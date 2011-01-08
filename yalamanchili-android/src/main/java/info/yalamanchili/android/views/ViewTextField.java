package info.yalamanchili.android.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewTextField extends LinearLayout {

	TextView label;
	TextView text;
	TextView errorMsg;

	public ViewTextField(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ViewTextField(Context context) {
		super(context);
		init();
	}

	public void init() {
		setOrientation(LinearLayout.VERTICAL);
		label = new TextView(getContext());
		text = new TextView(getContext());
		errorMsg = new TextView(getContext());
		int lHeight = LayoutParams.WRAP_CONTENT;
		int lWidth = LayoutParams.FILL_PARENT;
		addView(label, new LinearLayout.LayoutParams(lWidth, lHeight));
		addView(text, new LinearLayout.LayoutParams(lWidth, lHeight));
		addView(errorMsg, new LinearLayout.LayoutParams(lWidth, lHeight));
		label.setTypeface(Typeface.DEFAULT_BOLD);
		text.setBackgroundColor(Color.LTGRAY);
		text.setTextColor(Color.BLACK);

	}

	public void setValue(String value) {
		text.setText(value);
	}

	public String getValue() {
		return text.getText().toString();
	}

	public void setLabel(String label) {
		this.label.setText(label);
	}

	public void getLabel() {
		this.label.getText().toString();
	}

	public void setErrorMessage(String message) {
		errorMsg.setText(message);
	}

}