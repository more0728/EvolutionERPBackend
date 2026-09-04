package com.evolutionerp.controllers;

import com.evolutionerp.dtos.EcCostoDTO;
import com.evolutionerp.servicesinterfaces.EcCostoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Una entidad, un controller: CRUD completo de EcCosto (familia skill §A1/A5).
@RestController
@RequestMapping("/api/centros-costo")
public class EcCostoController {

  private final EcCostoService service;

  public EcCostoController(EcCostoService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<?> listar(@RequestParam(required = false) String codSociedad) {
    var lista = service.listar(codSociedad);
    if (lista.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay centros de costo registrados");
    return ResponseEntity.ok(lista);
  }

  @GetMapping("/{codSoc}/{ccodCencos}")
  public ResponseEntity<?> obtener(@PathVariable String codSoc, @PathVariable String ccodCencos) {
    return ResponseEntity.ok(service.obtener(codSoc, ccodCencos));
  }

  @PostMapping("/nuevo")
  public ResponseEntity<?> crear(@RequestBody EcCostoDTO dto) {
    if (dto == null || dto.getCodSociedad() == null || dto.getCodSociedad().isBlank()
        || dto.getCcodCencos() == null || dto.getCcodCencos().isBlank()
        || dto.getNomCencos() == null || dto.getNomCencos().isBlank())
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("La sociedad, el código y el nombre son obligatorios");
    return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
  }

  @PutMapping("/{codSoc}/{ccodCencos}")
  public ResponseEntity<?> actualizar(@PathVariable String codSoc, @PathVariable String ccodCencos,
      @RequestBody EcCostoDTO dto) {
    service.actualizar(codSoc, ccodCencos, dto);
    return ResponseEntity.ok("Centro de costo actualizado correctamente");
  }

  @DeleteMapping("/{codSoc}/{ccodCencos}")
  public ResponseEntity<?> eliminar(@PathVariable String codSoc, @PathVariable String ccodCencos) {
    service.eliminar(codSoc, ccodCencos);
    return ResponseEntity.ok("Centro de costo eliminado correctamente");
  }
}
