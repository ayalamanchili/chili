package info.chili.android.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class BaseTextField extends BaseField {

	protected TextView text;

	public BaseTextField(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void init() {
		label = new TextView(getContext());
		if (readOnly) {
			text = new TextView(getContext());
		} else {
			text = new EditText(getContext());
		}
		errorMsg = new TextView(getContext());
	}

	@Override
	protected void layout() {
		setOrientation(LinearLayout.VERTICAL);
		int lHeight = LayoutParams.WRAP_CONTENT;
		int lWidth = LayoutParams.FILL_PARENT;
		addView(label, new LinearLayout.LayoutParams(lWidth, lHeight));
		addView(text, new LinearLayout.LayoutParams(lWidth, lHeight));
		addView(errorMsg, new LinearLayout.LayoutParams(lWidth, lHeight));
		label.setTypeface(Typeface.DEFAULT_BOLD);
	}

	public void setValue(String value) {
		text.setText(value);
	}

	public String getValue() {
		return text.getText().toString();
	}
}
