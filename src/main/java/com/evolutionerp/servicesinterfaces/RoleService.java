package com.evolutionerp.servicesinterfaces;

import com.evolutionerp.dtos.RoleDTO;
import java.util.List;

public interface RoleService {
  List<RoleDTO> listar(Long userId);

  RoleDTO obtener(Long id);

  RoleDTO crear(RoleDTO dto);

  RoleDTO actualizar(Long id, RoleDTO dto);

  void eliminar(Long id);
}
