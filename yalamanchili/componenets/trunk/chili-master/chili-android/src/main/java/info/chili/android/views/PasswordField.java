package info.chili.android.views;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.TextView;

public class PasswordField extends BaseTextField {

	protected TextView label;
	protected TextView text;
	protected TextView errorMsg;

	protected boolean readOnly = false;

	public PasswordField(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void customize() {
		text.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
	}

}