package com.example.medimateserver.service.impl;

import com.example.medimateserver.dto.*;
import com.example.medimateserver.dto.CouponDetailDto;
import com.example.medimateserver.entity.*;
import com.example.medimateserver.entity.CouponDetail;
import com.example.medimateserver.repository.CouponDetailRepository; // Giả sử bạn đã tạo Repository này
import com.example.medimateserver.repository.CouponRepository;
import com.example.medimateserver.repository.UserRepository;
import com.example.medimateserver.service.CouponDetailService;
import com.example.medimateserver.service.CouponService;
import com.example.medimateserver.util.ConvertUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CouponDetailServiceImpl implements CouponDetailService {

    @Autowired
    private CouponDetailRepository couponDetailRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CouponRepository couponRepository;

    @Override
    public List<CouponDetail> findAll() {
        List<CouponDetail> couponDetailList = couponDetailRepository.findAll();
        return couponDetailRepository.findAll();
    }

    @Override
    public CouponDetail findById(Integer id) {
        Optional<CouponDetail> couponDetailOptional = couponDetailRepository.findById(id);
        return couponDetailOptional.orElse(null); // Hoặc ném Exception nếu thích hợp
    }

    @Override
    public List<CouponDetailDto> findByUserId(Integer id) {
        List<CouponDetail> couponDetailList = couponDetailRepository.findByIdUser(id);
        return couponDetailList.stream()
                .filter(couponDetail -> couponDetail.getStatus() != 0)
                .map(couponDetail -> ConvertUtil.gI().toDto(couponDetail, CouponDetailDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CouponDetailDto> findByUserIdSatus0(Integer id) {
        List<CouponDetail> couponDetailList = couponDetailRepository.findByIdUser(id);
        return couponDetailList.stream()
                .map(couponDetail -> ConvertUtil.gI().toDto(couponDetail, CouponDetailDto.class))
                .collect(Collectors.toList());
    }


    @Override
    @Transactional // Important for consistency
    public CouponDetailDto save(CouponDetailDto couponDetailDto) {
        Optional<User> userDto = userRepository.findById(couponDetailDto.getIdUser());
        if (!userDto.isPresent()) {
            throw new IllegalArgumentException("Người dùng không hợp lệ");
        }

        Optional<Coupon> couponDto = couponRepository.findById(couponDetailDto.getCoupon().getId());
        if (!couponDto.isPresent()) {
            throw new IllegalArgumentException("Không tìm thấy phiếu giảm giá này");
        }
        Coupon coupon = couponDto.get(); // Unwrap User
        User user = userDto.get(); // Unwrap User

        // Ensure sufficient points
        if (user.getPoint() - coupon.getPoint() < 0) {
            throw new IllegalArgumentException("Điểm người dùng không đủ");
        }

        // Deduct points
        user.setPoint(user.getPoint() - coupon.getPoint());
        userRepository.save(user); // Update user's points

        // Create CouponDetail
        CouponDetail couponDetail = ConvertUtil.gI().toEntity(couponDetailDto, CouponDetail.class);
        couponDetail.setCoupon(coupon); // Ensure ID is set

        Date nowDate = new Date();
        couponDetail.setStartTime(nowDate);

        // Get the expiration time in days
        Integer dayExpire = couponDto.get().getExpirationTime();

        // Create a Calendar instance to manipulate dates
        Calendar calendar = Calendar.getInstance();

        // Set the calendar to the start date (nowDate)
        calendar.setTime(nowDate);

        // Add the specified number of days (dayExpire) to the calendar
        calendar.add(Calendar.DAY_OF_MONTH, dayExpire);

        // Get the date after adding the expiration days
        Date expirationDate = calendar.getTime();

        // Set the endTime of couponDetail to the calculated expiration date
        couponDetail.setEndTime(expirationDate);
        couponDetail.setStatus(1);
        couponDetail = couponDetailRepository.save(couponDetail);
        return ConvertUtil.gI().toDto(couponDetail, CouponDetailDto.class);
    }

    @Override
    public CouponDetailDto update(Integer id, CouponDetailDto CouponDetailDto) {
        CouponDetail existingCouponDetail = couponDetailRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CouponDetail not found with id: " + id));


        CouponDetail updatedCouponDetail = couponDetailRepository.save(existingCouponDetail);
        return ConvertUtil.gI().toDto(updatedCouponDetail, CouponDetailDto.class);
    }

    @Override
    public void deleteById(Integer id) {
        couponDetailRepository.deleteById(id);
    }
}
