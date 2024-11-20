package com.example.medimateserver.service.impl;

import com.example.medimateserver.dto.RoleDto;
import com.example.medimateserver.entity.Role;
import com.example.medimateserver.repository.RoleRepository;
import com.example.medimateserver.service.RoleService;
import com.example.medimateserver.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<RoleDto> findAll() {
        List<Role> roleList = roleRepository.findAll();
        return roleList.stream()
                .map(role -> ConvertUtil.gI().toDto(role, RoleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoleDto findById(Integer id) {

        return roleRepository.findById(id)
                .map(role -> ConvertUtil.gI().toDto(role, RoleDto.class)).orElse(null);
    }

    @Override
    public RoleDto save(RoleDto roleDto) {
        Role role = ConvertUtil.gI().toEntity(roleDto, Role.class);
        role = roleRepository.save(role);
        return ConvertUtil.gI().toDto(role, RoleDto.class);
    }

    @Override
    public RoleDto update(Integer id, RoleDto role) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {}

}
