package com.evolutionerp.servicesinterfaces;

import com.evolutionerp.dtos.EPersonalDTO;
import java.util.List;

public interface EPersonalService {
  List<EPersonalDTO> listar(String codSociedad);

  EPersonalDTO obtener(String ccodPerson);

  EPersonalDTO crear(EPersonalDTO dto);

  EPersonalDTO actualizar(String ccodPerson, EPersonalDTO dto);

  void eliminar(String ccodPerson);
}
