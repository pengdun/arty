package com.kymjs.arty.module.main.moment;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZhangTao on 12/27/16.
 */
public class Moment {
    private int type;
    @SerializedName("article_id")
    private String articleId;
    @SerializedName("topic_id")
    private String topicId;
    @SerializedName("topic_title")
    private String topicTitle;
    private String content;
    private int praise;
    private int view;
    private int comment;
    private String img;
    private String audio;
    @SerializedName("created_user_id")
    private String createdUserId;
    @SerializedName("created_time")
    private long createdTime;
    @SerializedName("created_nickname")
    private String createdNickname;
    @SerializedName("created_portrait")
    private String createdPortrait;
    @SerializedName("praise_status")
    private int praiseStatus;
    @SerializedName("follow_status")
    private int followStatus;
    @SerializedName("topic_type")
    private int topicType;
    @SerializedName("poem_id")
    private int poemId;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedNickname() {
        return createdNickname;
    }

    public void setCreatedNickname(String createdNickname) {
        this.createdNickname = createdNickname;
    }

    public String getCreatedPortrait() {
        return createdPortrait;
    }

    public void setCreatedPortrait(String createdPortrait) {
        this.createdPortrait = createdPortrait;
    }

    public int getPraiseStatus() {
        return praiseStatus;
    }

    public void setPraiseStatus(int praiseStatus) {
        this.praiseStatus = praiseStatus;
    }

    public int getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(int followStatus) {
        this.followStatus = followStatus;
    }

    public int getTopicType() {
        return topicType;
    }

    public void setTopicType(int topicType) {
        this.topicType = topicType;
    }

    public int getPoemId() {
        return poemId;
    }

    public void setPoemId(int poemId) {
        this.poemId = poemId;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Moment
                && ((Moment) obj).getType() == type
                && topicId != null
                && topicId.equals(((Moment) obj).getTopicId())
                && articleId != null
                && articleId.equals(((Moment) obj).getArticleId())
                && ((Moment) obj).getCreatedTime() == createdTime;
    }

    @Override
    public int hashCode() {
        return ("" + type + topicId.hashCode() + articleId.hashCode() + createdTime).hashCode();
    }
}