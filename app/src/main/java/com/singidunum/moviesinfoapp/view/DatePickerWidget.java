package com.singidunum.moviesinfoapp.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.singidunum.moviesinfoapp.R;
import com.singidunum.moviesinfoapp.service.SharedStorageData;

import java.util.Calendar;

public class DatePickerWidget extends FilterBaseWidget {

    public DatePickerWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((LinearLayout) findViewById(R.id.content)).addView(LayoutInflater.from(getContext()).inflate(R.layout.filter_date_picker, null));
        DatePicker datePicker = findViewById(R.id.date_picker);
        setDate(((TextView) findViewById(R.id.header_name)).getText().toString(), datePicker);
    }

    private void setDate(String title, DatePicker datePicker) {
        String date = null;
        switch (title) {
            case "Date from":
                date = SharedStorageData.getDateFrom(getContext());
                break;
            case "Date to":
                date = SharedStorageData.getDateTo(getContext());
                break;
        }
        if (!TextUtils.isEmpty(date)) {
            String day = date.substring(date.lastIndexOf("-") + 1);
            String month = date.substring(date.indexOf("-") + 1, date.lastIndexOf("-"));
            String year = date.substring(0, date.indexOf("-"));
            datePicker.updateDate(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
        } else {
            if (title.equals("Date from")) {
                datePicker.updateDate(Calendar.getInstance().get(Calendar.YEAR) - 5, Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            }
        }
    }
}
