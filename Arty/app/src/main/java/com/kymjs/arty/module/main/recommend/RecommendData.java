package com.kymjs.arty.module.main.recommend;

import java.util.List;

/**
 * Created by ZhangTao on 12/26/16.
 */

public class RecommendData {
    
    private int status;
    private String message;
    private List<Poem> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Poem> getData() {
        return data;
    }

    public void setData(List<Poem> data) {
        this.data = data;
    }
}
