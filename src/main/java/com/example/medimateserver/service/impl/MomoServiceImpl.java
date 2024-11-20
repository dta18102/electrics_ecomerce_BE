package com.example.medimateserver.service.impl;

import com.example.medimateserver.config.payment.momo.model.MomoCreateRequest;
import com.example.medimateserver.config.payment.momo.shared.Encoder;
import com.example.medimateserver.config.payment.momo.shared.Parameter;
import com.example.medimateserver.service.MomoService;
import com.example.medimateserver.util.GsonUtil;
import org.springframework.stereotype.Service;

@Service
public class MomoServiceImpl implements MomoService {
    @Override
    public String getPayUrl(MomoCreateRequest momoCreateRequest) {
        return null;
    }

    @Override
    public String getStatusOrder() {
        return null;
    }
}
