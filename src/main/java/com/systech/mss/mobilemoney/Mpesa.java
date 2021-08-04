package com.systech.mss.mobilemoney;

import com.systech.mss.seurity.DateUtils;
import lombok.Getter;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;

@Getter
public class Mpesa {
    private String appKey;
    private String appSecret;
    private boolean isLive;

    private String TOKEN_URL,
            STK_PUSH_URL,
            STK_STATUS_URL,
            CommandID;

    public Mpesa(String app_key, String app_secret, boolean isLive) {
        this.appKey = app_key;
        this.appSecret = app_secret;
        this.isLive = isLive;

        setConfigs();
    }

    void setConfigs() {
        if (this.isLive) {
            this.TOKEN_URL = "https://api.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials";
            this.STK_PUSH_URL = "https://api.safaricom.co.ke/mpesa/stkpush/v1/processrequest";
            this.STK_STATUS_URL = "https://api.safaricom.co.ke/mpesa/stkpushquery/v1/query";
        } else {
            this.TOKEN_URL = "https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials";
            this.STK_PUSH_URL = "https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest";
            this.STK_STATUS_URL = "https://sandbox.safaricom.co.ke/mpesa/stkpushquery/v1/query";
        }
        this.CommandID = "CustomerPayBillOnline";
    }

    public String authenticate() throws IOException, JSONException {

        String appKeySecret = appKey + ":" + appSecret;
        byte[] bytes = appKeySecret.getBytes("ISO-8859-1");
        String encoded = Base64.getEncoder().encodeToString(bytes);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(getTOKEN_URL())
                .get()
                .addHeader("authorization", "Basic " + encoded)
                .addHeader("cache-control", "no-cache")

                .build();

        Response response = client.newCall(request).execute();
        String s = response.body().string();
//        System.out.println(s);
        JSONObject jsonObject = new JSONObject(s);
        String token = jsonObject.getString("access_token");
        System.out.println(token);
        return token;
    }

    public String STKPushSimulation(String businessShortCode, String password, String timestamp, String amount, String phoneNumber, String partyA, String partyB, String callBackURL, String queueTimeOutURL, String accountReference, String transactionDesc, MpesaCallBack mpesaCallBack) throws IOException, JSONException {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("BusinessShortCode", businessShortCode);
        jsonObject.put("Password", password);
        jsonObject.put("Timestamp", timestamp);
        jsonObject.put("TransactionType", getCommandID());
        jsonObject.put("Amount", amount);
        jsonObject.put("PhoneNumber", phoneNumber);
        jsonObject.put("PartyA", partyA);
        jsonObject.put("PartyB", partyB);
        jsonObject.put("CallBackURL", callBackURL);
        jsonObject.put("AccountReference", accountReference);
        jsonObject.put("QueueTimeOutURL", queueTimeOutURL);
        jsonObject.put("TransactionDesc", transactionDesc);

        jsonArray.put(jsonObject);

        String requestJson = jsonArray.toString().replaceAll("[\\[\\]]", "");

        OkHttpClient client = new OkHttpClient();
        String url = getSTK_PUSH_URL();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestJson);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer " + authenticate())
                .addHeader("cache-control", "no-cache")
                .build();


        Response response = client.newCall(request).execute();
        String s = response.body().string();
        System.out.println(s);
        if (mpesaCallBack != null) {
            return (String) mpesaCallBack.start(s);
        }
        return "Please try again";
    }

    public String STKPushTransactionStatus(String businessShortCode, String password, String timestamp, String checkoutRequestID, MpesaCallBack mpesaCallBack) throws IOException, JSONException {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("BusinessShortCode", businessShortCode);
        jsonObject.put("Password", password);
        jsonObject.put("Timestamp", timestamp);
        jsonObject.put("CheckoutRequestID", checkoutRequestID);
        jsonArray.put(jsonObject);
        String requestJson = jsonArray.toString().replaceAll("[\\[\\]]", "");
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestJson);
        Request request = new Request.Builder()
                .url(getSTK_STATUS_URL())
                .post(body)
                .addHeader("authorization", "Bearer " + authenticate())
                .addHeader("content-type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        String s = response.body().string();
        if (mpesaCallBack != null) {
            return (String) mpesaCallBack.start(s);
        }
        System.out.println(s);
        return "PLease try again";

    }

    /**
     * @param shortCode
     * @param passKey   Eg bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919
     * @return
     */
    public static String getPassword(String shortCode, String passKey, String timeStamp) {
//        Shortcode+Passkey+Timestamp (eg 174379bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c91920181015123520)
        String originalInput = String.format("%s%s%s",
                shortCode, passKey, timeStamp);
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
//        System.out.println(encodedString);
//        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
//        String decodedString = new String(decodedBytes);
//        System.out.println(decodedString);
        return encodedString;
    }

    public static void main(String[] args) {
        getPassword("174379", "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919", DateUtils.getTimestamp());
    }

    /**
     * http://129.159.250.225:8085/mss/resources/api/mpesaCallBack
     * http://129.159.250.225:8085/mss/resources/api/mpesaTimeoutCallBack
     */

}
