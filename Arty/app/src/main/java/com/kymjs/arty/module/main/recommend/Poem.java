package com.kymjs.arty.module.main.recommend;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZhangTao on 12/26/16.
 */

public class Poem {

    private int type;
    @SerializedName("poem_id")
    private int poemId;
    private String name;
    private String poet;
    private String dynasty;
    private String description;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPoemId() {
        return poemId;
    }

    public void setPoemId(int poemId) {
        this.poemId = poemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoet() {
        return poet;
    }

    public void setPoet(String poet) {
        this.poet = poet;
    }

    public String getDynasty() {
        return dynasty;
    }

    public void setDynasty(String dynasty) {
        this.dynasty = dynasty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Poem && ((Poem) obj).getPoemId() == poemId;
    }
}