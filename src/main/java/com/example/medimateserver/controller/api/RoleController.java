package com.example.medimateserver.controller.api;

import com.example.medimateserver.dto.RoleDto;
import com.example.medimateserver.entity.Role; // Assuming you have a Role model
import com.example.medimateserver.service.RoleService; // Assuming you have a RoleService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/role", produces = "application/json")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<RoleDto> getAllRoles() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Integer roleId) {
        RoleDto roleDto = roleService.findById(roleId);
        if (roleDto != null) {
            return ResponseEntity.ok(roleDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto role) {
        RoleDto newRole = roleService.save(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRole);
    }
}
