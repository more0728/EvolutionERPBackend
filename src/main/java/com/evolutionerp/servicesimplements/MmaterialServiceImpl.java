package com.evolutionerp.servicesimplements;

import com.evolutionerp.dtos.MmaterialDTO;
import com.evolutionerp.entities.Mmaterial;
import com.evolutionerp.exception.ConflictException;
import com.evolutionerp.exception.ModelNotFoundException;
import com.evolutionerp.repositories.MmaterialRepo;
import com.evolutionerp.servicesinterfaces.MmaterialService;
import com.evolutionerp.util.MapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MmaterialServiceImpl implements MmaterialService {
  private final MmaterialRepo repo;
  private final MapperUtil mapper;

  public MmaterialServiceImpl(MmaterialRepo repo, MapperUtil mapper) {
    this.repo = repo;
    this.mapper = mapper;
  }

  @Override
  @Transactional(readOnly = true)
  public List<MmaterialDTO> listar(String q) {
    List<Mmaterial> lista = q == null || q.isBlank() ? repo.findAll() : repo.search(q.toUpperCase());
    return mapper.mapList(lista, MmaterialDTO.class);
  }

  @Override
  @Transactional(readOnly = true)
  public MmaterialDTO obtener(String codMaterial) {
    return mapper.map(findOrThrow(codMaterial), MmaterialDTO.class);
  }

  @Override
  @Transactional
  public MmaterialDTO crear(MmaterialDTO dto) {
    if (repo.existsById(dto.getCodMaterial()))
      throw new ConflictException("Material ya registrado: " + dto.getCodMaterial());
    return mapper.map(repo.save(mapper.map(dto, Mmaterial.class)), MmaterialDTO.class);
  }

  @Override
  @Transactional
  public MmaterialDTO actualizar(String codMaterial, MmaterialDTO dto) {
    Mmaterial e = findOrThrow(codMaterial);
    if (dto.getNomMaterial() != null && !dto.getNomMaterial().isBlank())
      e.setNomMaterial(dto.getNomMaterial());
    if (dto.getCUnidad() != null && !dto.getCUnidad().isBlank())
      e.setCUnidad(dto.getCUnidad());
    if (dto.getOpcMant() != null && !dto.getOpcMant().isBlank())
      e.setOpcMant(dto.getOpcMant());
    return mapper.map(repo.save(e), MmaterialDTO.class);
  }

  @Override
  @Transactional
  public void eliminar(String codMaterial) {
    if (!repo.existsById(codMaterial))
      throw new ModelNotFoundException("Material no encontrado: " + codMaterial);
    repo.deleteById(codMaterial);
  }

  private Mmaterial findOrThrow(String codMaterial) {
    return repo.findById(codMaterial)
        .orElseThrow(() -> new ModelNotFoundException("Material no encontrado: " + codMaterial));
  }
}
