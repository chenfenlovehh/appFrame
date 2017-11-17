package com.jinghan.app.mvp.model.bean;

/**
 * 底部栏信息
 * @author liuzeren
 * @time 2017/11/10    下午4:11
 * @mail lzr319@163.com
 */
public class CatalogInfo {

    private long catalogId;

    private String catalogName;

    private String logo;

    private String selectedLogo;

    private String service;

    private String method;

    private long oraderflag;

    private String jumpType;

    private String catalogType;

    private long parentId;

    private String highLight;

    public long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(long catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSelectedLogo() {
        return selectedLogo;
    }

    public void setSelectedLogo(String selectedLogo) {
        this.selectedLogo = selectedLogo;
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

    public long getOraderflag() {
        return oraderflag;
    }

    public void setOraderflag(long oraderflag) {
        this.oraderflag = oraderflag;
    }

    public String getJumpType() {
        return jumpType;
    }

    public void setJumpType(String jumpType) {
        this.jumpType = jumpType;
    }

    public String getCatalogType() {
        return catalogType;
    }

    public void setCatalogType(String catalogType) {
        this.catalogType = catalogType;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getHighLight() {
        return highLight;
    }

    public void setHighLight(String highLight) {
        this.highLight = highLight;
    }
}