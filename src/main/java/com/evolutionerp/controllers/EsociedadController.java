package com.evolutionerp.controllers;

import com.evolutionerp.dtos.EsociedadDTO;
import com.evolutionerp.servicesinterfaces.EsociedadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/sociedades")
public class EsociedadController {

  private final EsociedadService service;

  public EsociedadController(EsociedadService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<?> listar() {
    var lista = service.listar();
    if (lista.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay sociedades registradas");
    return ResponseEntity.ok(lista);
  }

  @GetMapping("/{codSoc}")
  public ResponseEntity<?> obtener(@PathVariable String codSoc) {
    return ResponseEntity.ok(service.obtener(codSoc));
  }

  @PostMapping("/nuevo")
  public ResponseEntity<?> crear(@RequestBody EsociedadDTO dto, Authentication auth) {
    if (dto == null || dto.getCodSociedad() == null || dto.getCodSociedad().isBlank()
        || dto.getNomSociedad() == null || dto.getNomSociedad().isBlank()
        || dto.getNitSociedad() == null || dto.getNitSociedad().isBlank())
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("El código, el nombre y el NIT de la sociedad son obligatorios");
    return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto, auth.getName()));
  }

  @PutMapping("/{codSoc}")
  public ResponseEntity<?> actualizar(@PathVariable String codSoc, @RequestBody EsociedadDTO dto,
      Authentication auth) {
    service.actualizar(codSoc, dto, auth.getName());
    return ResponseEntity.ok("Sociedad actualizada correctamente");
  }

  @DeleteMapping("/{codSoc}")
  public ResponseEntity<?> eliminar(@PathVariable String codSoc) {
    service.eliminar(codSoc);
    return ResponseEntity.ok("Sociedad eliminada correctamente");
  }
}
