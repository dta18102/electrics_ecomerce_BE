package com.example.medimateserver.config.jwt;

import com.example.medimateserver.dto.TokenDto;
import com.example.medimateserver.dto.UserDto;
import com.example.medimateserver.service.TokenService;
import com.example.medimateserver.util.CheckAuthUtil;
import com.example.medimateserver.util.GsonUtil;
import com.example.medimateserver.util.MLogger;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

@Component
@Data
public class JwtProvider {
    @Value("${accessTime}")
    private long accessTime;
    @Value("${refreshTime}")
    private long refreshTime;
    @Value("${secretKey}")
    private String secretKey;

    private static JwtProvider jwtProvider;
    public static JwtProvider gI() {
        if (jwtProvider == null) {
            jwtProvider = new JwtProvider();
        }
        return jwtProvider;
    }
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Sử dụng khóa mới tạo
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    public String generateToken(String username) {
        System.out.println(secretKey + " sc");
        System.out.println(initializeKey() + " sm");
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTime)) // 1 day
                .signWith(SignatureAlgorithm.HS256, initializeKey())
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTime)) // 1 day
                .signWith(SignatureAlgorithm.HS256, initializeKey())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        String result = "";
        try {
            result = Jwts.parser()
                    .setSigningKey(initializeKey())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception ex) {
            result = "";
        }
        return result;
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            Jwts.parser().setSigningKey(initializeKey()).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            MLogger.LOGGER.severe("Invalid JWT signature: {}" + ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token: {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty: {}", ex.getMessage());
        }
        return false;
    }
    public boolean verifyToken(String accessToken, TokenDto tokenDto) {
        try {
            if (!tokenDto.getAccessToken().equals(accessToken)) {
                return false;
            }
            Claims claims = Jwts.parser()
                    .setSigningKey(initializeKey())
                    .parseClaimsJws(accessToken)
                    .getBody();

            // Lấy thông tin từ token
            String username = claims.getSubject();
            Date issuedAtDate = claims.getIssuedAt();
            Date expirationDate = claims.getExpiration();

            // Định dạng ngày tháng
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String issuedAt = dateFormat.format(issuedAtDate);
            String expiration = dateFormat.format(expirationDate);

            // Kiểm tra thời gian hết hạn
            if (expirationDate.before(new Date())) {
                return false;
            } else {
                return true;
            }

        } catch (ExpiredJwtException e) {
            return false;
        } catch (JwtException e) {
            System.out.println("Lỗi giải mã token: " + e.getMessage());
            return false;
        }
    }
    public boolean verifyRefreshToken(String refreshToken, TokenDto tokenDto) {
        try {
            if (!tokenDto.getRefreshToken().equals(refreshToken)) {
                return false;
            }
            Claims claims = Jwts.parser()
                    .setSigningKey(initializeKey())
                    .parseClaimsJws(refreshToken)
                    .getBody();

            // Lấy thông tin từ token
            String username = claims.getSubject();
            Date issuedAtDate = claims.getIssuedAt();
            Date expirationDate = claims.getExpiration();

            // Định dạng ngày tháng
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String issuedAt = dateFormat.format(issuedAtDate);
            String expiration = dateFormat.format(expirationDate);

            // Kiểm tra thời gian hết hạn
            if (expirationDate.before(new Date())) {
                return false;
            } else {
                return true;
            }

        } catch (ExpiredJwtException e) {
            return false;
        } catch (JwtException e) {
            return false;
        }
    }

    private Key initializeKey() {
        byte[] decodedSecretKey = Base64.getDecoder().decode(secretKey);  // Decode base64-encoded secret
        return Keys.hmacShaKeyFor(decodedSecretKey);  // Create secure Key object
    }

}