package com.jinghan.app.mvp.model.response;

/**
 * @author liuzeren
 * @time 2017/11/9    上午11:21
 * @mail lzr319@163.com
 */
public class BaseResponse<T> {

    private static final String STATUS_SUCCESS = "000000";

    private String returnCode;

    private String message;

    private T resultData;

    public String getReturnCode() {
        return returnCode;
    }

    public String getMessage() {
        return message;
    }

    public T getResultData() {
        return resultData;
    }

    /**
     * 是否成功
     * */
    public boolean isSusccess(){
        return returnCode == STATUS_SUCCESS;
    }
}