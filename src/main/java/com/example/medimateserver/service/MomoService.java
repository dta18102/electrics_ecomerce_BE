package com.example.medimateserver.service;

import com.example.medimateserver.config.payment.momo.model.MomoCreateRequest;

public interface MomoService {
    String getPayUrl(MomoCreateRequest momoCreateRequest);
    String getStatusOrder();
}
