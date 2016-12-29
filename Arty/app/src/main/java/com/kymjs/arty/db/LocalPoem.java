package com.kymjs.arty.db;

/**
 * Created by ZhangTao on 12/29/16.
 */

public class LocalPoem {
    private int _id;
    private String name; //名称
    private String poet; //作者
    private String video; //视频
    private String dynasty; //朝代 
    private String country; //国家
    private String column; //分类 
    private String stage; //阶段
    private String textbook; //课本
    private String book;  //丛书
    private String from; //出处
    private String description; //描述
    private String original;  //原文
    private String voice;  //语音


    public int getId() {
        return _id;
    }

    public LocalPoem setId(int _id) {
        this._id = _id;
        return this;
    }

    public String getName() {
        return name;
    }

    public LocalPoem setName(String name) {
        this.name = name;
        return this;
    }

    public String getPoet() {
        return poet;
    }

    public LocalPoem setPoet(String poet) {
        this.poet = poet;
        return this;
    }

    public String getVideo() {
        return video;
    }

    public LocalPoem setVideo(String video) {
        this.video = video;
        return this;
    }

    public String getDynasty() {
        return dynasty;
    }

    public LocalPoem setDynasty(String dynasty) {
        this.dynasty = dynasty;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public LocalPoem setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getColumn() {
        return column;
    }

    public LocalPoem setColumn(String column) {
        this.column = column;
        return this;
    }

    public String getStage() {
        return stage;
    }

    public LocalPoem setStage(String stage) {
        this.stage = stage;
        return this;
    }

    public String getTextbook() {
        return textbook;
    }

    public LocalPoem setTextbook(String textbook) {
        this.textbook = textbook;
        return this;
    }

    public String getBook() {
        return book;
    }

    public LocalPoem setBook(String book) {
        this.book = book;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public LocalPoem setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public LocalPoem setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getOriginal() {
        return original;
    }

    public LocalPoem setOriginal(String original) {
        this.original = original;
        return this;
    }

    public String getVoice() {
        return voice;
    }

    public LocalPoem setVoice(String voice) {
        this.voice = voice;
        return this;
    }
}
