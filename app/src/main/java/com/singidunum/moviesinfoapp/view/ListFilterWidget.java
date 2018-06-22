package com.singidunum.moviesinfoapp.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.singidunum.moviesinfoapp.R;
import com.singidunum.moviesinfoapp.model.filter.FilterObjectId;

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

        setList(getList(((TextView) findViewById(R.id.header_name)).getText().toString()));
        LinearLayout frameLayout = findViewById(R.id.content);
        for (int i = 0; i < list.size(); i++) {
            final CheckBox checkBox = new CheckBox(frameLayout.getContext());
            checkBox.setText(list.get(i).getDisplayName());
            frameLayout.addView(checkBox);
            checkBox.setTextColor(getResources().getColor(android.R.color.darker_gray));
            checkBox.setButtonDrawable(R.drawable.ic_unchecked);
            final int position = i;
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        selected.add(list.get(position));
                        checkBox.setButtonDrawable(R.drawable.ic_checked);
                    } else {
                        selected.remove(list.get(position));
                        checkBox.setButtonDrawable(R.drawable.ic_unchecked);
                    }
                }
            });
        }
    }

    private List<FilterObjectId> getList(String title) {
        switch (title) {
            case "Genres":
                return getGenres();
            case "Languages":
                return getLanguages();
            case "Production houses":
                return getProductions();
            default:
                return null;
        }
    }

    public void setList(List<FilterObjectId> list) {
        this.list.clear();
        this.list = list;
    }

    private List<FilterObjectId> getGenres() {
        List<FilterObjectId> genres = new ArrayList<>();
        genres.add(new FilterObjectId("28", "Action"));
        genres.add(new FilterObjectId("12", "Adventure"));
        genres.add(new FilterObjectId("16", "Animation"));
        genres.add(new FilterObjectId("35", "Comedy"));
        genres.add(new FilterObjectId("80", "Crime"));
        genres.add(new FilterObjectId("99", "Documentary"));
        genres.add(new FilterObjectId("18", "Drama"));
        genres.add(new FilterObjectId("10751", "Family"));
        genres.add(new FilterObjectId("14", "Fantasy"));
        genres.add(new FilterObjectId("36", "History"));
        genres.add(new FilterObjectId("27", "Horror"));
        genres.add(new FilterObjectId("10402", "Music"));
        genres.add(new FilterObjectId("9648", "Mystery"));
        genres.add(new FilterObjectId("10749", "Romance"));
        genres.add(new FilterObjectId("878", "Science Fiction"));
        genres.add(new FilterObjectId("10770", "TV Movie"));
        genres.add(new FilterObjectId("53", "Thriller"));
        genres.add(new FilterObjectId("10752", "War"));
        genres.add(new FilterObjectId("37", "Western"));
        return genres;
    }

    private List<FilterObjectId> getLanguages() {
        List<FilterObjectId> languages = new ArrayList<>();
        languages.add(new FilterObjectId("en", "English"));
        languages.add(new FilterObjectId("es", "Spanish"));
        languages.add(new FilterObjectId("fr", "French"));
        languages.add(new FilterObjectId("it", "Italian"));
        languages.add(new FilterObjectId("ru", "Russian"));
        languages.add(new FilterObjectId("sr", "Serbian"));
        languages.add(new FilterObjectId("el", "Greek"));
        languages.add(new FilterObjectId("hr", "Croatian"));
        languages.add(new FilterObjectId("de", "German"));
        return languages;
    }

    private List<FilterObjectId> getProductions() {
        List<FilterObjectId> productions = new ArrayList<>();
        productions.add(new FilterObjectId("3036", "Walt Disney Studios Motion Pictures"));
        productions.add(new FilterObjectId("17", "Warner Bros. Entertainment"));
        productions.add(new FilterObjectId("5", "Columbia Pictures"));
        productions.add(new FilterObjectId("25", "20th Century Fox"));
        productions.add(new FilterObjectId("21", "Metro-Goldwyn-Mayer"));
        productions.add(new FilterObjectId("33", "Universal Pictures"));
        productions.add(new FilterObjectId("4", "Paramount"));
        productions.add(new FilterObjectId("1632", "Lionsgate"));
        productions.add(new FilterObjectId("7", "DreamWorks"));
        productions.add(new FilterObjectId("19551", "Marvel Enterprises"));
        return productions;
    }
}
