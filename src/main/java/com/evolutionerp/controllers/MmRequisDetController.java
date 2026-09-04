package com.evolutionerp.controllers;

import com.evolutionerp.dtos.MmRequisDetDTO;
import com.evolutionerp.servicesinterfaces.MmRequisDetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Una entidad, un controller: CRUD completo de MmRequisDet (familia skill §A1/A5).
// Clave compuesta (codSociedad, nroDoc, nroItem): el padre va en el path,
// el nroItem se asigna correlativo al crear.
@RestController
@RequestMapping("/api/requisicion-detalles")
public class MmRequisDetController {

  private final MmRequisDetService service;

  public MmRequisDetController(MmRequisDetService service) {
    this.service = service;
  }

  @GetMapping("/{codSoc}/{nroDoc}")
  public ResponseEntity<?> listar(@PathVariable String codSoc, @PathVariable String nroDoc) {
    var lista = service.listarPorDocumento(codSoc, nroDoc);
    if (lista.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay detalles registrados");
    return ResponseEntity.ok(lista);
  }

  @GetMapping("/{codSoc}/{nroDoc}/{nroItem}")
  public ResponseEntity<?> obtener(@PathVariable String codSoc, @PathVariable String nroDoc,
      @PathVariable Long nroItem) {
    return ResponseEntity.ok(service.obtener(codSoc, nroDoc, nroItem));
  }

  @PostMapping("/{codSoc}/{nroDoc}/nuevo")
  public ResponseEntity<?> crear(@PathVariable String codSoc, @PathVariable String nroDoc,
      @Valid @RequestBody MmRequisDetDTO dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(codSoc, nroDoc, dto));
  }

  @PutMapping("/{codSoc}/{nroDoc}/{nroItem}")
  public ResponseEntity<?> actualizar(@PathVariable String codSoc, @PathVariable String nroDoc,
      @PathVariable Long nroItem, @RequestBody MmRequisDetDTO dto) {
    service.actualizar(codSoc, nroDoc, nroItem, dto);
    return ResponseEntity.ok("Detalle actualizado correctamente");
  }

  @DeleteMapping("/{codSoc}/{nroDoc}/{nroItem}")
  public ResponseEntity<?> eliminar(@PathVariable String codSoc, @PathVariable String nroDoc,
      @PathVariable Long nroItem) {
    service.eliminar(codSoc, nroDoc, nroItem);
    return ResponseEntity.ok("Detalle eliminado correctamente");
  }
}
