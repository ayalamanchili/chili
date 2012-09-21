package info.chili.android.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

public class OptionsBar extends LinearLayout {
	public final static int OPTIONS_BAR_DONE_BUTTON = 13;
	public final static int OPTIONS_BAR_UPDATE_BUTTON = 14;
	public final static int OPTIONS_BAR_DELETE_BUTTON = 15;
	Button doneButton;
	Button updateButton;
	Button delatePageButton;

	public OptionsBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init() {
		setOrientation(LinearLayout.HORIZONTAL);
		doneButton = new Button(getContext());
		doneButton.setId(OPTIONS_BAR_DONE_BUTTON);
		updateButton = new Button(getContext());
		updateButton.setId(OPTIONS_BAR_UPDATE_BUTTON);
		delatePageButton = new Button(getContext());
		delatePageButton.setId(OPTIONS_BAR_DELETE_BUTTON);
		doneButton.setText("done");
		updateButton.setText("next");
		delatePageButton.setText("previous");
		int lHeight = LayoutParams.WRAP_CONTENT;
		int lWidth = LayoutParams.WRAP_CONTENT;
		addView(doneButton, new LinearLayout.LayoutParams(lWidth, lHeight));
		addView(updateButton, new LinearLayout.LayoutParams(lWidth, lHeight));
		addView(delatePageButton,
				new LinearLayout.LayoutParams(lWidth, lHeight));
	}

	public Button getDoneButton() {
		return doneButton;
	}

	public Button getUpdateButton() {
		return updateButton;
	}

	public Button getDeleteButton() {
		return delatePageButton;
	}
}
