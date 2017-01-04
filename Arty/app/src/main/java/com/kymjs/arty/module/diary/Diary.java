package com.kymjs.arty.module.diary;

import java.sql.Timestamp;

/**
 * 用户写作
 */
public class Diary {

    private long _id;
    private long userId;
    private String poet;
    private String title;
    private String content;
    private Timestamp createTime;

    public long getId() {
        return _id;
    }

    public Diary setId(long id) {
        this._id = id;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public Diary setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public String getPoet() {
        return poet;
    }

    public Diary setPoet(String poet) {
        this.poet = poet;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Diary setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Diary setContent(String content) {
        this.content = content;
        return this;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public Diary setCreateTime(Timestamp creatTime) {
        this.createTime = creatTime;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Diary) {
            return ((Diary) obj)._id == _id && ((Diary) obj).userId == userId;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return (String.valueOf(userId) + _id).hashCode();
    }
}
