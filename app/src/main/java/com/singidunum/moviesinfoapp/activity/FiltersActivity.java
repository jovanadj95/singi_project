package com.singidunum.moviesinfoapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.singidunum.moviesinfoapp.R;
import com.singidunum.moviesinfoapp.model.filter.FilterObject;
import com.singidunum.moviesinfoapp.service.SharedStorageData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FiltersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        findViewById(R.id.save_filters).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFilters((ViewGroup) findViewById(R.id.save_filters).getParent());
            }
        });

        findViewById(R.id.clear_filters).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFilters((ViewGroup) findViewById(R.id.save_filters).getParent().getParent());
            }
        });
    }

    private void clearFilters(ViewGroup parent) {
        parent = (ViewGroup) parent.getChildAt(0);
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if (child != null && child.findViewById(R.id.header_name) != null && child.findViewById(R.id.content) != null) {
                ViewGroup content = child.findViewById(R.id.content);
                switch (((TextView) child.findViewById(R.id.header_name)).getText().toString()) {
                    case "Genres":
                        clearCheckboxes(content);
                        break;
                    case "Date from":
                        clearDate(content, true);
                        break;
                    case "Date to":
                        clearDate(content, false);
                        break;
                    case "Languages":
                        clearRadioButton(content);
                        break;
                    case "Production houses":
                        clearCheckboxes(content);
                        break;
                }
            }
        }
    }

    private void clearRadioButton(ViewGroup content) {
        ViewGroup radioGroup = (ViewGroup) content.getChildAt(0);
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            View child = radioGroup.getChildAt(i);
            if (child != null && child instanceof RadioButton) {
                if (((RadioButton) child).isChecked()) {
                    if (!((RadioButton) child).getText().toString().equals("English")) {
                        ((RadioButton) child).setChecked(false);
                    }
                } else if (!((RadioButton) child).isChecked() && ((RadioButton) child).getText().toString().equals("English")) {
                    ((RadioButton) child).setChecked(true);
                }
            }
        }
    }

    private void clearDate(ViewGroup content, boolean from) {
        for (int i = 0; i < content.getChildCount(); i++) {
            View child = content.getChildAt(i);
            if (child != null && child.findViewById(R.id.date_picker) != null) {
                DatePicker datePicker = child.findViewById(R.id.date_picker);
                if (from) {
                    datePicker.updateDate(Calendar.getInstance().get(Calendar.YEAR) - 5,
                            Calendar.getInstance().get(Calendar.MONTH),
                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                } else {
                    datePicker.updateDate(Calendar.getInstance().get(Calendar.YEAR),
                            Calendar.getInstance().get(Calendar.MONTH),
                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                }
            }
        }
    }

    private void clearCheckboxes(ViewGroup content) {
        for (int i = 0; i < content.getChildCount(); i++) {
            View child = content.getChildAt(i);
            if (child != null && child instanceof CheckBox) {
                if (((CheckBox) child).isChecked()) {
                    ((CheckBox) child).setChecked(false);
                }
            }
        }
    }

    private void saveFilters(ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if (child != null && child.findViewById(R.id.header_name) != null && child.findViewById(R.id.content) != null) {
                ViewGroup content = child.findViewById(R.id.content);
                switch (((TextView) child.findViewById(R.id.header_name)).getText().toString()) {
                    case "Genres":
                        SharedStorageData.setGenres(this, new Gson().toJson(saveCheckboxes(content)));
                        break;
                    case "Date from":
                        SharedStorageData.setDateFrom(this, saveDate(content));
                        break;
                    case "Date to":
                        SharedStorageData.setDateTo(this, saveDate(content));
                        break;
                    case "Languages":
                        SharedStorageData.setLanguages(this, new Gson().toJson(saveRadioButton(content)));
                        break;
                    case "Production houses":
                        SharedStorageData.setProductionHouses(this, new Gson().toJson(saveCheckboxes(content)));
                        break;
                }
            }
        }
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    private FilterObject saveRadioButton(ViewGroup content) {
        ViewGroup radioGroup = (ViewGroup) content.getChildAt(0);
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            View child = radioGroup.getChildAt(i);
            if (child != null && child instanceof RadioButton) {
                if (((RadioButton) child).isChecked()) {
                    return new FilterObject(child.getTag().toString(), ((RadioButton) child).getText().toString());
                }
            }
        }
        return null;
    }

    private String saveDate(ViewGroup content) {
        String date = null;
        for (int i = 0; i < content.getChildCount(); i++) {
            View child = content.getChildAt(i);
            if (child != null && child.findViewById(R.id.date_picker) != null) {
                DatePicker datePicker = child.findViewById(R.id.date_picker);
                String month = String.valueOf(datePicker.getMonth() + 1);
                if (month.length() == 1) {
                    month = "0" + month;
                }
                date = datePicker.getYear() + "-" + month + "-" + datePicker.getDayOfMonth();
            }
        }
        return date;
    }

    private List<FilterObject> saveCheckboxes(ViewGroup content) {
        List<FilterObject> selected = new ArrayList<>();
        for (int i = 0; i < content.getChildCount(); i++) {
            View child = content.getChildAt(i);
            if (child != null && child instanceof CheckBox) {
                if (((CheckBox) child).isChecked()) {
                    selected.add(new FilterObject(child.getTag().toString(), ((CheckBox) child).getText().toString()));
                }
            }
        }
        return selected;
    }
}
