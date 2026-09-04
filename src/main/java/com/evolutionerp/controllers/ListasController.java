package com.evolutionerp.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.evolutionerp.dtos.BProveedorDTO;
import com.evolutionerp.dtos.EPersonalDTO;
import com.evolutionerp.dtos.MmaterialDTO;
import com.evolutionerp.servicesinterfaces.ListasService;

// Estilo KitchenHack: Controller → Service → Repository. Nunca expone entidades,
// responde ResponseEntity<?>, lista vacía → 404 String en español,
// ModelMapper singleton inyectado (skipNullEnabled).
@RestController
@RequestMapping("/api/listas")
public class ListasController {

    private final ListasService service;
    private final ModelMapper mapper;

    public ListasController(ListasService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/personal/{codSoc}")
    public ResponseEntity<?> personal(@PathVariable String codSoc) {
        if (codSoc == null || codSoc.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El código de sociedad es obligatorio");
        }
        var lista = service.listarPersonal(codSoc);
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay personal registrado");
        }
        List<EPersonalDTO> listaDTO = lista.stream().map(e -> mapper.map(e, EPersonalDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listaDTO);
    }

    @GetMapping("/proveedores")
    public ResponseEntity<?> proveedores(@RequestParam(required = false) String q) {
        var lista = service.listarProveedores(q);
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay proveedores registrados");
        }
        List<BProveedorDTO> listaDTO = lista.stream().map(e -> mapper.map(e, BProveedorDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listaDTO);
    }

    @GetMapping("/materiales")
    public ResponseEntity<?> materiales(@RequestParam(required = false) String q) {
        if (q == null || q.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El filtro de búsqueda es obligatorio");
        }
        var lista = service.buscarMaterial(q);
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay materiales registrados");
        }
        List<MmaterialDTO> listaDTO = lista.stream().map(e -> mapper.map(e, MmaterialDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listaDTO);
    }
}
