package com.example.pdf_signature.model.result;

public class SettingsResult {

    /**
     * 服务器地址
     */
    private String address;

    /**
     * 列表接口
     */
    private String listApi;

    /**
     * 上传签名图片接口（base64）
     */
    private String signApi;

    /**
     * 设备序列号
     */
    private String deviceNo;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getListApi() {
        return listApi;
    }

    public void setListApi(String listApi) {
        this.listApi = listApi;
    }

    public String getSignApi() {
        return signApi;
    }

    public void setSignApi(String signApi) {
        this.signApi = signApi;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }
}
