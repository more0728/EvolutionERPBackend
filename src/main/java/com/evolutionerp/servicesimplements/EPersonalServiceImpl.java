package com.evolutionerp.servicesimplements;

import com.evolutionerp.dtos.EPersonalDTO;
import com.evolutionerp.entities.EPersonal;
import com.evolutionerp.exception.ConflictException;
import com.evolutionerp.exception.ModelNotFoundException;
import com.evolutionerp.repositories.EPersonalRepo;
import com.evolutionerp.repositories.EsociedadRepo;
import com.evolutionerp.servicesinterfaces.EPersonalService;
import com.evolutionerp.util.MapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class EPersonalServiceImpl implements EPersonalService {
  private final EPersonalRepo repo;
  private final EsociedadRepo socRepo;
  private final MapperUtil mapper;

  public EPersonalServiceImpl(EPersonalRepo repo, EsociedadRepo socRepo, MapperUtil mapper) {
    this.repo = repo;
    this.socRepo = socRepo;
    this.mapper = mapper;
  }

  @Override
  @Transactional(readOnly = true)
  public List<EPersonalDTO> listar(String codSociedad) {
    List<EPersonal> lista = codSociedad == null || codSociedad.isBlank()
        ? repo.findAll()
        : repo.findByCodSociedad(codSociedad);
    return mapper.mapList(lista, EPersonalDTO.class);
  }

  @Override
  @Transactional(readOnly = true)
  public EPersonalDTO obtener(String ccodPerson) {
    return mapper.map(findOrThrow(ccodPerson), EPersonalDTO.class);
  }

  @Override
  @Transactional
  public EPersonalDTO crear(EPersonalDTO dto) {
    validarSociedad(dto.getCodSociedad());
    if (repo.existsById(dto.getCcodPerson()))
      throw new ConflictException("Personal ya registrado: " + dto.getCcodPerson());
    return mapper.map(repo.save(mapper.map(dto, EPersonal.class)), EPersonalDTO.class);
  }

  @Override
  @Transactional
  public EPersonalDTO actualizar(String ccodPerson, EPersonalDTO dto) {
    EPersonal e = findOrThrow(ccodPerson);
    if (dto.getCodSociedad() != null && !dto.getCodSociedad().isBlank()) {
      validarSociedad(dto.getCodSociedad());
      e.setCodSociedad(dto.getCodSociedad());
    }
    if (dto.getNomPerson() != null && !dto.getNomPerson().isBlank())
      e.setNomPerson(dto.getNomPerson());
    if (dto.getOpcMant() != null && !dto.getOpcMant().isBlank())
      e.setOpcMant(dto.getOpcMant());
    return mapper.map(repo.save(e), EPersonalDTO.class);
  }

  @Override
  @Transactional
  public void eliminar(String ccodPerson) {
    if (!repo.existsById(ccodPerson))
      throw new ModelNotFoundException("Personal no encontrado: " + ccodPerson);
    repo.deleteById(ccodPerson);
  }

  private EPersonal findOrThrow(String ccodPerson) {
    return repo.findById(ccodPerson)
        .orElseThrow(() -> new ModelNotFoundException("Personal no encontrado: " + ccodPerson));
  }

  private void validarSociedad(String codSociedad) {
    if (codSociedad != null && !codSociedad.isBlank() && !socRepo.existsById(codSociedad))
      throw new ModelNotFoundException("Sociedad no encontrada: " + codSociedad);
  }
}
