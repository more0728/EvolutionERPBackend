package com.evolutionerp.servicesinterfaces;

import com.evolutionerp.dtos.EnumRangosDTO;
import java.util.List;

public interface EnumRangosService {
  List<EnumRangosDTO> listar(String codSociedad);

  EnumRangosDTO obtener(String codSociedad, String app);

  EnumRangosDTO crear(EnumRangosDTO dto);

  EnumRangosDTO actualizar(String codSociedad, String app, EnumRangosDTO dto);

  void eliminar(String codSociedad, String app);
}
