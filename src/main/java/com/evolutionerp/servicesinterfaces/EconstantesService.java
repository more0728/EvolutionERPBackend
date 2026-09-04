package com.evolutionerp.servicesinterfaces;

import com.evolutionerp.dtos.EconstantesDTO;
import java.util.List;

public interface EconstantesService {
  List<EconstantesDTO> listar(String codSociedad, String app);

  EconstantesDTO obtener(String codSociedad, String cvalor, String app);

  EconstantesDTO crear(EconstantesDTO dto, String username);

  EconstantesDTO actualizar(String codSociedad, String cvalor, String app, EconstantesDTO dto, String username);

  void eliminar(String codSociedad, String cvalor, String app);
}
