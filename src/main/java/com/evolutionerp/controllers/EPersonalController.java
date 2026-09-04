package com.evolutionerp.controllers;

import com.evolutionerp.dtos.EPersonalDTO;
import com.evolutionerp.servicesinterfaces.EPersonalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/personal")
public class EPersonalController {

  private final EPersonalService service;

  public EPersonalController(EPersonalService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<?> listar(@RequestParam(required = false) String codSociedad) {
    var lista = service.listar(codSociedad);
    if (lista.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay personal registrado");
    return ResponseEntity.ok(lista);
  }

  @GetMapping("/{ccodPerson}")
  public ResponseEntity<?> obtener(@PathVariable String ccodPerson) {
    return ResponseEntity.ok(service.obtener(ccodPerson));
  }

  @PostMapping("/nuevo")
  public ResponseEntity<?> crear(@RequestBody EPersonalDTO dto) {
    if (dto == null || dto.getCcodPerson() == null || dto.getCcodPerson().isBlank()
        || dto.getNomPerson() == null || dto.getNomPerson().isBlank())
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El código y el nombre son obligatorios");
    return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
  }

  @PutMapping("/{ccodPerson}")
  public ResponseEntity<?> actualizar(@PathVariable String ccodPerson, @RequestBody EPersonalDTO dto) {
    service.actualizar(ccodPerson, dto);
    return ResponseEntity.ok("Personal actualizado correctamente");
  }

  @DeleteMapping("/{ccodPerson}")
  public ResponseEntity<?> eliminar(@PathVariable String ccodPerson) {
    service.eliminar(ccodPerson);
    return ResponseEntity.ok("Personal eliminado correctamente");
  }
}
