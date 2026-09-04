package com.evolutionerp.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evolutionerp.dtos.AuthSociedadRequest;
import com.evolutionerp.dtos.JwtResponseSociedadDTO;
import com.evolutionerp.securities.JwtTokenUtil;
import com.evolutionerp.servicesinterfaces.RequisicionService;


@RestController
@RequestMapping("/api/auth")
public class AuthSociedadController {

    private final RequisicionService requisicionService;
    private final JwtTokenUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public AuthSociedadController(RequisicionService requisicionService, JwtTokenUtil jwtUtil,
            UserDetailsService userDetailsService) {
        this.requisicionService = requisicionService;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/sociedad")
    public ResponseEntity<?> seleccionar(@RequestBody AuthSociedadRequest req, Authentication auth) {
        if (req == null || req.getCodSociedad() == null || req.getCodSociedad().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El código de sociedad es obligatorio");
        }
        String codSoc = req.getCodSociedad();
        if (!requisicionService.existeSociedad(codSoc)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sociedad no encontrada");
        }
        UserDetails ud = userDetailsService.loadUserByUsername(auth.getName());
        String token = jwtUtil.generateTokenWithSociedad(ud, codSoc);
        List<String> socs = requisicionService.listarCodSociedades();
        JwtResponseSociedadDTO resp = new JwtResponseSociedadDTO();
        resp.setToken(token);
        resp.setUsername(auth.getName());
        resp.setSociedades(socs);
        resp.setSociedadActual(codSoc);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/sociedades")
    public ResponseEntity<?> sociedades() {
        List<String> socs = requisicionService.listarCodSociedades();
        if (socs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay sociedades registradas");
        }
        return ResponseEntity.ok(socs);
    }
}
