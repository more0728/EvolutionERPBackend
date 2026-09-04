package com.evolutionerp.servicesimplements;

import com.evolutionerp.dtos.BProveedorDTO;
import com.evolutionerp.entities.BProveedor;
import com.evolutionerp.exception.ConflictException;
import com.evolutionerp.exception.ModelNotFoundException;
import com.evolutionerp.repositories.BProveedorRepo;
import com.evolutionerp.servicesinterfaces.BProveedorService;
import com.evolutionerp.util.MapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class BProveedorServiceImpl implements BProveedorService {
  private final BProveedorRepo repo;
  private final MapperUtil mapper;

  public BProveedorServiceImpl(BProveedorRepo repo, MapperUtil mapper) {
    this.repo = repo;
    this.mapper = mapper;
  }

  @Override
  @Transactional(readOnly = true)
  public List<BProveedorDTO> listar(String q) {
    List<BProveedor> lista = q == null || q.isBlank() ? repo.findAll() : repo.search(q.toUpperCase());
    return mapper.mapList(lista, BProveedorDTO.class);
  }

  @Override
  @Transactional(readOnly = true)
  public BProveedorDTO obtener(String ccodProveedor) {
    return mapper.map(findOrThrow(ccodProveedor), BProveedorDTO.class);
  }

  @Override
  @Transactional
  public BProveedorDTO crear(BProveedorDTO dto) {
    if (repo.existsById(dto.getCcodProveedor()))
      throw new ConflictException("Proveedor ya registrado: " + dto.getCcodProveedor());
    return mapper.map(repo.save(mapper.map(dto, BProveedor.class)), BProveedorDTO.class);
  }

  @Override
  @Transactional
  public BProveedorDTO actualizar(String ccodProveedor, BProveedorDTO dto) {
    BProveedor e = findOrThrow(ccodProveedor);
    if (dto.getNomProv() != null && !dto.getNomProv().isBlank())
      e.setNomProv(dto.getNomProv());
    if (dto.getRuc() != null)
      e.setRuc(dto.getRuc());
    if (dto.getOpcMant() != null && !dto.getOpcMant().isBlank())
      e.setOpcMant(dto.getOpcMant());
    return mapper.map(repo.save(e), BProveedorDTO.class);
  }

  @Override
  @Transactional
  public void eliminar(String ccodProveedor) {
    if (!repo.existsById(ccodProveedor))
      throw new ModelNotFoundException("Proveedor no encontrado: " + ccodProveedor);
    repo.deleteById(ccodProveedor);
  }

  private BProveedor findOrThrow(String ccodProveedor) {
    return repo.findById(ccodProveedor)
        .orElseThrow(() -> new ModelNotFoundException("Proveedor no encontrado: " + ccodProveedor));
  }
}
