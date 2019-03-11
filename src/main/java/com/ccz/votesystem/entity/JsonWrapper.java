package com.ccz.votesystem.entity;

import lombok.Data;

/**
 * 用来包装数据
 */
@Data
public class JsonWrapper {
    private String massage;
    private int code;
    private Object data;
    private long total;

    public JsonWrapper(long total,String massage, int code, Object data) {
        this.massage = massage;
        this.code = code;
        this.data = data;
        this.total = total;
    }

    public JsonWrapper(String massage, int code) {
        this.massage = massage;
        this.code = code;
    }

    public JsonWrapper(String massage, int code, Object data) {
        this.massage = massage;
        this.code = code;
        this.data = data;
    }
}
