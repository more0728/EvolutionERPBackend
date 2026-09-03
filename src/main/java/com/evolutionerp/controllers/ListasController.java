
package com.evolutionerp.controllers;
import com.evolutionerp.entities.BProveedor;
import com.evolutionerp.entities.EPersonal;
import com.evolutionerp.entities.Mmaterial;
import com.evolutionerp.service.ListasService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/listas") @RequiredArgsConstructor
public class ListasController {
  private final ListasService service;
  @GetMapping("/personal/{codSoc}") public List<EPersonal> personal(@PathVariable String codSoc){ return service.listarPersonal(codSoc); }
  @GetMapping("/proveedores") public List<BProveedor> proveedores(@RequestParam(required=false) String q){ return service.listarProveedores(q); }
  @GetMapping("/materiales") public List<Mmaterial> materiales(@RequestParam String q){ return service.buscarMaterial(q); }
}
