package com.singidunum.moviesinfoapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.gson.Gson;
import com.singidunum.moviesinfoapp.R;
import com.singidunum.moviesinfoapp.model.filter.FilterObjectId;
import com.singidunum.moviesinfoapp.service.SharedStorageData;

import java.util.ArrayList;
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
                        SharedStorageData.setLanguages(this, new Gson().toJson(saveCheckboxes(content)));
                        break;
                    case "Production houses":
                        SharedStorageData.setProductionHouses(this, new Gson().toJson(saveCheckboxes(content)));
                        break;
                }
            }
        }
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    private String saveDate(ViewGroup content) {
        String date = "";
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

    private List<FilterObjectId> saveCheckboxes(ViewGroup content) {
        List<FilterObjectId> selected = new ArrayList<>();
        for (int i = 0; i < content.getChildCount(); i++) {
            View child = content.getChildAt(i);
            if (child != null && child instanceof CheckBox) {
                if (((CheckBox) child).isChecked()) {
                    selected.add(new FilterObjectId(child.getTag().toString(), ((CheckBox) child).getText().toString()));
                }
            }
        }
        return selected;
    }
}
