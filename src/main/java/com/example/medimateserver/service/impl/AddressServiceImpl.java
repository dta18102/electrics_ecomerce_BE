package com.example.medimateserver.service.impl;

import com.example.medimateserver.dto.AddressDto;
import com.example.medimateserver.dto.CouponDetailDto;
import com.example.medimateserver.entity.Address;
import com.example.medimateserver.entity.CouponDetail;
import com.example.medimateserver.repository.AddressRepository;
import com.example.medimateserver.service.AddressService;
import com.example.medimateserver.util.ConvertUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Override
    public List<AddressDto> findAll() {
        List<Address> AddressList = addressRepository.findAll();
        return AddressList
                .stream()
                .filter(address -> address.getStatus() != 0) // Filter out addresses with status 0
                .map(Address -> ConvertUtil.gI().toDto(Address, AddressDto.class))
                .collect(Collectors.toList());
    }
    @Override
    public AddressDto findById(Integer id) {
        return addressRepository.findById(id)
                .map(Address -> {
                    if (Address.getStatus() == 0) {
                        throw new IllegalArgumentException("Địa chỉ không hợp lệ: Trạng thái không thể bằng 0");
                    }
                    return ConvertUtil.gI().toDto(Address, AddressDto.class);
                })
                .orElseThrow(() -> new EntityNotFoundException("We not found with id: " + id));
    }

    @Override
    public AddressDto findByIsDefaultTrue(Integer idUser) {
        return ConvertUtil.gI().toDto(addressRepository.findByIsDefaultTrue(idUser), AddressDto.class);
    }

    @Override
    public AddressDto save(Integer id, AddressDto addressDto) {
        Address savedAddress = ConvertUtil.gI().toEntity(addressDto, Address.class);
        savedAddress.setStatus(1);
        savedAddress = addressRepository.save(savedAddress);
        if (addressDto.getIsDefault()) {
            List<Address> addresses = addressRepository.findByIdUser(id);
            for (Address address : addresses) {
                address.setIsDefault(false);
                if (address.getId() == savedAddress.getId()) {
                    address.setIsDefault(true);
                }
            }
            addressRepository.saveAll(addresses);
        }
        return ConvertUtil.gI().toDto(savedAddress, AddressDto.class);
    }

    @Override
    public AddressDto update(Integer id, AddressDto addressDto) {
        Address existingAddress = addressRepository.findById(addressDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("We not found with id: " + addressDto.getId()));
        // Kiểm tra status của bản ghi hiện tại
        if (existingAddress.getStatus() == 0) {
            throw new IllegalArgumentException("Địa chỉ không hợp lệ: Trạng thái không thể bằng 0");
        }
        // Kiểm tra isDefault của addressDto
        if (addressDto.getIsDefault()) {
            Address updatedAddress = null;
            List<Address> addresses = addressRepository.findByIdUser(id);
            for (Address address : addresses) {
                address.setIsDefault(false);
                if (address.getId().equals(addressDto.getId())) {
                    BeanUtils.copyProperties(addressDto, address);
                    updatedAddress = address;
                }
            }
            addressRepository.saveAll(addresses);
            return ConvertUtil.gI().toDto(updatedAddress, AddressDto.class);
        }
        // Cập nhật dữ liệu cho Address hiện tại (có thể dùng BeanUtils.copyProperties)
        BeanUtils.copyProperties(addressDto, existingAddress);
        Address updatedAddress = addressRepository.save(existingAddress);
        return ConvertUtil.gI().toDto(updatedAddress, AddressDto.class);
    }
    @Override
    public void deleteById(Integer id) {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("We not found with id: " + id));
        existingAddress.setStatus(0);
        addressRepository.save(existingAddress);
    }

    @Override
    public List<AddressDto> findByIdUser(Integer id) {
        List<Address> addressList = addressRepository.findByIdUser(id);
        return addressList
                .stream()
                .filter(address -> address.getStatus() != 0) // Filter out addresses with status 0
                .map(Address -> ConvertUtil.gI().toDto(Address, AddressDto.class))
                .collect(Collectors.toList());
    }
}
