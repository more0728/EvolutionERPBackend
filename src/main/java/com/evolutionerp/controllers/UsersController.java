package com.evolutionerp.controllers;

import com.evolutionerp.dtos.UsersDTO;
import com.evolutionerp.servicesinterfaces.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsersController {

  private final UsersService service;

  public UsersController(UsersService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<?> listar() {
    var lista = service.listar();
    if (lista.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay usuarios registrados");
    return ResponseEntity.ok(lista);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> obtener(@PathVariable Long id) {
    return ResponseEntity.ok(service.obtener(id));
  }

  @PostMapping("/nuevo")
  public ResponseEntity<?> crear(@RequestBody UsersDTO dto) {
    if (dto == null || dto.getUsername() == null || dto.getUsername().isBlank()
        || dto.getPassword() == null || dto.getPassword().isBlank())
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("El usuario y la contraseña son obligatorios");
    return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody UsersDTO dto) {
    service.actualizar(id, dto);
    return ResponseEntity.ok("Usuario actualizado correctamente");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> eliminar(@PathVariable Long id) {
    service.eliminar(id);
    return ResponseEntity.ok("Usuario eliminado correctamente");
  }
}
