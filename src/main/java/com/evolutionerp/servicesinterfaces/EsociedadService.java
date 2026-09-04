package com.evolutionerp.servicesinterfaces;

import com.evolutionerp.dtos.EsociedadDTO;
import java.util.List;

public interface EsociedadService {
  List<EsociedadDTO> listar();

  EsociedadDTO obtener(String codSociedad);

  EsociedadDTO crear(EsociedadDTO dto, String username);

  EsociedadDTO actualizar(String codSociedad, EsociedadDTO dto, String username);

  void eliminar(String codSociedad);
}
