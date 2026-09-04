package com.evolutionerp.servicesimplements;

import com.evolutionerp.dtos.RoleDTO;
import com.evolutionerp.entities.Role;
import com.evolutionerp.entities.Users;
import com.evolutionerp.exception.ConflictException;
import com.evolutionerp.exception.ModelNotFoundException;
import com.evolutionerp.repositories.IUserRepository;
import com.evolutionerp.repositories.RoleRepo;
import com.evolutionerp.servicesinterfaces.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
  private final RoleRepo repo;
  private final IUserRepository userRepo;

  public RoleServiceImpl(RoleRepo repo, IUserRepository userRepo) {
    this.repo = repo;
    this.userRepo = userRepo;
  }

  @Override
  @Transactional(readOnly = true)
  public List<RoleDTO> listar(Long userId) {
    List<Role> lista = userId == null ? repo.findAll() : repo.findByUser_Id(userId);
    return lista.stream().map(this::toDTO).collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public RoleDTO obtener(Long id) {
    return toDTO(findOrThrow(id));
  }

  @Override
  @Transactional
  public RoleDTO crear(RoleDTO dto) {
    if (dto.getRol() == null || dto.getRol().isBlank())
      throw new ConflictException("El rol es obligatorio");
    Users user = validarUsuario(dto.getUserId());
    if (repo.findByUser_Id(user.getId()).stream().anyMatch(r -> dto.getRol().equalsIgnoreCase(r.getRol())))
      throw new ConflictException("Rol ya asignado al usuario: " + dto.getRol());
    Role e = new Role();
    e.setRol(dto.getRol().trim().toUpperCase());
    e.setUser(user);
    return toDTO(repo.save(e));
  }

  @Override
  @Transactional
  public RoleDTO actualizar(Long id, RoleDTO dto) {
    Role e = findOrThrow(id);
    if (dto.getRol() != null && !dto.getRol().isBlank())
      e.setRol(dto.getRol().trim().toUpperCase());
    if (dto.getUserId() != null
        && (e.getUser() == null || !dto.getUserId().equals(e.getUser().getId())))
      e.setUser(validarUsuario(dto.getUserId()));
    return toDTO(repo.save(e));
  }

  @Override
  @Transactional
  public void eliminar(Long id) {
    if (!repo.existsById(id))
      throw new ModelNotFoundException("Rol no encontrado: " + id);
    repo.deleteById(id);
  }

  private Role findOrThrow(Long id) {
    return repo.findById(id).orElseThrow(() -> new ModelNotFoundException("Rol no encontrado: " + id));
  }

  private Users validarUsuario(Long userId) {
    if (userId == null)
      throw new ModelNotFoundException("Usuario no encontrado: null");
    return userRepo.findById(userId)
        .orElseThrow(() -> new ModelNotFoundException("Usuario no encontrado: " + userId));
  }

  private RoleDTO toDTO(Role e) {
    RoleDTO dto = new RoleDTO();
    dto.setId(e.getId());
    dto.setRol(e.getRol());
    if (e.getUser() != null) {
      dto.setUserId(e.getUser().getId());
      dto.setUsername(e.getUser().getUsername());
    }
    return dto;
  }
}
