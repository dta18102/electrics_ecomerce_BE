package com.example.medimateserver.config.payment.momo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MomoCreateRequest {
    private String orderInfo;
    private Integer amount;
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
            momoCreateRequest.setOrderInfo("Payment for EEShop order");
            momoCreateRequest.setPartnerName("test MoMo");
            momoCreateRequest.setRequestType("captureWallet");
            momoCreateRequest.setRedirectUrl(" https://49a3-14-169-255-212.ngrok-free.app/cart");
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
