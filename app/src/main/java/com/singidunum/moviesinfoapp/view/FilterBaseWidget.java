package com.singidunum.moviesinfoapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.singidunum.moviesinfoapp.R;

public class FilterBaseWidget extends LinearLayout implements View.OnClickListener {

    private View content;
    private ImageView expandButton;

    public FilterBaseWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.filter_base, this);
        findViewById(R.id.header).setOnClickListener(this);
        content = findViewById(R.id.content);
        TextView headerName = findViewById(R.id.header_name);
        expandButton = findViewById(R.id.expand_button);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FilterBaseWidget, 0, 0);
        try {
            headerName.setText(a.getString(R.styleable.FilterBaseWidget_title));
        } finally {
            a.recycle();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header:
                toggleContent();
                break;
        }
    }

    private void toggleContent() {
        if (content.getVisibility() == VISIBLE) {
            content.setVisibility(GONE);
            expandButton.setImageResource(R.drawable.ic_arrow_right);
        } else {
            expandButton.setImageResource(R.drawable.ic_arrow_down);
            content.setVisibility(VISIBLE);
        }
    }
}
