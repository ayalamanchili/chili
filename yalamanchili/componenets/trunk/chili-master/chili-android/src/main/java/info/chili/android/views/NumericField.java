package info.chili.android.views;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.EditText;

public class NumericField extends BaseTextField {

	public NumericField(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	@Override
	protected void customize() {
		// fixme not working
		text.setInputType(InputType.TYPE_CLASS_NUMBER);
	}

	public class NumericView extends EditText {
		public NumericView(Context context) {
			super(context);
		}

		public NumericView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public NumericView(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
		}
		// TODO having issue with this the keyCode does nto seemt o be
		// consistent or being fired at all times.
		// public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
		// // numbers and back space
		// Log.d("chili-android", new Integer(keyCode).toString());
		// if ((keyCode >= 7 && keyCode <= 16) || keyCode == 67) {
		// return super.onKeyDown(keyCode, keyEvent);
		// }
		// return false;
		// }
	}

}
