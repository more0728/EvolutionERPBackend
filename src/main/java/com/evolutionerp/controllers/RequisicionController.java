
package com.evolutionerp.controllers;

import com.evolutionerp.dtos.MmRequisCabDTO;
import com.evolutionerp.securities.JwtTokenUtil;
import com.evolutionerp.servicesinterfaces.RequisicionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/requisiciones")
public class RequisicionController {
  private final RequisicionService service;
  private final JwtTokenUtil jwtTokenUtil;

  public RequisicionController(RequisicionService service, JwtTokenUtil jwtTokenUtil) {
    this.service = service;
    this.jwtTokenUtil = jwtTokenUtil;
  }

  private void exigirSociedad(Authentication auth, String codSoc) {
    String token = auth != null && auth.getCredentials() != null
        ? String.valueOf(auth.getCredentials())
        : null;
    String claim = null;
    try {
      claim = token != null ? jwtTokenUtil.getSociedadFromToken(token) : null;
    } catch (Exception e) {
      claim = null;
    }
    if (claim == null || !claim.equals(codSoc))
      throw new ResponseStatusException(HttpStatus.FORBIDDEN,
          "Sociedad del token no coincide con la solicitada");
  }

  @PostMapping
  public ResponseEntity<MmRequisCabDTO> crear(@Valid @RequestBody MmRequisCabDTO dto, Authentication auth) {
    exigirSociedad(auth, dto.getCodSociedad());
    return ResponseEntity.ok(service.crear(dto, auth.getName()));
  }

  @GetMapping("/{codSoc}/{nroDoc}")
  public ResponseEntity<MmRequisCabDTO> obtener(@PathVariable String codSoc, @PathVariable String nroDoc,
      Authentication auth) {
    exigirSociedad(auth, codSoc);
    return ResponseEntity.ok(service.obtener(codSoc, nroDoc));
  }

  @PutMapping("/{codSoc}/{nroDoc}")
  public ResponseEntity<MmRequisCabDTO> actualizar(@PathVariable String codSoc, @PathVariable String nroDoc,
      @Valid @RequestBody MmRequisCabDTO dto, Authentication auth) {
    exigirSociedad(auth, codSoc);
    return ResponseEntity.ok(service.actualizar(codSoc, nroDoc, dto, auth.getName()));
  }

  @PatchMapping("/{codSoc}/{nroDoc}/anular")
  public ResponseEntity<Void> anular(@PathVariable String codSoc, @PathVariable String nroDoc, Authentication auth) {
    exigirSociedad(auth, codSoc);
    service.anular(codSoc, nroDoc, auth.getName());
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{codSoc}/{nroDoc}")
  public ResponseEntity<Void> eliminar(@PathVariable String codSoc, @PathVariable String nroDoc,
      Authentication auth) {
    exigirSociedad(auth, codSoc);
    service.eliminar(codSoc, nroDoc);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public Page<MmRequisCabDTO> listar(@RequestParam String codSociedad, @RequestParam(required = false) String estado,
      @RequestParam(required = false) String cencos, @RequestParam(required = false) String prio,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecIni,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecFin,
      @RequestParam(required = false) String q, Pageable pageable, Authentication auth) {
    exigirSociedad(auth, codSociedad);
    return service.listar(codSociedad, estado, cencos, prio, fecIni, fecFin, q, pageable);
  }
}
