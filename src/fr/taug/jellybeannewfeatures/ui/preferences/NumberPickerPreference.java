package fr.taug.jellybeannewfeatures.ui.preferences;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;
import fr.taug.jellybeannewfeatures.R;

public class NumberPickerPreference extends DialogPreference {
	private NumberPicker mPicker;
	private int mStartRange = 0;
	private int mEndRange = 60;
	private int mDefault;

	public NumberPickerPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		if (attrs == null) {
			return;
		}

		TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.numberpicker);
		mStartRange = arr.getInteger(R.styleable.numberpicker_startRange, 0);
		mEndRange = arr.getInteger(R.styleable.numberpicker_endRange, 200);
		mDefault = arr.getInteger(R.styleable.numberpicker_defaultValue, 0);

		arr.recycle();

		setDialogLayoutResource(R.layout.number_picker_preference);
	}

	public NumberPickerPreference(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.dialogPreferenceStyle);
	}

	public NumberPickerPreference(Context context) {
		this(context, null);
	}

	@Override
	protected void onBindDialogView(View view) {
		super.onBindDialogView(view);
		mPicker = (NumberPicker) view.findViewById(R.id.number_picker);
		setRange(mStartRange, mEndRange);
		mPicker.setValue(getValue());
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		super.onDialogClosed(positiveResult);

		final int origValue = getValue();
		final int curValue = mPicker.getValue();

		if (positiveResult && (curValue != origValue)) {
			if (callChangeListener(curValue)) {
				saveValue(curValue);
			}
		}
	}

	public void setRange(int start, int end) {
		mPicker.setMaxValue(end);
		mPicker.setMinValue(start);
	}

	private boolean saveValue(int val) {
		return persistInt(val);
	}

	private int getValue() {
		return getSharedPreferences().getInt(getKey(), mDefault);
	}
}
