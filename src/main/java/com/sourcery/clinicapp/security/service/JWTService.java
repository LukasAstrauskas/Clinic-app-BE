package com.sourcery.clinicapp.security.service;

import com.sourcery.clinicapp.user.model.User;
import com.sourcery.clinicapp.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class JWTService {

    @Autowired
    private JwtEncoder encoder;

    @Autowired
    private UserMapper userMapper;


    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        User user = userMapper.findByEmail(authentication.getName()).orElseThrow();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        String type = scope.toLowerCase();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(user.getName())
                .issuedAt(now)
                .expiresAt(now.plus(2, ChronoUnit.DAYS))
                .subject(user.getEmail())
                .id(user.getId().toString())
                .claim("scope", scope)
                .claim("type", type)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
