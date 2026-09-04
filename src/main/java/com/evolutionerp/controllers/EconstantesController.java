package com.evolutionerp.controllers;

import com.evolutionerp.dtos.EconstantesDTO;
import com.evolutionerp.servicesinterfaces.EconstantesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/constantes")
public class EconstantesController {

  private final EconstantesService service;

  public EconstantesController(EconstantesService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<?> listar(@RequestParam(required = false) String codSociedad,
      @RequestParam(required = false) String app) {
    var lista = service.listar(codSociedad, app);
    if (lista.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay constantes registradas");
    return ResponseEntity.ok(lista);
  }

  @GetMapping("/{codSoc}/{cvalor}/{app}")
  public ResponseEntity<?> obtener(@PathVariable String codSoc, @PathVariable String cvalor,
      @PathVariable String app) {
    return ResponseEntity.ok(service.obtener(codSoc, cvalor, app));
  }

  @PostMapping("/nuevo")
  public ResponseEntity<?> crear(@RequestBody EconstantesDTO dto, Authentication auth) {
    if (dto == null || dto.getCodSociedad() == null || dto.getCodSociedad().isBlank()
        || dto.getCvalor() == null || dto.getCvalor().isBlank()
        || dto.getApp() == null || dto.getApp().isBlank()
        || dto.getCnomValor() == null || dto.getCnomValor().isBlank())
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("La sociedad, el valor, la aplicación y el nombre son obligatorios");
    return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto, auth.getName()));
  }

  @PutMapping("/{codSoc}/{cvalor}/{app}")
  public ResponseEntity<?> actualizar(@PathVariable String codSoc, @PathVariable String cvalor,
      @PathVariable String app, @RequestBody EconstantesDTO dto, Authentication auth) {
    service.actualizar(codSoc, cvalor, app, dto, auth.getName());
    return ResponseEntity.ok("Constante actualizada correctamente");
  }

  @DeleteMapping("/{codSoc}/{cvalor}/{app}")
  public ResponseEntity<?> eliminar(@PathVariable String codSoc, @PathVariable String cvalor,
      @PathVariable String app) {
    service.eliminar(codSoc, cvalor, app);
    return ResponseEntity.ok("Constante eliminada correctamente");
  }
}
