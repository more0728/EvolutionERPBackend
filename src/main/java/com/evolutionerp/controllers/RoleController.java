package com.evolutionerp.controllers;

import com.evolutionerp.dtos.RoleDTO;
import com.evolutionerp.servicesinterfaces.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Una entidad, un controller: CRUD completo de Role (familia skill §A1/A5).
@RestController
@RequestMapping("/api/roles")
public class RoleController {

  private final RoleService service;

  public RoleController(RoleService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<?> listar(@RequestParam(required = false) Long userId) {
    var lista = service.listar(userId);
    if (lista.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay roles registrados");
    return ResponseEntity.ok(lista);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> obtener(@PathVariable Long id) {
    return ResponseEntity.ok(service.obtener(id));
  }

  @PostMapping("/nuevo")
  public ResponseEntity<?> crear(@RequestBody RoleDTO dto) {
    if (dto == null || dto.getRol() == null || dto.getRol().isBlank() || dto.getUserId() == null)
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El rol y el usuario son obligatorios");
    return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody RoleDTO dto) {
    service.actualizar(id, dto);
    return ResponseEntity.ok("Rol actualizado correctamente");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> eliminar(@PathVariable Long id) {
    service.eliminar(id);
    return ResponseEntity.ok("Rol eliminado correctamente");
  }
}
