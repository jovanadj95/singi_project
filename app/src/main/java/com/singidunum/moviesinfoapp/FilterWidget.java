package com.singidunum.moviesinfoapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class FilterWidget extends LinearLayout {

    public FilterWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FilterWidget, 0, 0);
        try {
            CharSequence[] entries = a.getTextArray(R.styleable.FilterWidget_android_entries);
            if (entries != null) {
                String[] filterGroups = Arrays.toString(entries).replaceAll("[\\[\\]]", "").split(", ");

                for (String filterGroup : filterGroups) {
                    final View view = LayoutInflater.from(context).inflate(R.layout.filter_base, null);
                    addView(view);
                    ((TextView) view.findViewById(R.id.filter_group_name)).setText(filterGroup);
                    view.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            expandFilter(view);
                        }
                    });
                }
            }
        } finally {
            a.recycle();
        }
    }

    private void expandFilter(View view) {
        ((ImageButton)view.findViewById(R.id.expand_button)).setImageResource(R.drawable.ic_arrow_down);
        Toast.makeText(getContext(), "radi " + ((TextView)view.findViewById(R.id.filter_group_name)).getText(), Toast.LENGTH_SHORT).show();
    }
}
