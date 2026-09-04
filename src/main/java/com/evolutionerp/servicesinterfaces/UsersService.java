package com.evolutionerp.servicesinterfaces;

import com.evolutionerp.dtos.UsersDTO;
import java.util.List;

public interface UsersService {
  List<UsersDTO> listar();

  UsersDTO obtener(Long id);

  UsersDTO crear(UsersDTO dto);

  UsersDTO actualizar(Long id, UsersDTO dto);

  void eliminar(Long id);
}
