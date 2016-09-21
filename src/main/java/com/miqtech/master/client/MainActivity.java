package com.miqtech.master.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.miqtech.master.client.core.alipay.AliPay;
import com.miqtech.master.client.core.PayCallback;
import com.miqtech.master.client.core.PayHelper;
import com.miqtech.master.client.core.wxpay.WXPay;
import com.miqtech.master.client.http.Requestutil;
import com.miqtech.master.client.http.ResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ResponseListener, PayCallback {
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login();
        PayHelper.getInstance().addCallback(this);
        PayHelper.getInstance().setActivity(this);
    }

    public void checkWXPay(View view) {
    }

    public void reqWXPay(View view) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", user.getId());
        params.put("token", user.getToken());
        params.put("body", "支付网费" + "--" + "蜗牛网咖" + "--"
                + 0.01);
        params.put("netbar_id", "101020");
        params.put("origAmount", "0.01");

        params.put("orderType", "0");


        params.put("amount", "0.01");


        params.put("type", 0 + "");

        params.put("isNewAccount", "1");
        Log.e("obj", "paramount == " + params.toString());
        Requestutil.sendPostRequest(Constants.HOST + "pay/orderPay", params, "orderpay", this, "MainActivity");
    }

    private void login() {
        Map<String, String> params = new HashMap<>();
        params.put("username", "13032111821");
        params.put("password", "12345678");
        Requestutil.sendPostRequest(Constants.HOST + "login", params, "login", this, "MainActivity");
    }

    @Override
    public void onSuccess(JSONObject object, String method) {
        try {
            switch (method) {
                case "login":
                    user = new Gson().fromJson(object.getJSONObject("object").toString(), User.class);
                    Log.e("user", "---user----" + user.toString());
                    break;
                case "orderpay":
                    Log.e("user", "---order----" + object.toString());
                    WXPayModel model = new Gson().fromJson(object.getJSONObject("object").toString(), WXPayModel.class);
//                    PayHelper.getInstance().pay(PayHelper.PayType.WXPAY, model.getPrepayId());
                    PayHelper.getInstance().pay(WXPay.class,model.getPrepayId());
                    break;
                case "orderpayALI":
                    Log.e("user", "---order----" + object.toString());
                    AlipayEntity entity = new Gson().fromJson(object.getJSONObject("object").toString(), AlipayEntity.class);
//                    PayHelper.getInstance().pay(PayHelper.PayType.ALIPAY, entity.getOut_trade_no(),this);
                    PayHelper.getInstance().pay(AliPay.class,entity.getOut_trade_no(),"望京科技","买佛山","12.5");
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(String errMsg, String method) {

    }

    @Override
    public void onFaild(JSONObject object, String method) {
        Log.e("tag", "obj   " + object.toString());
    }

    @Override
    public void onCallback(int code) {
        switch (code){
            case 0:
                Toast.makeText(this, "支付成功", 0).show();
                break;
            case -1:
                Toast.makeText(this, "支付失败  " , 0).show();
                break;
            case -2:
                Toast.makeText(this, "他取消啦", 0).show();
                break;
        }
    }

    public void reqAliPay(View view) {

        Map<String, String> params = new HashMap<>();
        params.put("userId", user.getId());
        params.put("token", user.getToken());
        params.put("body", "支付网费" + "--" + "蜗牛网咖" + "--"
                + 0.01);
        params.put("netbar_id", "101020");
        params.put("origAmount", "0.01");

        params.put("orderType", "0");


        params.put("amount", "0.01");


        params.put("type", 1 + "");

        params.put("isNewAccount", "1");
        Log.e("obj", "paramount == " + params.toString());
        Requestutil.sendPostRequest(Constants.HOST + "pay/orderPay", params, "orderpayALI", this, "MainActivity");
    }

    @Override
    protected void onDestroy() {
        PayHelper.getInstance().releaseActivity();
        PayHelper.getInstance().removeCallback(this);
        super.onDestroy();
    }
}
