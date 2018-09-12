package com.singidunum.moviesinfoapp.model.api.actors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActorFilmography {

    @SerializedName("cast")
    @Expose
    private List<Cast> cast = null;
    @SerializedName("id")
    @Expose
    private Integer id;

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}