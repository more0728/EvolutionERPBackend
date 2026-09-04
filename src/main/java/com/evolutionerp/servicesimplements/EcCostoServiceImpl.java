package com.evolutionerp.servicesimplements;

import com.evolutionerp.dtos.EcCostoDTO;
import com.evolutionerp.entities.EcCosto;
import com.evolutionerp.entities.EcCosto.EcCostoId;
import com.evolutionerp.exception.ConflictException;
import com.evolutionerp.exception.ModelNotFoundException;
import com.evolutionerp.repositories.EcCostoRepo;
import com.evolutionerp.repositories.EsociedadRepo;
import com.evolutionerp.servicesinterfaces.EcCostoService;
import com.evolutionerp.util.MapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class EcCostoServiceImpl implements EcCostoService {
  private final EcCostoRepo repo;
  private final EsociedadRepo socRepo;
  private final MapperUtil mapper;

  public EcCostoServiceImpl(EcCostoRepo repo, EsociedadRepo socRepo, MapperUtil mapper) {
    this.repo = repo;
    this.socRepo = socRepo;
    this.mapper = mapper;
  }

  @Override
  @Transactional(readOnly = true)
  public List<EcCostoDTO> listar(String codSociedad) {
    List<EcCosto> lista = codSociedad == null || codSociedad.isBlank()
        ? repo.findAll()
        : repo.findByCodSociedad(codSociedad);
    return mapper.mapList(lista, EcCostoDTO.class);
  }

  @Override
  @Transactional(readOnly = true)
  public EcCostoDTO obtener(String codSociedad, String ccodCencos) {
    return mapper.map(findOrThrow(codSociedad, ccodCencos), EcCostoDTO.class);
  }

  @Override
  @Transactional
  public EcCostoDTO crear(EcCostoDTO dto) {
    validarSociedad(dto.getCodSociedad());
    EcCostoId id = new EcCostoId(dto.getCodSociedad(), dto.getCcodCencos());
    if (repo.existsById(id))
      throw new ConflictException("Centro de costo ya registrado: " + dto.getCcodCencos());
    return mapper.map(repo.save(mapper.map(dto, EcCosto.class)), EcCostoDTO.class);
  }

  @Override
  @Transactional
  public EcCostoDTO actualizar(String codSociedad, String ccodCencos, EcCostoDTO dto) {
    EcCosto e = findOrThrow(codSociedad, ccodCencos);
    if (dto.getNomCencos() != null && !dto.getNomCencos().isBlank())
      e.setNomCencos(dto.getNomCencos());
    if (dto.getOpcMant() != null && !dto.getOpcMant().isBlank())
      e.setOpcMant(dto.getOpcMant());
    return mapper.map(repo.save(e), EcCostoDTO.class);
  }

  @Override
  @Transactional
  public void eliminar(String codSociedad, String ccodCencos) {
    EcCostoId id = new EcCostoId(codSociedad, ccodCencos);
    if (!repo.existsById(id))
      throw new ModelNotFoundException("Centro de costo no encontrado: " + ccodCencos);
    repo.deleteById(id);
  }

  private EcCosto findOrThrow(String codSociedad, String ccodCencos) {
    return repo.findById(new EcCostoId(codSociedad, ccodCencos))
        .orElseThrow(() -> new ModelNotFoundException("Centro de costo no encontrado: " + ccodCencos));
  }

  private void validarSociedad(String codSociedad) {
    if (codSociedad == null || codSociedad.isBlank() || !socRepo.existsById(codSociedad))
      throw new ModelNotFoundException("Sociedad no encontrada: " + codSociedad);
  }
}
