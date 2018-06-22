package com.singidunum.moviesinfoapp.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.singidunum.moviesinfoapp.R;

public class DatePickerWidget extends FilterBaseWidget {

    public DatePickerWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((LinearLayout) findViewById(R.id.content)).addView(LayoutInflater.from(getContext()).inflate(R.layout.filter_date_picker, null));
    }
}
