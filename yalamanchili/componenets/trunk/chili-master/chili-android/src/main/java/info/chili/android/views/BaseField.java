package info.chili.android.views;

import info.chili.android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class BaseField extends LinearLayout {

	protected TextView label;
	protected TextView errorMsg;

	protected boolean readOnly = false;

	public BaseField(Context context, AttributeSet attrs) {
		super(context, attrs);
		// read custom attributes
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.BooleanField);
		final int N = a.getIndexCount();
		for (int i = 0; i < N; ++i) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.BooleanField_readOnly:
				readOnly = a.getBoolean(attr, false);
				break;
			}
		}
		a.recycle();
		init();
		layout();
		// any customizations would go here
		customize();
	}

	protected void init() {
		label = new TextView(getContext());
		errorMsg = new TextView(getContext());
	}

	protected void layout() {
		setOrientation(LinearLayout.VERTICAL);
		int lHeight = LayoutParams.WRAP_CONTENT;
		int lWidth = LayoutParams.FILL_PARENT;
		addView(label, new LinearLayout.LayoutParams(lWidth, lHeight));
		//
		addView(errorMsg, new LinearLayout.LayoutParams(lWidth, lHeight));
		label.setTypeface(Typeface.DEFAULT_BOLD);
	}

	/*
	 * implement to do any customizatons here // eg: for numeric field //
	 * text.setInputType(InputType.TYPE_CLASS_NUMBER);
	 */
	protected abstract void customize();

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