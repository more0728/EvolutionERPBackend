
package com.evolutionerp.controllers;

import com.evolutionerp.dtos.MmRequisCabDTO;
import com.evolutionerp.dtos.EcCostoDTO;
import com.evolutionerp.dtos.EconstantesDTO;
import com.evolutionerp.dtos.EsociedadDTO;
import com.evolutionerp.servicesinterfaces.RequisicionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/requisiciones")
public class RequisicionController {
  private final RequisicionService service;

  public RequisicionController(RequisicionService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<MmRequisCabDTO> crear(@Valid @RequestBody MmRequisCabDTO dto, Authentication auth) {
    return ResponseEntity.ok(service.crear(dto, auth.getName()));
  }

  @GetMapping("/{codSoc}/{nroDoc}")
  public ResponseEntity<MmRequisCabDTO> obtener(@PathVariable String codSoc, @PathVariable String nroDoc) {
    return ResponseEntity.ok(service.obtener(codSoc, nroDoc));
  }

  @PutMapping("/{codSoc}/{nroDoc}")
  public ResponseEntity<MmRequisCabDTO> actualizar(@PathVariable String codSoc, @PathVariable String nroDoc,
      @Valid @RequestBody MmRequisCabDTO dto, Authentication auth) {
    return ResponseEntity.ok(service.actualizar(codSoc, nroDoc, dto, auth.getName()));
  }

  @PatchMapping("/{codSoc}/{nroDoc}/anular")
  public ResponseEntity<Void> anular(@PathVariable String codSoc, @PathVariable String nroDoc, Authentication auth) {
    service.anular(codSoc, nroDoc, auth.getName());
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{codSoc}/{nroDoc}")
  public ResponseEntity<Void> eliminar(@PathVariable String codSoc, @PathVariable String nroDoc) {
    service.eliminar(codSoc, nroDoc);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public Page<MmRequisCabDTO> listar(@RequestParam String codSociedad, @RequestParam(required = false) String estado,
      @RequestParam(required = false) String cencos, @RequestParam(required = false) String prio,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecIni,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecFin,
      @RequestParam(required = false) String q, Pageable pageable) {
    return service.listar(codSociedad, estado, cencos, prio, fecIni, fecFin, q, pageable);
  }

  @GetMapping("/listas/centros/{codSoc}")
  public List<EcCostoDTO> centros(@PathVariable String codSoc) {
    return service.listarCentros(codSoc);
  }

  @GetMapping("/listas/prioridades/{codSoc}")
  public List<EconstantesDTO> prioridades(@PathVariable String codSoc) {
    return service.listarPrioridades(codSoc);
  }

  @GetMapping("/listas/sociedades")
  public List<EsociedadDTO> sociedades() {
    return service.listarSociedades();
  }
}
