package info.chili.android.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BooleanField extends BaseField {

	protected CheckBox booleanBox;

	public BooleanField(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void init() {
		label = new TextView(getContext());
		booleanBox = new CheckBox(getContext());
		booleanBox.setEnabled(!readOnly);
		errorMsg = new TextView(getContext());
	}

	@Override
	protected void layout() {
		setOrientation(LinearLayout.VERTICAL);
		int lHeight = LayoutParams.WRAP_CONTENT;
		int lWidth = LayoutParams.FILL_PARENT;
		addView(label, new LinearLayout.LayoutParams(lWidth, lHeight));
		addView(booleanBox, new LinearLayout.LayoutParams(lWidth, lHeight));
		addView(errorMsg, new LinearLayout.LayoutParams(lWidth, lHeight));
		label.setTypeface(Typeface.DEFAULT_BOLD);
	}

	@Override
	protected void customize() {
		// TODO Auto-generated method stub

	}

	public void setValue(Boolean value) {
		booleanBox.setChecked(value);
	}

	public Boolean getValue() {
		return booleanBox.isChecked();
	}

}
