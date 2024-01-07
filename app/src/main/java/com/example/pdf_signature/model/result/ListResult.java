package com.example.pdf_signature.model.result;

import com.google.gson.annotations.SerializedName;

public class ListResult {

    /**
     * 出库单编号
     */
    @SerializedName("ticketNo")
    private String ticketNo;

    /**
     * 编制人
     */
    @SerializedName("loginInform")
    private String loginInform;

    /**
     * 运输方式
     */
    @SerializedName("shippingPointDescr")
    private String shippingPointDescr;

    /**
     * 出库日期
     */
    @SerializedName("loadingDate")
    private String loadingDate;

    /**
     * 客户名称
     */
    @SerializedName("partnerDescr")
    private String partnerDescr;

    /**
     * 出库号
     */
    @SerializedName("voyageNo")
    private String voyageNo;

    /**
     * 订单号
     */
    @SerializedName("orderDocNo")
    private String orderDocNo;

    /**
     * 出库地点
     */
    @SerializedName("shippingPoint")
    private String shippingPoint;

    /**
     * 载具编号
     */
    @SerializedName("vehicleNo")
    private String vehicleNo;

    /**
     * 产品编号
     */
    @SerializedName("productCode")
    private String productCode;

    /**
     * 产品名称
     */
    @SerializedName("productDescr")
    private String productDescr;

    /**
     * 订单数量
     */
    @SerializedName("scheduledVolume")
    private Integer scheduledVolume;

    /**
     * 装货数量
     */
    @SerializedName("loadingVolume")
    private Integer loadingVolume;

    /**
     * 结算数量
     */
    @SerializedName("accountingVolume")
    private Double accountingVolume;

    /**
     * 单位TON
     */
    @SerializedName("priceUom")
    private String priceUom;

    /**
     * 15Volume
     */
    @SerializedName("l15Volume")
    private Integer l15Volume;

    /**
     * 编号
     */
    @SerializedName("serialNo")
    private Integer serialNo;

    /**
     * 流量计编号
     */
    @SerializedName("fmId")
    private String fmId;

    /**
     * 皮重 (液位)
     */
    @SerializedName("flowmeterOpening")
    private Integer flowmeterOpening;

    /**
     * 毛重 (液位)
     */
    @SerializedName("flowmeterClosing")
    private Integer flowmeterClosing;

    /**
     * 罐编号
     */
    @SerializedName("tankId")
    private String tankId;

    /**
     * 平均温度
     */
    @SerializedName("temp")
    private Double temp;

    /**
     * 密度 (15C)
     */
    @SerializedName("density")
    private Double density;

    /**
     * 密度 (20C)
     */
    @SerializedName("density20")
    private Integer density20;

    /**
     * 当日出库序号
     */
    @SerializedName("tankactId")
    private Integer tankactId;

    /**
     * 到达地
     */
    @SerializedName("partnerName")
    private String partnerName;

    /**
     * 制单时间
     */
    @SerializedName("createTime")
    private String createTime;

    /**
     * 开始时间
     */
    @SerializedName("openTime")
    private String openTime;

    /**
     * 开始日期
     */
    @SerializedName("openDate")
    private String openDate;

    /**
     * 结束时间
     */
    @SerializedName("closeTime")
    private String closeTime;

    /**
     * 结束日期
     */
    @SerializedName("closeDate")
    private String closeDate;

    /**
     * 单位TON
     */
    @SerializedName("uom")
    private String uom;

    /**
     * PDF文档地址
     */
    @SerializedName("documentUrl")
    private String documentUrl;

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getLoginInform() {
        return loginInform;
    }

    public void setLoginInform(String loginInform) {
        this.loginInform = loginInform;
    }

    public String getShippingPointDescr() {
        return shippingPointDescr;
    }

    public void setShippingPointDescr(String shippingPointDescr) {
        this.shippingPointDescr = shippingPointDescr;
    }

    public String getLoadingDate() {
        return loadingDate;
    }

    public void setLoadingDate(String loadingDate) {
        this.loadingDate = loadingDate;
    }

    public String getPartnerDescr() {
        return partnerDescr;
    }

    public void setPartnerDescr(String partnerDescr) {
        this.partnerDescr = partnerDescr;
    }

    public String getVoyageNo() {
        return voyageNo;
    }

    public void setVoyageNo(String voyageNo) {
        this.voyageNo = voyageNo;
    }

    public String getOrderDocNo() {
        return orderDocNo;
    }

    public void setOrderDocNo(String orderDocNo) {
        this.orderDocNo = orderDocNo;
    }

    public String getShippingPoint() {
        return shippingPoint;
    }

    public void setShippingPoint(String shippingPoint) {
        this.shippingPoint = shippingPoint;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductDescr() {
        return productDescr;
    }

    public void setProductDescr(String productDescr) {
        this.productDescr = productDescr;
    }

    public Integer getScheduledVolume() {
        return scheduledVolume;
    }

    public void setScheduledVolume(Integer scheduledVolume) {
        this.scheduledVolume = scheduledVolume;
    }

    public Integer getLoadingVolume() {
        return loadingVolume;
    }

    public void setLoadingVolume(Integer loadingVolume) {
        this.loadingVolume = loadingVolume;
    }

    public Double getAccountingVolume() {
        return accountingVolume;
    }

    public void setAccountingVolume(Double accountingVolume) {
        this.accountingVolume = accountingVolume;
    }

    public String getPriceUom() {
        return priceUom;
    }

    public void setPriceUom(String priceUom) {
        this.priceUom = priceUom;
    }

    public Integer getL15Volume() {
        return l15Volume;
    }

    public void setL15Volume(Integer l15Volume) {
        this.l15Volume = l15Volume;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    public String getFmId() {
        return fmId;
    }

    public void setFmId(String fmId) {
        this.fmId = fmId;
    }

    public Integer getFlowmeterOpening() {
        return flowmeterOpening;
    }

    public void setFlowmeterOpening(Integer flowmeterOpening) {
        this.flowmeterOpening = flowmeterOpening;
    }

    public Integer getFlowmeterClosing() {
        return flowmeterClosing;
    }

    public void setFlowmeterClosing(Integer flowmeterClosing) {
        this.flowmeterClosing = flowmeterClosing;
    }

    public String getTankId() {
        return tankId;
    }

    public void setTankId(String tankId) {
        this.tankId = tankId;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getDensity() {
        return density;
    }

    public void setDensity(Double density) {
        this.density = density;
    }

    public Integer getDensity20() {
        return density20;
    }

    public void setDensity20(Integer density20) {
        this.density20 = density20;
    }

    public Integer getTankactId() {
        return tankactId;
    }

    public void setTankactId(Integer tankactId) {
        this.tankactId = tankactId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }
}
