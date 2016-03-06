package com.medikeen.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView {

	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);

		if (isInEditMode()) {
			return;
		}

		Typeface typeface = Typeface.createFromAsset(context.getAssets(),
				"SofiaProLight.otf");
		setTypeface(typeface);
	}
}
