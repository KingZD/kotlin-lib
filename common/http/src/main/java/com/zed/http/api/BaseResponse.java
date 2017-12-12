package com.zed.http.api;

import java.io.Serializable;

/**
 * Desc  : 回调信息 - 基类
 */
public abstract class BaseResponse<T> implements Serializable {

    private final static int STATUS_SUCCEED = 0;

    private int code;

    private String message;

    private String msg;

    private String status;

    private String version;

    private T data;

    public BaseResponse() {
    }

    public boolean isSucceed() {
        return code == STATUS_SUCCEED;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
