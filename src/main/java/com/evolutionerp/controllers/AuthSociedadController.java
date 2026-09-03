
package com.evolutionerp.controllers;

import com.evolutionerp.dtos.AuthSociedadRequest;
import com.evolutionerp.dtos.JwtResponseSociedadDTO;
import com.evolutionerp.repositories.EsociedadRepo;
import com.evolutionerp.securities.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthSociedadController {
  private final EsociedadRepo socRepo;
  private final JwtTokenUtil jwtUtil;
  private final UserDetailsService userDetailsService;

  @PostMapping("/sociedad")
  public ResponseEntity<JwtResponseSociedadDTO> seleccionar(@RequestBody AuthSociedadRequest req, Authentication auth) {
    String codSoc = req.getCodSociedad();
    if (!socRepo.existsById(codSoc))
      return ResponseEntity.badRequest().build();
    UserDetails ud = userDetailsService.loadUserByUsername(auth.getName());
    String token = jwtUtil.generateTokenWithSociedad(ud, codSoc);
    List<String> socs = socRepo.findAll().stream().map(s -> s.getCodSociedad()).collect(Collectors.toList());
    return ResponseEntity.ok(new JwtResponseSociedadDTO(token, auth.getName(), socs, codSoc));
  }

  @GetMapping("/sociedades")
  public ResponseEntity<List<String>> sociedades() {
    return ResponseEntity.ok(socRepo.findAll().stream().map(s -> s.getCodSociedad()).collect(Collectors.toList()));
  }
}
