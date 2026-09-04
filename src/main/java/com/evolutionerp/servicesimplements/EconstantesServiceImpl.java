package com.evolutionerp.servicesimplements;

import com.evolutionerp.dtos.EconstantesDTO;
import com.evolutionerp.entities.Econstantes;
import com.evolutionerp.entities.Econstantes.EconstantesId;
import com.evolutionerp.exception.ConflictException;
import com.evolutionerp.exception.ModelNotFoundException;
import com.evolutionerp.repositories.EconstantesRepo;
import com.evolutionerp.repositories.EsociedadRepo;
import com.evolutionerp.servicesinterfaces.EconstantesService;
import com.evolutionerp.util.MapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EconstantesServiceImpl implements EconstantesService {
  private final EconstantesRepo repo;
  private final EsociedadRepo socRepo;
  private final MapperUtil mapper;

  public EconstantesServiceImpl(EconstantesRepo repo, EsociedadRepo socRepo, MapperUtil mapper) {
    this.repo = repo;
    this.socRepo = socRepo;
    this.mapper = mapper;
  }

  @Override
  @Transactional(readOnly = true)
  public List<EconstantesDTO> listar(String codSociedad, String app) {
    List<Econstantes> lista = repo.findAll();
    if (codSociedad != null && !codSociedad.isBlank())
      lista = lista.stream().filter(e -> codSociedad.equals(e.getCodSociedad()))
          .collect(Collectors.toList());
    if (app != null && !app.isBlank())
      lista = lista.stream().filter(e -> app.equals(e.getApp())).collect(Collectors.toList());
    return mapper.mapList(lista, EconstantesDTO.class);
  }

  @Override
  @Transactional(readOnly = true)
  public EconstantesDTO obtener(String codSociedad, String cvalor, String app) {
    return mapper.map(findOrThrow(codSociedad, cvalor, app), EconstantesDTO.class);
  }

  @Override
  @Transactional
  public EconstantesDTO crear(EconstantesDTO dto, String username) {
    validarSociedad(dto.getCodSociedad());
    EconstantesId id = new EconstantesId(dto.getCodSociedad(), dto.getCvalor(), dto.getApp());
    if (repo.existsById(id))
      throw new ConflictException("Constante ya registrada: " + dto.getCvalor());
    Econstantes e = mapper.map(dto, Econstantes.class);
    e.setUserSis(username.toUpperCase());
    e.setUserMod(username.toUpperCase());
    e.setUserSisDate(LocalDateTime.now());
    return mapper.map(repo.save(e), EconstantesDTO.class);
  }

  @Override
  @Transactional
  public EconstantesDTO actualizar(String codSociedad, String cvalor, String app, EconstantesDTO dto,
      String username) {
    Econstantes e = findOrThrow(codSociedad, cvalor, app);
    if (dto.getCnomValor() != null && !dto.getCnomValor().isBlank())
      e.setCnomValor(dto.getCnomValor());
    if (dto.getOpcMant() != null && !dto.getOpcMant().isBlank())
      e.setOpcMant(dto.getOpcMant());
    e.setUserMod(username.toUpperCase());
    e.setUserSisDate(LocalDateTime.now());
    return mapper.map(repo.save(e), EconstantesDTO.class);
  }

  @Override
  @Transactional
  public void eliminar(String codSociedad, String cvalor, String app) {
    EconstantesId id = new EconstantesId(codSociedad, cvalor, app);
    if (!repo.existsById(id))
      throw new ModelNotFoundException("Constante no encontrada: " + cvalor);
    repo.deleteById(id);
  }

  private Econstantes findOrThrow(String codSociedad, String cvalor, String app) {
    return repo.findById(new EconstantesId(codSociedad, cvalor, app))
        .orElseThrow(() -> new ModelNotFoundException("Constante no encontrada: " + cvalor));
  }

  private void validarSociedad(String codSociedad) {
    if (codSociedad == null || codSociedad.isBlank() || !socRepo.existsById(codSociedad))
      throw new ModelNotFoundException("Sociedad no encontrada: " + codSociedad);
  }
}
