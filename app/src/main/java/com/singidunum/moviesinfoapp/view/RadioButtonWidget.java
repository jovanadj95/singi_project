package com.singidunum.moviesinfoapp.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.singidunum.moviesinfoapp.R;
import com.singidunum.moviesinfoapp.model.filter.FilterObject;
import com.singidunum.moviesinfoapp.service.FilterLists;
import com.singidunum.moviesinfoapp.service.SharedStorageData;

import java.util.List;

public class RadioButtonWidget extends FilterBaseWidget {

    public RadioButtonWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        LinearLayout content = findViewById(R.id.content);
        RadioGroup radioGroup = new RadioGroup(content.getContext());
        radioGroup.setOrientation(VERTICAL);
        List<FilterObject> languages = FilterLists.getLanguages();

        for (int i = 0; i < languages.size(); i++) {
            RadioButton radioButton = new RadioButton(content.getContext());
            radioButton.setText(languages.get(i).getDisplayName());
            radioButton.setTag(languages.get(i).getId());
            radioButton.setTextColor(getResources().getColor(android.R.color.darker_gray));
            radioGroup.addView(radioButton);
            radioButton.setButtonDrawable(R.drawable.radio_button_selector);

            FilterObject language = new Gson().fromJson(SharedStorageData.getLanguages(getContext()), FilterObject.class);
            String selected = language == null ? "English" : language.getDisplayName();
            if (!TextUtils.isEmpty(selected) && languages.get(i).getDisplayName().equals(selected)) {
                radioButton.setChecked(true);
            }
        }
        content.addView(radioGroup);
    }
}
