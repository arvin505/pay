package com.miqtech.master.client;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xiaoyi on 2016/9/20.
 */
public class WXPayModel {
    @SerializedName("result_code")
    String resultCode;
    String sign;
    @SerializedName("mch_id")
    String mchId;
    @SerializedName("prepay_id")
    String prepayId;
    @SerializedName("returnMsg")
    String return_msg;
    String appid;
    @SerializedName("nonce_str")
    String nonceStr;
    @SerializedName("return_code")
    String returnCode;
    @SerializedName("trade_type")
    String tradeType;
    String orderId;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
