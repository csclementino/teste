package com.arc.lince.services;

import com.arc.lince.domains.Supervisor;
import com.arc.lince.gateways.SupervisorRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {

    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final SupervisorRepository supervisorRepository;

    public JwtService(PrivateKey privateKey, PublicKey publicKey,SupervisorRepository supervisorRepository) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.supervisorRepository = supervisorRepository;
    }

    public String generateToken(UserDetails user) {
        Optional<Supervisor> usuarioInfo = supervisorRepository.findByEmail(user.getUsername());
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("nome", usuarioInfo.get().getNome())
                .claim("id", usuarioInfo.get().getId())
                .claim("roles", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .setExpiration(new Date(System.currentTimeMillis() + 3600_000))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
