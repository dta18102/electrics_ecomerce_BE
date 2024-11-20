package com.example.medimateserver.controller.web;

import com.example.medimateserver.config.jwt.JwtProvider;
import com.example.medimateserver.config.payment.momo.model.MomoCreateRequest;
import com.example.medimateserver.config.payment.momo.model.MomoCreateResponse;
import com.example.medimateserver.config.payment.momo.shared.Encoder;
import com.example.medimateserver.config.payment.momo.shared.Parameter;
import com.example.medimateserver.service.UserService;
import com.example.medimateserver.util.GsonUtil;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


@RestController
public class Test {
    @Autowired
    UserService userService;
    @Autowired
    JwtProvider jwtProvider;

    @GetMapping("/test")
    public String test() throws Exception {
        MomoCreateRequest.getInstance().setAmount(100000);
        MomoCreateRequest.getInstance().setRequestId(String.valueOf(System.currentTimeMillis()));
        MomoCreateRequest.getInstance().setOrderId(String.valueOf(System.currentTimeMillis()));
        MomoCreateRequest.getInstance().setStartTime(System.currentTimeMillis());
        String requestRawData = new StringBuilder()
                .append(Parameter.ACCESS_KEY).append("=").append(Parameter.DEV_ACCESS_KEY).append("&")
                .append(Parameter.AMOUNT).append("=").append(Long.toString(MomoCreateRequest.getInstance().getAmount())).append("&")
                .append(Parameter.EXTRA_DATA).append("=").append("").append("&")
                .append(Parameter.IPN_URL).append("=").append(MomoCreateRequest.getInstance().getIpnUrl()).append("&")
                .append(Parameter.ORDER_ID).append("=").append(MomoCreateRequest.getInstance().getOrderId()).append("&")
                .append(Parameter.ORDER_INFO).append("=").append(MomoCreateRequest.getInstance().getOrderInfo()).append("&")
                .append(Parameter.PARTNER_CODE).append("=").append(MomoCreateRequest.getInstance().getPartnerCode()).append("&")
                .append(Parameter.REDIRECT_URL).append("=").append(MomoCreateRequest.getInstance().getRedirectUrl()).append("&")
                .append(Parameter.REQUEST_ID).append("=").append(MomoCreateRequest.getInstance().getRequestId()).append("&")
                .append(Parameter.REQUEST_TYPE).append("=").append(MomoCreateRequest.getInstance().getRequestType())
                .toString();
        String signRequest = Encoder.signHmacSHA256(requestRawData, Parameter.DEV_SECRET_KEY);
        MomoCreateRequest.getInstance().setSignature(signRequest);
        String payload = GsonUtil.gI().toJson(MomoCreateRequest.getInstance());
        MomoCreateResponse momoCreateResponse = GsonUtil.gI().fromJson(sendRequest(payload), MomoCreateResponse.class);
        System.out.println(momoCreateResponse.getPayUrl());
        return sendRequest(payload) +"\n\n" + GsonUtil.gI().toJson(momoCreateResponse).replaceAll("\\\\u003d", "=");
    }
    public static String sendRequest(String payload) throws IOException {
        URL url = new URL("https://test-payment.momo.vn/v2/gateway/api/create");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(payload.getBytes());
        }

        int responseCode = connection.getResponseCode();
        String responseBody = "";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String input;
            while ((input = br.readLine()) != null) {
                responseBody += input;
            }
        }

        return responseBody;
    }



}
