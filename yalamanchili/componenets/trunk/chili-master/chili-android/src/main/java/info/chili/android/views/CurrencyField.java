package info.chili.android.views;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

//TODO implements currency validations 
//currently just a copy of Decimal field
public class CurrencyField extends BaseTextField {

	public CurrencyField(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void init() {
		label = new TextView(getContext());
		if (readOnly) {
			text = new TextView(getContext());
		} else {
			text = new EditText(getContext());
			text.setInputType(InputType.TYPE_CLASS_NUMBER);
			text.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		}
		errorMsg = new TextView(getContext());
	}

	@Override
	protected void customize() {
		// TODO Auto-generated method stub

	}

	// not being used currently could be used to build complex logic for inout
	// validation
	public class DecimalView extends EditText {

		public DecimalView(Context context) {
			super(context);
		}

		public DecimalView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public DecimalView(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
		}
		// TODO having issue with this the keyCode does nto seemt o be
		// consistent or being fired at all times. also having a issue with
		// decimal point being visible.
		// public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
		// // numbers, backspace and decimal point
		// Log.d("chili-android", new Integer(keyCode).toString());
		// if ((keyCode >= 7 && keyCode <= 16) || keyCode == 56
		// || keyCode == 67) {
		// return super.onKeyDown(keyCode, keyEvent);
		// }
		// return false;
		// }
	}

}