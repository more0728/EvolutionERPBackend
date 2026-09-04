package com.evolutionerp.controllers;

import com.evolutionerp.dtos.MmaterialDTO;
import com.evolutionerp.servicesinterfaces.MmaterialService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Una entidad, un controller: CRUD completo de Mmaterial (familia skill §A1/A5).
@RestController
@RequestMapping("/api/materiales")
public class MmaterialController {

  private final MmaterialService service;

  public MmaterialController(MmaterialService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<?> listar(@RequestParam(required = false) String q) {
    var lista = service.listar(q);
    if (lista.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay materiales registrados");
    return ResponseEntity.ok(lista);
  }

  @GetMapping("/{codMaterial}")
  public ResponseEntity<?> obtener(@PathVariable String codMaterial) {
    return ResponseEntity.ok(service.obtener(codMaterial));
  }

  @PostMapping("/nuevo")
  public ResponseEntity<?> crear(@RequestBody MmaterialDTO dto) {
    if (dto == null || dto.getCodMaterial() == null || dto.getCodMaterial().isBlank()
        || dto.getNomMaterial() == null || dto.getNomMaterial().isBlank()
        || dto.getCUnidad() == null || dto.getCUnidad().isBlank())
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("El código, el nombre y la unidad son obligatorios");
    return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
  }

  @PutMapping("/{codMaterial}")
  public ResponseEntity<?> actualizar(@PathVariable String codMaterial, @RequestBody MmaterialDTO dto) {
    service.actualizar(codMaterial, dto);
    return ResponseEntity.ok("Material actualizado correctamente");
  }

  @DeleteMapping("/{codMaterial}")
  public ResponseEntity<?> eliminar(@PathVariable String codMaterial) {
    service.eliminar(codMaterial);
    return ResponseEntity.ok("Material eliminado correctamente");
  }
}
