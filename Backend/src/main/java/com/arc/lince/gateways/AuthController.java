package com.arc.lince.gateways;


import com.arc.lince.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestHeader("Authorization") String basicAuth) {

        var decoded = new String(Base64.getDecoder().decode(basicAuth.substring(6)));
        var parts = decoded.split(":");
        String username = parts[0];
        String password = parts[1];

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        UserDetails user = (UserDetails) auth.getPrincipal();
        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(Map.of(
                "access_token", token
        ));
    }

}
