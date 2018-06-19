package com.singidunum.moviesinfoapp.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.singidunum.moviesinfoapp.FilterObjectId;

import java.util.List;

public class ListFilterWidget extends FilterBaseWidget {
    List<FilterObjectId> list;

    public ListFilterWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setList(List<FilterObjectId> list) {
        this.list = list;
    }

}
