package com.jinghan.app.mvp.model.bean;

/**
 * 欢迎页信息体
 * @author liuzeren
 * @time 2017/11/9    上午11:06
 * @mail lzr319@163.com
 */
public class SplashInfo {

    private String initialImageUrl;
    private String linkUrl;
    private int countDown;

    public String getInitialImageUrl() {
        return initialImageUrl;
    }

    public void setInitialImageUrl(String initialImageUrl) {
        this.initialImageUrl = initialImageUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public int getCountDown() {
        return countDown;
    }

    public void setCountDown(int countDown) {
        this.countDown = countDown;
    }
}