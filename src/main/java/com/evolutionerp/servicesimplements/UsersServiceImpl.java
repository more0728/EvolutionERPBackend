package com.evolutionerp.servicesimplements;

import com.evolutionerp.dtos.UsersDTO;
import com.evolutionerp.entities.Role;
import com.evolutionerp.entities.Users;
import com.evolutionerp.exception.ConflictException;
import com.evolutionerp.exception.ModelNotFoundException;
import com.evolutionerp.repositories.IUserRepository;
import com.evolutionerp.repositories.RoleRepo;
import com.evolutionerp.servicesinterfaces.UsersService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {
  private final IUserRepository repo;
  private final RoleRepo roleRepo;
  private final PasswordEncoder passwordEncoder;

  public UsersServiceImpl(IUserRepository repo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
    this.repo = repo;
    this.roleRepo = roleRepo;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  @Transactional(readOnly = true)
  public List<UsersDTO> listar() {
    return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public UsersDTO obtener(Long id) {
    return toDTO(findOrThrow(id));
  }

  @Override
  @Transactional
  public UsersDTO crear(UsersDTO dto) {
    if (dto.getUsername() == null || dto.getUsername().isBlank())
      throw new ConflictException("El username es obligatorio");
    if (dto.getPassword() == null || dto.getPassword().isBlank())
      throw new ConflictException("La contraseña es obligatoria");
    if (repo.findOneByUsername(dto.getUsername()) != null)
      throw new ConflictException("Usuario ya registrado: " + dto.getUsername());
    Users e = new Users();
    e.setUsername(dto.getUsername());
    e.setPassword(passwordEncoder.encode(dto.getPassword()));
    e.setEnabled(dto.getEnabled() != null ? dto.getEnabled() : true);
    e.setRoles(buildRoles(dto.getRoles()));
    return toDTO(repo.save(e));
  }

  @Override
  @Transactional
  public UsersDTO actualizar(Long id, UsersDTO dto) {
    Users e = findOrThrow(id);
    if (dto.getUsername() != null && !dto.getUsername().isBlank()
        && !dto.getUsername().equals(e.getUsername())) {
      if (repo.findOneByUsername(dto.getUsername()) != null)
        throw new ConflictException("Usuario ya registrado: " + dto.getUsername());
      e.setUsername(dto.getUsername());
    }
    if (dto.getPassword() != null && !dto.getPassword().isBlank())
      e.setPassword(passwordEncoder.encode(dto.getPassword()));
    if (dto.getEnabled() != null)
      e.setEnabled(dto.getEnabled());
    if (dto.getRoles() != null) {
      roleRepo.deleteAll(roleRepo.findByUser_Id(id));
      e.getRoles().clear();
      e.getRoles().addAll(buildRoles(dto.getRoles()));
    }
    return toDTO(repo.save(e));
  }

  @Override
  @Transactional
  public void eliminar(Long id) {
    if (!repo.existsById(id))
      throw new ModelNotFoundException("Usuario no encontrado: " + id);
    repo.deleteById(id);
  }

  private Users findOrThrow(Long id) {
    return repo.findById(id).orElseThrow(() -> new ModelNotFoundException("Usuario no encontrado: " + id));
  }

  private List<Role> buildRoles(List<String> roles) {
    List<Role> list = new ArrayList<>();
    if (roles == null)
      return list;
    for (String r : roles) {
      if (r == null || r.isBlank())
        continue;
      Role role = new Role();
      role.setRol(r.trim().toUpperCase());
      list.add(role);
    }
    return list;
  }

  private UsersDTO toDTO(Users e) {
    UsersDTO dto = new UsersDTO();
    dto.setId(e.getId());
    dto.setUsername(e.getUsername());
    dto.setEnabled(e.getEnabled());
    dto.setRoles(e.getRoles() == null ? List.of()
        : e.getRoles().stream().map(Role::getRol).collect(Collectors.toList()));
    return dto;
  }
}
