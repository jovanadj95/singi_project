package com.singidunum.moviesinfoapp.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.singidunum.moviesinfoapp.R;
import com.singidunum.moviesinfoapp.model.filter.FilterObjectId;
import com.singidunum.moviesinfoapp.service.FilterLists;
import com.singidunum.moviesinfoapp.service.SharedStorageData;

import java.util.ArrayList;
import java.util.List;

public class ListFilterWidget extends FilterBaseWidget {
    private List<FilterObjectId> list = new ArrayList<>();
    private List<FilterObjectId> selected = new ArrayList<>();

    public ListFilterWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        String title = ((TextView) findViewById(R.id.header_name)).getText().toString();
        setList(getList(title));
        LinearLayout content = findViewById(R.id.content);
        for (int i = 0; i < list.size(); i++) {
            final CheckBox checkBox = new CheckBox(content.getContext());
            checkBox.setText(list.get(i).getDisplayName());
            checkBox.setTag(list.get(i).getId());
            content.addView(checkBox);
            checkBox.setTextColor(getResources().getColor(android.R.color.darker_gray));
            checkBox.setButtonDrawable(R.drawable.checkbox_selector);
            final int position = i;
            selected = getSelected(title);
            for (int j = 0; j < selected.size(); j++) {
                if (selected.get(j).getDisplayName().equals(list.get(i).getDisplayName())) {
                    checkBox.setChecked(true);
                    break;
                }
            }
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        selected.add(list.get(position));
                    } else {
                        selected.remove(list.get(position));
                    }
                }
            });
        }
    }

    private List<FilterObjectId> getSelected(String title) {
        String data = null;
        switch (title) {
            case "Genres":
                data = SharedStorageData.getGenres(getContext());
                break;
            case "Production houses":
                data = SharedStorageData.getProductionHouses(getContext());
                break;
        }
        if (data == null) {
            return new ArrayList<>();
        } else {
            return new Gson().fromJson(data, new TypeToken<ArrayList<FilterObjectId>>() {
            }.getType());
        }
    }

    private List<FilterObjectId> getList(String title) {
        switch (title) {
            case "Genres":
                return FilterLists.getGenres();
            case "Production houses":
                return FilterLists.getProductions();
            default:
                return new ArrayList<>();
        }
    }

    public void setList(List<FilterObjectId> list) {
        this.list.clear();
        this.list = list;
    }
}
