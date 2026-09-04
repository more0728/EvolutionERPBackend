package com.evolutionerp.controllers;

import com.evolutionerp.dtos.BProveedorDTO;
import com.evolutionerp.servicesinterfaces.BProveedorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Una entidad, un controller: CRUD completo de BProveedor (familia skill §A1/A5).
@RestController
@RequestMapping("/api/proveedores")
public class BProveedorController {

  private final BProveedorService service;

  public BProveedorController(BProveedorService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<?> listar(@RequestParam(required = false) String q) {
    var lista = service.listar(q);
    if (lista.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay proveedores registrados");
    return ResponseEntity.ok(lista);
  }

  @GetMapping("/{ccodProveedor}")
  public ResponseEntity<?> obtener(@PathVariable String ccodProveedor) {
    return ResponseEntity.ok(service.obtener(ccodProveedor));
  }

  @PostMapping("/nuevo")
  public ResponseEntity<?> crear(@RequestBody BProveedorDTO dto) {
    if (dto == null || dto.getCcodProveedor() == null || dto.getCcodProveedor().isBlank()
        || dto.getNomProv() == null || dto.getNomProv().isBlank())
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El código y el nombre son obligatorios");
    return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
  }

  @PutMapping("/{ccodProveedor}")
  public ResponseEntity<?> actualizar(@PathVariable String ccodProveedor, @RequestBody BProveedorDTO dto) {
    service.actualizar(ccodProveedor, dto);
    return ResponseEntity.ok("Proveedor actualizado correctamente");
  }

  @DeleteMapping("/{ccodProveedor}")
  public ResponseEntity<?> eliminar(@PathVariable String ccodProveedor) {
    service.eliminar(ccodProveedor);
    return ResponseEntity.ok("Proveedor eliminado correctamente");
  }
}
