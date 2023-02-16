package com.spring.service;

import java.security.Key;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.dto.api.LogInUserDto;
import com.spring.dto.jwt.JwtTokensDto;
import com.spring.dto.jwt.RefreshJwtDto;
import com.spring.dto.jwt.TokenDataDto;
import com.spring.dto.model.user.UserDto;
import com.spring.entity.User;
import com.spring.util.ClientType;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtAuthServiceImpl implements JwtAuthService {
    
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private UserService userService;
    
    @Override
    public JwtTokensDto registerUser(UserDto userDto) {
        User user = userService.createUser(userDto);
        
        TokenDataDto tokenDataDto = new TokenDataDto();
        tokenDataDto.setClientType(ClientType.USER);
        tokenDataDto.setUserId(user.getId());
        tokenDataDto.setEmail(user.getEmail());
        tokenDataDto.setUsername(user.getUsername());

        // redis.set(refreshToken)

        return generateJwtTokens(tokenDataDto);
    }

    @Override
    public JwtTokensDto logInUser(LogInUserDto logInUserDto) {
        User user = userService.getUserByUsername(logInUserDto.getUsername());

        bCryptPasswordEncoder.matches(logInUserDto.getPassword(), user.getPassword());

        TokenDataDto tokenDataDto = new TokenDataDto();
        tokenDataDto.setClientType(ClientType.USER);
        tokenDataDto.setUserId(user.getId());
        tokenDataDto.setEmail(user.getEmail());
        tokenDataDto.setUsername(user.getUsername());

        // redis.set(refreshToken)

        return generateJwtTokens(tokenDataDto);
    }

    @Override
    public JwtTokensDto refreshJwt(RefreshJwtDto refreshJwtDto) {
        TokenDataDto tokenDataDto = new TokenDataDto();
        return generateJwtTokens(tokenDataDto);
    }

    private JwtTokensDto generateJwtTokens(TokenDataDto dto) {
        JwtTokensDto tokens = new JwtTokensDto();
        tokens.setAccessToken(generateAccessToken(dto));
        tokens.setRefreshToken(generateRefreshToken(dto));
        return tokens;
    }

    private String generateAccessToken(TokenDataDto dto) {
        Map<String, Object> accessTokenData = new HashMap<>();
        accessTokenData.put("clientType", dto.getClientType());
        accessTokenData.put("userId", dto.getUserId());
        accessTokenData.put("email", dto.getEmail());
        accessTokenData.put("username", dto.getUsername());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 15);

        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setExpiration(calendar.getTime());
        jwtBuilder.setClaims(accessTokenData);
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String accessToken = jwtBuilder.signWith(key).compact();
        return accessToken;
    }

    private String generateRefreshToken(TokenDataDto dto) {
        Map<String, Object> refreshTokenData = new HashMap<>();
        refreshTokenData.put("clientType", dto.getClientType());
        refreshTokenData.put("userId", dto.getUserId());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1440);

        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setExpiration(calendar.getTime());
        jwtBuilder.setClaims(refreshTokenData);
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String refreshToken = jwtBuilder.signWith(key).compact();
        return refreshToken;
    }

}
