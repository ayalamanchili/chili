package info.chili.android.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReadAllPageBar extends LinearLayout {
	public final static int READ_ALL_PAGING_BAR_CREATE_BUTTON=10;
	public final static int READ_ALL_PAGING_BAR_NEXT_BUTTON=11;
	public final static int READ_ALL_PAGING_BAR_PREVIOUS_BUTTON=12;
	Button createButton;
	Button nextPageButton;
	Button previousPageButton;
	
	
	public ReadAllPageBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init() {
		setOrientation(LinearLayout.HORIZONTAL);
		createButton = new Button(getContext());
		createButton.setId(READ_ALL_PAGING_BAR_CREATE_BUTTON);
		nextPageButton = new Button(getContext());
		nextPageButton.setId(READ_ALL_PAGING_BAR_NEXT_BUTTON);
		previousPageButton = new Button(getContext());
		previousPageButton.setId(READ_ALL_PAGING_BAR_PREVIOUS_BUTTON);
		createButton.setText("create");
		nextPageButton.setText("next");
		previousPageButton.setText("previous");
		int lHeight = LayoutParams.WRAP_CONTENT;
		int lWidth = LayoutParams.WRAP_CONTENT;
		addView(createButton, new LinearLayout.LayoutParams(lWidth, lHeight));
		addView(nextPageButton, new LinearLayout.LayoutParams(lWidth, lHeight));
		addView(previousPageButton, new LinearLayout.LayoutParams(lWidth, lHeight));
	}

	public Button getCreateButton() {
		return createButton;
	}

	public Button getNextPageButton() {
		return nextPageButton;
	}

	public Button getPreviousPageButton() {
		return previousPageButton;
	}
	
}
