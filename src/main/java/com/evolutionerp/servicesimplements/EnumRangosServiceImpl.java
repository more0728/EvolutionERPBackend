package com.evolutionerp.servicesimplements;

import com.evolutionerp.dtos.EnumRangosDTO;
import com.evolutionerp.entities.EnumRangos;
import com.evolutionerp.entities.EnumRangos.EnumRangosId;
import com.evolutionerp.exception.ConflictException;
import com.evolutionerp.exception.ModelNotFoundException;
import com.evolutionerp.repositories.EnumRangosRepo;
import com.evolutionerp.repositories.EsociedadRepo;
import com.evolutionerp.servicesinterfaces.EnumRangosService;
import com.evolutionerp.util.MapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnumRangosServiceImpl implements EnumRangosService {
  private final EnumRangosRepo repo;
  private final EsociedadRepo socRepo;
  private final MapperUtil mapper;

  public EnumRangosServiceImpl(EnumRangosRepo repo, EsociedadRepo socRepo, MapperUtil mapper) {
    this.repo = repo;
    this.socRepo = socRepo;
    this.mapper = mapper;
  }

  @Override
  @Transactional(readOnly = true)
  public List<EnumRangosDTO> listar(String codSociedad) {
    List<EnumRangos> lista = repo.findAll();
    if (codSociedad != null && !codSociedad.isBlank())
      lista = lista.stream().filter(r -> codSociedad.equals(r.getCodSociedad())).collect(Collectors.toList());
    return mapper.mapList(lista, EnumRangosDTO.class);
  }

  @Override
  @Transactional(readOnly = true)
  public EnumRangosDTO obtener(String codSociedad, String app) {
    return mapper.map(findOrThrow(codSociedad, app), EnumRangosDTO.class);
  }

  @Override
  @Transactional
  public EnumRangosDTO crear(EnumRangosDTO dto) {
    validarSociedad(dto.getCodSociedad());
    EnumRangosId id = new EnumRangosId(dto.getCodSociedad(), dto.getApp());
    if (repo.existsById(id))
      throw new ConflictException("Rango ya registrado: " + dto.getApp());
    EnumRangos e = mapper.map(dto, EnumRangos.class);
    e.setNroDoc(null);
    return mapper.map(repo.save(e), EnumRangosDTO.class);
  }

  @Override
  @Transactional
  public EnumRangosDTO actualizar(String codSociedad, String app, EnumRangosDTO dto) {
    EnumRangos e = findOrThrow(codSociedad, app);
    if (dto.getUltimoNum() != null)
      e.setUltimoNum(dto.getUltimoNum());
    return mapper.map(repo.save(e), EnumRangosDTO.class);
  }

  @Override
  @Transactional
  public void eliminar(String codSociedad, String app) {
    EnumRangosId id = new EnumRangosId(codSociedad, app);
    if (!repo.existsById(id))
      throw new ModelNotFoundException("Rango no encontrado: " + app);
    repo.deleteById(id);
  }

  private EnumRangos findOrThrow(String codSociedad, String app) {
    return repo.findById(new EnumRangosId(codSociedad, app))
        .orElseThrow(() -> new ModelNotFoundException("Rango no encontrado: " + app));
  }

  private void validarSociedad(String codSociedad) {
    if (codSociedad == null || codSociedad.isBlank() || !socRepo.existsById(codSociedad))
      throw new ModelNotFoundException("Sociedad no encontrada: " + codSociedad);
  }
}
