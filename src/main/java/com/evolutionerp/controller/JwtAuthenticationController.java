
package com.evolutionerp.controller;
import com.evolutionerp.dto.JwtRequestDTO;
import com.evolutionerp.dto.JwtResponseSociedadDTO;
import com.evolutionerp.repo.EsociedadRepo;
import com.evolutionerp.security.JwtTokenUtil;
import com.evolutionerp.impl.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;
@RestController @CrossOrigin @RequiredArgsConstructor
public class JwtAuthenticationController {
  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  private final JwtUserDetailsService userDetailsService;
  private final EsociedadRepo socRepo;
  @PostMapping({"/login","/api/auth/login"})
  public ResponseEntity<JwtResponseSociedadDTO> login(@RequestBody JwtRequestDTO req) throws Exception {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
    UserDetails ud=userDetailsService.loadUserByUsername(req.getUsername());
    String token=jwtTokenUtil.generateToken(ud);
    var socs=socRepo.findAll().stream().map(s->s.getCodSociedad()).collect(Collectors.toList());
    return ResponseEntity.ok(new JwtResponseSociedadDTO(token, req.getUsername(), socs, socs.isEmpty()?null:socs.get(0)));
  }
}
