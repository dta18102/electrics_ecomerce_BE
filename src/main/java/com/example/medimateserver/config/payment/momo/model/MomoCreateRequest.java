package com.example.medimateserver.config.payment.momo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MomoCreateRequest {
    private String orderInfo;
    private int amount;
    private String partnerName;
    private String requestType;
    private String redirectUrl;
    private String ipnUrl;
    private String storeId;
    private String extraData;
    private boolean autoCapture;
    private String signature;
    private String partnerCode;
    private String requestId;
    private String orderId;
    private String lang;
    private long startTime;
    private static MomoCreateRequest momoCreateRequest;
    public static MomoCreateRequest getInstance() {
        if (momoCreateRequest == null) {
            momoCreateRequest = new MomoCreateRequest();
            momoCreateRequest.setOrderInfo("Pay for Medimate orders");
            momoCreateRequest.setPartnerName("test MoMo");
            momoCreateRequest.setRequestType("captureWallet");
            momoCreateRequest.setRedirectUrl("myapp://payment-completed");
            momoCreateRequest.setIpnUrl("https://google.com.vn");
            momoCreateRequest.setStoreId("Medimateserver");
            momoCreateRequest.setExtraData("");
            momoCreateRequest.setAutoCapture(true);
            momoCreateRequest.setPartnerCode("MOMOLRJZ20181206");
            momoCreateRequest.setLang("en");
        }
        return momoCreateRequest;
    }
}
