package com.evolutionerp.servicesinterfaces;

import com.evolutionerp.dtos.EcCostoDTO;
import java.util.List;

public interface EcCostoService {
  List<EcCostoDTO> listar(String codSociedad);

  EcCostoDTO obtener(String codSociedad, String ccodCencos);

  EcCostoDTO crear(EcCostoDTO dto);

  EcCostoDTO actualizar(String codSociedad, String ccodCencos, EcCostoDTO dto);

  void eliminar(String codSociedad, String ccodCencos);
}
