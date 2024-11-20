package com.example.medimateserver.service.impl;

import com.example.medimateserver.dto.AddressDto;
import com.example.medimateserver.dto.DeviceDto;
import com.example.medimateserver.dto.NotificationDto;
import com.example.medimateserver.entity.Device;
import com.example.medimateserver.entity.Notification;
import com.example.medimateserver.repository.NotificationRepository;
import com.example.medimateserver.service.NotificationService;
import com.example.medimateserver.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationImpl implements NotificationService {
    @Autowired
    NotificationRepository notificationRepository;
    @Override
    public List<NotificationDto> findByIdUser(Integer id) {
        List<Notification> notificationList = notificationRepository.findAllByIdUser(id);
        return notificationList
                .stream()
                .map(notification -> ConvertUtil.gI().toDto(notification, NotificationDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDto save(NotificationDto notificationDto) {
        notificationDto.setStatus(1);
        Notification notificationSaved = notificationRepository.save(ConvertUtil.gI().toEntity(notificationDto, Notification.class));
        return ConvertUtil.gI().toEntity(notificationSaved, NotificationDto.class);
    }

    @Override
    public NotificationDto add(NotificationDto notificationDto) {
        notificationDto.setStatus(2);
        Notification notificationSaved = notificationRepository.save(ConvertUtil.gI().toEntity(notificationDto, Notification.class));
        return ConvertUtil.gI().toEntity(notificationSaved, NotificationDto.class);
    }

    @Override
    public NotificationDto findById(Integer id) {
        Notification notification = notificationRepository.findById(id).get();
        return ConvertUtil.gI().toEntity(notification, NotificationDto.class);
    }

    @Override
    public boolean changeAllStatus(Integer id) {
        try {
            List<Notification> notificationList = notificationRepository.findAllByIdUser(id);
            for (Notification notification: notificationList) {
                notification.setStatus(1);
            }
            notificationRepository.saveAll(notificationList);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
