package com.evolutionerp.servicesinterfaces;

import com.evolutionerp.dtos.BProveedorDTO;
import java.util.List;

public interface BProveedorService {
  List<BProveedorDTO> listar(String q);

  BProveedorDTO obtener(String ccodProveedor);

  BProveedorDTO crear(BProveedorDTO dto);

  BProveedorDTO actualizar(String ccodProveedor, BProveedorDTO dto);

  void eliminar(String ccodProveedor);
}
