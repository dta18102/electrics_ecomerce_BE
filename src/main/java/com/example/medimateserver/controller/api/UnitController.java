package com.example.medimateserver.controller.api;

import com.example.medimateserver.dto.UnitDto;
import com.example.medimateserver.service.UnitService;
import com.example.medimateserver.util.GsonUtil;
import com.example.medimateserver.util.ResponseUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/unit", produces = "application/json")
public class UnitController {
    @Autowired
    UnitService unitService;
    @GetMapping
    public ResponseEntity<?> getAllUnit(@PathVariable Integer id, HttpServletRequest request) throws JsonProcessingException {
        try {
            List<UnitDto> UnitList = unitService.findAll();
            String jsons = GsonUtil.gI().toJson(UnitList);
            return ResponseUtil.success(jsons);
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }

    }

    // Create a new Unit
    @PostMapping
    public ResponseEntity<UnitDto> createUnit(@RequestBody UnitDto UnitDto) {
        UnitDto savedUnit = unitService.save(UnitDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUnit);
    }

    // Update a Unit
    @PutMapping
    public ResponseEntity<String> updateUnit(@PathVariable Integer id, @RequestBody UnitDto Unit) {
        return ResponseEntity.ok("");
    }

    // Delete a Unit
    @DeleteMapping
    public ResponseEntity<?> deleteUnit(@PathVariable Integer id) {
        try {
            UnitDto Unit = unitService.findById(id);
            unitService.save(Unit);
            return ResponseUtil.success();
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }
}
