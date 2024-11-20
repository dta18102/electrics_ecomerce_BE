package com.example.medimateserver.service;

import com.example.medimateserver.dto.TokenDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TokenService {
    List<TokenDto> findAll();
    TokenDto findById(Integer id);
    TokenDto findByIdUser(Integer id);

    TokenDto save(TokenDto TokenDto);

    TokenDto update(Integer id, TokenDto TokenDto);

    void deleteById(Integer id);
}
