package com.example.pdf_signature.model.result;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PdfResult {

    @SerializedName("code")
    private Integer code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private List<DataDTO> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        @SerializedName("connect")
        private ConnectDTO connect;
        @SerializedName("qrcodeUrl")
        private String qrcodeUrl;
        @SerializedName("deviceName")
        private String deviceName;
        @SerializedName("className")
        private String className;
        @SerializedName("qrcodeName")
        private String qrcodeName;

        public ConnectDTO getConnect() {
            return connect;
        }

        public void setConnect(ConnectDTO connect) {
            this.connect = connect;
        }

        public String getQrcodeUrl() {
            return qrcodeUrl;
        }

        public void setQrcodeUrl(String qrcodeUrl) {
            this.qrcodeUrl = qrcodeUrl;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getQrcodeName() {
            return qrcodeName;
        }

        public void setQrcodeName(String qrcodeName) {
            this.qrcodeName = qrcodeName;
        }

        public static class ConnectDTO {
            @SerializedName("token")
            private String token;
            @SerializedName("webUrl")
            private String webUrl;

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getWebUrl() {
                return webUrl;
            }

            public void setWebUrl(String webUrl) {
                this.webUrl = webUrl;
            }
        }
    }
}
