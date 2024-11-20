package com.example.medimateserver.config.payment.momo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MomoCreateResponse {
    private String partnerCode;
    private String orderId;
    private String requestId;
    private long amount;
    private long responseTime;
    private String message;
    private int resultCode;
    private String payUrl;
    private String deeplink;
    private String qrCodeUrl;
    private String applink;
    private String deeplinkMiniApp;
    public boolean isSuccessful() {
        return resultCode == 0;
    }

}
