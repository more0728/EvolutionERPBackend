package com.evolutionerp.repositories;

import com.evolutionerp.entities.Role;
import java.util.List;

public interface RoleRepo extends IGenericRepo<Role, Long> {
  List<Role> findByUser_Id(Long userId);
}
