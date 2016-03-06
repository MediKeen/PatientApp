package com.medikeen.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class MyEditText extends EditText {

	public MyEditText(Context context, AttributeSet attrs) {
		super(context, attrs);

		if (isInEditMode()) {
			return;
		}

		Typeface typeface = Typeface.createFromAsset(context.getAssets(),
				"SofiaProLight.otf");
		setTypeface(typeface);
	}

}
