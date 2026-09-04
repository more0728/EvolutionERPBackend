package com.evolutionerp.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.evolutionerp.dtos.JwtRequestDTO;
import com.evolutionerp.dtos.JwtResponseDTO;
import com.evolutionerp.dtos.JwtResponseSociedadDTO;
import com.evolutionerp.securities.JwtTokenUtil;
import com.evolutionerp.servicesimplements.JwtUserDetailsService;
import com.evolutionerp.servicesinterfaces.RequisicionService;

@RestController
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    private final RequisicionService requisicionService;

    public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
            JwtUserDetailsService userDetailsService, RequisicionService requisicionService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.requisicionService = requisicionService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCanonical(@RequestBody JwtRequestDTO req) {
        if (req == null || req.getUsername() == null || req.getUsername().isBlank()
                || req.getPassword() == null || req.getPassword().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario y la contraseña son obligatorios");
        }
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario deshabilitado");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
        }
        UserDetails ud = userDetailsService.loadUserByUsername(req.getUsername());
        String token = jwtTokenUtil.generateToken(ud);
        JwtResponseDTO resp = new JwtResponseDTO();
        resp.setJwttoken(token);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody JwtRequestDTO req) {
        if (req == null || req.getUsername() == null || req.getUsername().isBlank()
                || req.getPassword() == null || req.getPassword().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario y la contraseña son obligatorios");
        }
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario deshabilitado");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
        }
        UserDetails ud = userDetailsService.loadUserByUsername(req.getUsername());
        String token = jwtTokenUtil.generateToken(ud);
        List<String> socs = requisicionService.listarCodSociedades();
        JwtResponseSociedadDTO resp = new JwtResponseSociedadDTO();
        resp.setToken(token);
        resp.setUsername(req.getUsername());
        resp.setSociedades(socs);
        resp.setSociedadActual(socs.isEmpty() ? null : socs.get(0));
        return ResponseEntity.ok(resp);
    }
}
