package com.jinghan.app.mvp.model.request;

/**
 * @author liuzeren
 * @time 2017/11/9    上午11:13
 * @mail lzr319@163.com
 */
public class BaseRequest<T extends BaseRequestData> {

    /**
     * 请求的类名
     * */
    private String service;

    /**
     * 请求的方法名
     * */
    private String method;

    /**
     * 服务器端接口版本号,默认为1.0.0
     * */
    private String version = "1.0.0";

    /**
     * 封装参数对象
     * */
    private T data;

    public BaseRequest(String service,String method,T data){
        this.service = service;
        this.method = method;
        this.data = data;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}