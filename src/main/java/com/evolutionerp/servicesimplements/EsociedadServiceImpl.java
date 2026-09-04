package com.evolutionerp.servicesimplements;

import com.evolutionerp.dtos.EsociedadDTO;
import com.evolutionerp.entities.Esociedad;
import com.evolutionerp.exception.ConflictException;
import com.evolutionerp.exception.ModelNotFoundException;
import com.evolutionerp.repositories.EsociedadRepo;
import com.evolutionerp.servicesinterfaces.EsociedadService;
import com.evolutionerp.util.MapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EsociedadServiceImpl implements EsociedadService {
  private final EsociedadRepo repo;
  private final MapperUtil mapper;

  public EsociedadServiceImpl(EsociedadRepo repo, MapperUtil mapper) {
    this.repo = repo;
    this.mapper = mapper;
  }

  @Override
  @Transactional(readOnly = true)
  public List<EsociedadDTO> listar() {
    return mapper.mapList(repo.findAll(), EsociedadDTO.class);
  }

  @Override
  @Transactional(readOnly = true)
  public EsociedadDTO obtener(String codSociedad) {
    return mapper.map(findOrThrow(codSociedad), EsociedadDTO.class);
  }

  @Override
  @Transactional
  public EsociedadDTO crear(EsociedadDTO dto, String username) {
    if (repo.existsById(dto.getCodSociedad()))
      throw new ConflictException("Sociedad ya registrada: " + dto.getCodSociedad());
    Esociedad e = mapper.map(dto, Esociedad.class);
    e.setUserSis(username.toUpperCase());
    e.setUserMod(username.toUpperCase());
    e.setUserSisDate(LocalDateTime.now());
    return mapper.map(repo.save(e), EsociedadDTO.class);
  }

  @Override
  @Transactional
  public EsociedadDTO actualizar(String codSociedad, EsociedadDTO dto, String username) {
    Esociedad e = findOrThrow(codSociedad);
    if (dto.getNomSociedad() != null && !dto.getNomSociedad().isBlank())
      e.setNomSociedad(dto.getNomSociedad());
    if (dto.getNitSociedad() != null && !dto.getNitSociedad().isBlank())
      e.setNitSociedad(dto.getNitSociedad());
    if (dto.getIdPais() != null && !dto.getIdPais().isBlank())
      e.setIdPais(dto.getIdPais());
    if (dto.getIdIdioma() != null && !dto.getIdIdioma().isBlank())
      e.setIdIdioma(dto.getIdIdioma());
    if (dto.getNomComercial() != null)
      e.setNomComercial(dto.getNomComercial());
    if (dto.getOpcMant() != null && !dto.getOpcMant().isBlank())
      e.setOpcMant(dto.getOpcMant());
    e.setUserMod(username.toUpperCase());
    e.setUserSisDate(LocalDateTime.now());
    return mapper.map(repo.save(e), EsociedadDTO.class);
  }

  @Override
  @Transactional
  public void eliminar(String codSociedad) {
    if (!repo.existsById(codSociedad))
      throw new ModelNotFoundException("Sociedad no encontrada: " + codSociedad);
    repo.deleteById(codSociedad);
  }

  private Esociedad findOrThrow(String codSociedad) {
    return repo.findById(codSociedad)
        .orElseThrow(() -> new ModelNotFoundException("Sociedad no encontrada: " + codSociedad));
  }
}
