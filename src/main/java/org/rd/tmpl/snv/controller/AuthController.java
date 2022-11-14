package org.rd.tmpl.snv.controller;

import org.rd.tmpl.snv.dto.LoginRequest;
import org.rd.tmpl.snv.dto.MessageResponse;
import org.rd.tmpl.snv.util.JwtUtils;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/jwt")
@Tag(name = "AuthController", description = "Application controller for authentication/authorization.")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/ping")
    @Operation(summary = "Report internal state - Simple PING.",
               description = "MessageResponse.class")
    public ResponseEntity<MessageResponse> ping() {
        return ResponseEntity.ok(new MessageResponse("Ping !!!"));
    }

    @GetMapping("/token")
    @Operation(summary = "Decode a JWT token.",
               description = "MessageResponse.class")
    public ResponseEntity<MessageResponse> decode(@RequestParam String token) {
        String clearToken = jwtUtils.decodeJwtToken(token);
        return ResponseEntity.ok(new MessageResponse(clearToken));
    }

    @PostMapping("/token")
    @Operation(summary = "Authenticate/Authorize a user in order to obtain a JWT/API token.", 
               description = "MessageResponse.class")
    public ResponseEntity<MessageResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        return ResponseEntity.ok(new MessageResponse(jwt));
    }
}