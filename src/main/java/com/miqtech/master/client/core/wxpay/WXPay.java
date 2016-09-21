package com.miqtech.master.client.core.wxpay;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.miqtech.master.client.Constants;
import com.miqtech.master.client.core.wxpay.utils.MD5Util;
import com.miqtech.master.client.core.Pay;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by xiaoyi on 2016/9/20.
 * 微信支付
 */
public class WXPay extends Pay {
    private PayReq mPayReq;
    private IWXAPI mIWXAPI;

    /**
     * 调起微信支付
     *
     * @param prepayId 微信返回的预支付id
     */
    private void genPayReq(String prepayId) {
        mPayReq = new PayReq();
        mIWXAPI.registerApp(Constants.APP_ID);
        mPayReq.appId = Constants.APP_ID;
        mPayReq.partnerId = Constants.MCH_ID;
        mPayReq.prepayId = prepayId;
        mPayReq.packageValue = "Sign=WXPay";
        mPayReq.nonceStr = genNonceStr();
        mPayReq.timeStamp = String.valueOf(genTimeStamp());
        mPayReq.sign = getParams(mPayReq.appId, mPayReq.partnerId, mPayReq.prepayId, mPayReq.packageValue, mPayReq.nonceStr, mPayReq.timeStamp);
        mIWXAPI.sendReq(mPayReq);

    }

    private String genNonceStr() {
        Random random = new Random();
        return getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private String getMessageDigest(byte[] buffer) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 时间戳
     *
     * @return
     */
    @Nullable
    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    private static String getParams(String appid, String partnerid, String prepayid, String packageValue,
                                    String noncestr, String timestamp) {
        // 构建xml
        Document doc = DocumentHelper.createDocument();
        Element xml = doc.addElement("xml");

        // 添加参数
        Element ele = xml.addElement("appid");
        ele.setText(appid);

        ele = xml.addElement("noncestr");
        ele.setText(noncestr);

        ele = xml.addElement("package");
        ele.setText(packageValue);

        ele = xml.addElement("partnerid");
        ele.setText(partnerid);

        ele = xml.addElement("prepayid");
        ele.setText(prepayid);

        ele = xml.addElement("timestamp");
        ele.setText(timestamp);
        // 获取sign
        String sign = getSign(xml);
        return sign;
    }

    private static String getSign(Element xml) {
        Iterator<Element> it = xml.elementIterator();
        StringBuffer paramStr = new StringBuffer();
        while (it.hasNext()) {
            Element el = it.next();
            if (paramStr.toString() != null && paramStr.toString().length() > 0) {
                paramStr.append("&");
            }
            paramStr.append(el.getName() + "=" + el.getText());
        }
        paramStr.append("&key=" + Constants.APP_KEY);

        String result = MD5Util.MD5Encode(paramStr.toString(), "UTF-8").toUpperCase();

        return result;
    }


    @Override
    public void pay(String prepayID, String shopName, String orderInfo, String price) {
        genPayReq(prepayID);
    }

    @Override
    public void init(Activity activity) {
        mIWXAPI = WXAPIFactory.createWXAPI(activity.getApplicationContext(), Constants.APP_ID);
    }
}
