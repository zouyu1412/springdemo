package com.ssp.higher.base.pojos;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.StringUtils;

public class Result {

    public Result() {
        
    }
    
    /**
     * 正常结果
     * @param result
     */
    public Result(Object result) {
        this.code = 0;
        this.msg = StringUtils.EMPTY;
        this.result = result;
    }
    
    /**
     * 异常结果
     * @param code
     * @param msg
     */
    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.result = null;
    }
    
    /**
     * 异常结果
     * @param code
     * @param msg
     */
    public Result(Throwable exception) {
        this.code = -99;
        this.msg = exception.getMessage();
        this.result = null;
    }
    
    /**
     * 自定义结果
     * @param code
     * @param result
     */
    public Result(int code, Object result) {
        this.code = code;
        this.msg = StringUtils.EMPTY;
        this.result = result;
    }
    
    /**
     * 自定义结果
     * @param code
     * @param msg
     * @param result
     */
    public Result(int code, String msg, Object result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    /** 返回状态 */
    @SerializedName("code")
    private int code;

    /** 错误信息 */
    @SerializedName("msg")
    private String msg;

    /** 返回值 */
    @SerializedName("result")
    private Object result;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getResult() {
        return result;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
