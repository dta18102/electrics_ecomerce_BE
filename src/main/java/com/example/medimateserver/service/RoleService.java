package com.example.medimateserver.service;

import com.example.medimateserver.dto.RoleDto;
import com.example.medimateserver.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RoleService {

    List<RoleDto> findAll();

    RoleDto findById(Integer id);

    RoleDto save(RoleDto role);

    RoleDto update(Integer id, RoleDto role);

    void deleteById(Integer id);
}
