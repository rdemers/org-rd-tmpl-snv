package com.lq.sdj.pdc.secjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lq.sdj.pdc.secjwt.dto.LoginRequest;
import com.lq.sdj.pdc.secjwt.dto.MessageResponse;
import com.lq.sdj.pdc.secjwt.util.JwtUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/jwt")
@Api(value = "Contrôleur de l'application pour l'authentification/autorisation")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/ping")
    @ApiOperation(value = "Signaler que l'état interne - Simple PING", response = MessageResponse.class)
    public ResponseEntity<MessageResponse> ping() {
        return ResponseEntity.ok(new MessageResponse("Ping !!!"));
    }

    @GetMapping("/token")
    @ApiOperation(value = "Décoder un jeton JWT", response = MessageResponse.class)
    public ResponseEntity<MessageResponse> decode(@RequestParam String token) {
        String clearToken = jwtUtils.decodeJwtToken(token);
        return ResponseEntity.ok(new MessageResponse(clearToken));
    }

    @PostMapping("/token")
    @ApiOperation(value = "Authentifier/Autoriser un usager afin d'obtenir un jeton JWT/API", response = MessageResponse.class)
    public ResponseEntity<MessageResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        return ResponseEntity.ok(new MessageResponse(jwt));
    }
}