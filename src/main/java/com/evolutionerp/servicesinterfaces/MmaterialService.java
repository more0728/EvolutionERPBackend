package com.evolutionerp.servicesinterfaces;

import com.evolutionerp.dtos.MmaterialDTO;
import java.util.List;

public interface MmaterialService {
  List<MmaterialDTO> listar(String q);

  MmaterialDTO obtener(String codMaterial);

  MmaterialDTO crear(MmaterialDTO dto);

  MmaterialDTO actualizar(String codMaterial, MmaterialDTO dto);

  void eliminar(String codMaterial);
}
