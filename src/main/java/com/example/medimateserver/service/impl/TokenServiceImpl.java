package com.example.medimateserver.service.impl;

import com.example.medimateserver.dto.TokenDto;
import com.example.medimateserver.dto.TokenDto;
import com.example.medimateserver.entity.Token;
import com.example.medimateserver.repository.TokenRepository;
import com.example.medimateserver.service.TokenService;
import com.example.medimateserver.util.ConvertUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private TokenRepository tokenRepository; // Sử dụng private cho đúng nguyên tắc

    @Override
    public List<TokenDto> findAll() {
        List<Token> tokenList = tokenRepository.findAll();
        return tokenList
                .stream()
                .map(token -> ConvertUtil.gI().toDto(token, TokenDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TokenDto findById(Integer id) {

        return tokenRepository.findById(id)
                .map(Token -> ConvertUtil.gI().toDto(Token, TokenDto.class))
                .orElse(null);
    }
    @Override
    public TokenDto findByIdUser(Integer id) {

        return tokenRepository.findByIdUser(id)
                .map(Token -> ConvertUtil.gI().toDto(Token, TokenDto.class))
                .orElse(null);
    }

    @Override
    public TokenDto save(TokenDto tokenDto) {
        Token token = ConvertUtil.gI().toEntity(tokenDto, Token.class);
        token.setIdUser(tokenDto.getIdUser());
        token = tokenRepository.save(token);
        return ConvertUtil.gI().toDto(token, TokenDto.class);
    }

    @Override
    public TokenDto update(Integer id, TokenDto tokenDto) {
        Token existingToken = tokenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        Token updatedCategory = tokenRepository.save(existingToken);
        return ConvertUtil.gI().toDto(updatedCategory, TokenDto.class);
    }

    @Override
    public void deleteById(Integer id) {
        tokenRepository.deleteById(id);
    }
}
