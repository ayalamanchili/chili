package info.chili.android.views;

import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DateField extends BaseField {

	protected static final int DATE_ACTIVITY_REQUEST_CODE = 100;

	protected int dateFieldId;

	protected TextView dateView;
	protected Button dateButton;

	public DateField(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void init() {
		label = new TextView(getContext());
		dateView = new TextView(getContext());
		errorMsg = new TextView(getContext());
		dateButton = new Button(getContext());
		dateButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent i = new Intent(getContext(), DateWidget.class);
				i.putExtra("id", getDateFieldId());
				((Activity) getContext()).startActivityForResult(i,
						DATE_ACTIVITY_REQUEST_CODE);
			}
		});
	}

	@Override
	protected void layout() {
		setOrientation(LinearLayout.VERTICAL);
		int lHeight = LayoutParams.WRAP_CONTENT;
		int lWidth = LayoutParams.FILL_PARENT;
		addView(label, new LinearLayout.LayoutParams(lWidth, lHeight));
		addView(dateView, new LinearLayout.LayoutParams(lWidth, lHeight));
		addView(dateButton, new LinearLayout.LayoutParams(lWidth, lHeight));
		addView(errorMsg, new LinearLayout.LayoutParams(lWidth, lHeight));
		label.setTypeface(Typeface.DEFAULT_BOLD);
	}

	@Override
	protected void customize() {
		dateButton.setText("Date");
	}

	public void setValue(String value) {
		dateView.setText(value);
	}

	public Date getValue() {
		return DateWidget.getDate(getDateFieldId());
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

	public int getDateFieldId() {
		return dateFieldId;
	}

	public void setDateFieldId(int dateFieldId) {
		this.dateFieldId = dateFieldId;
	}

}