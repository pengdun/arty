package com.kymjs.arty.bean;

import com.kymjs.arty.module.upgrade.Upgrade;

import java.io.Serializable;

/**
 * <b>Project:</b> YuantaiApplication<br>
 * <b>Create Date:</b> 2017/1/8<br>
 * <b>Author:</b> pengdun<br>
 * <b>Description:</b> <br>
 */
public class UpGradeMessage implements Serializable {

    private String msg = "msg";

    public UpGradeMessage(String msg) {
        this.msg = msg;
    }
}
