package com.kymjs.arty.module.main.moment;

import java.util.List;

/**
 * Created by ZhangTao on 12/27/16.
 */

public class MomentData {

    private int status;
    private String message;
    private List<Moment> data;

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

    public List<Moment> getData() {
        return data;
    }

    public void setData(List<Moment> data) {
        this.data = data;
    }
}
