package com.evolutionerp.controllers;

import com.evolutionerp.dtos.EnumRangosDTO;
import com.evolutionerp.servicesinterfaces.EnumRangosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Una entidad, un controller: CRUD completo de EnumRangos (familia skill §A1/A5).
@RestController
@RequestMapping("/api/rangos")
public class EnumRangosController {

  private final EnumRangosService service;

  public EnumRangosController(EnumRangosService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<?> listar(@RequestParam(required = false) String codSociedad) {
    var lista = service.listar(codSociedad);
    if (lista.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay rangos registrados");
    return ResponseEntity.ok(lista);
  }

  @GetMapping("/{codSoc}/{app}")
  public ResponseEntity<?> obtener(@PathVariable String codSoc, @PathVariable String app) {
    return ResponseEntity.ok(service.obtener(codSoc, app));
  }

  @PostMapping("/nuevo")
  public ResponseEntity<?> crear(@RequestBody EnumRangosDTO dto) {
    if (dto == null || dto.getCodSociedad() == null || dto.getCodSociedad().isBlank()
        || dto.getApp() == null || dto.getApp().isBlank() || dto.getUltimoNum() == null)
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("La sociedad, la aplicación y el último número son obligatorios");
    return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
  }

  @PutMapping("/{codSoc}/{app}")
  public ResponseEntity<?> actualizar(@PathVariable String codSoc, @PathVariable String app,
      @RequestBody EnumRangosDTO dto) {
    service.actualizar(codSoc, app, dto);
    return ResponseEntity.ok("Rango actualizado correctamente");
  }

  @DeleteMapping("/{codSoc}/{app}")
  public ResponseEntity<?> eliminar(@PathVariable String codSoc, @PathVariable String app) {
    service.eliminar(codSoc, app);
    return ResponseEntity.ok("Rango eliminado correctamente");
  }
}
