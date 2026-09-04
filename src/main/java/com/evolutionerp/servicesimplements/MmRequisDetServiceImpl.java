package com.evolutionerp.servicesimplements;

import com.evolutionerp.dtos.MmRequisDetDTO;
import com.evolutionerp.entities.MmRequisCab;
import com.evolutionerp.entities.MmRequisCab.MmRequisCabId;
import com.evolutionerp.entities.MmRequisDet;
import com.evolutionerp.entities.MmRequisDet.MmRequisDetId;
import com.evolutionerp.exception.ModelNotFoundException;
import com.evolutionerp.repositories.BProveedorRepo;
import com.evolutionerp.repositories.MmaterialRepo;
import com.evolutionerp.repositories.MmRequisCabRepo;
import com.evolutionerp.repositories.MmRequisDetRepo;
import com.evolutionerp.servicesinterfaces.MmRequisDetService;
import com.evolutionerp.util.MapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MmRequisDetServiceImpl implements MmRequisDetService {
  private final MmRequisDetRepo repo;
  private final MmRequisCabRepo cabRepo;
  private final MmaterialRepo matRepo;
  private final BProveedorRepo provRepo;
  private final MapperUtil mapper;

  public MmRequisDetServiceImpl(MmRequisDetRepo repo, MmRequisCabRepo cabRepo, MmaterialRepo matRepo,
      BProveedorRepo provRepo, MapperUtil mapper) {
    this.repo = repo;
    this.cabRepo = cabRepo;
    this.matRepo = matRepo;
    this.provRepo = provRepo;
    this.mapper = mapper;
  }

  @Override
  @Transactional(readOnly = true)
  public List<MmRequisDetDTO> listarPorDocumento(String codSociedad, String nroDoc) {
    validarCabecera(codSociedad, nroDoc);
    return mapper.mapList(repo.findByCodSociedadAndNroDocOrderByNroItemAsc(codSociedad, nroDoc),
        MmRequisDetDTO.class);
  }

  @Override
  @Transactional(readOnly = true)
  public MmRequisDetDTO obtener(String codSociedad, String nroDoc, Long nroItem) {
    return mapper.map(findOrThrow(codSociedad, nroDoc, nroItem), MmRequisDetDTO.class);
  }

  @Override
  @Transactional
  public MmRequisDetDTO crear(String codSociedad, String nroDoc, MmRequisDetDTO dto) {
    MmRequisCab cab = validarCabecera(codSociedad, nroDoc);
    validarMaterial(dto.getCodMaterial());
    validarProveedor(dto.getCcodProveedor());
    MmRequisDet e = mapper.map(dto, MmRequisDet.class);
    e.setCodSociedad(codSociedad);
    e.setNroDoc(nroDoc);
    e.setNroItem(siguienteItem(codSociedad, nroDoc));
    e.setCab(cab);
    return mapper.map(repo.save(e), MmRequisDetDTO.class);
  }

  @Override
  @Transactional
  public MmRequisDetDTO actualizar(String codSociedad, String nroDoc, Long nroItem, MmRequisDetDTO dto) {
    validarCabecera(codSociedad, nroDoc);
    MmRequisDet e = findOrThrow(codSociedad, nroDoc, nroItem);
    if (dto.getCodMaterial() != null) {
      validarMaterial(dto.getCodMaterial());
      e.setCodMaterial(dto.getCodMaterial());
    }
    if (dto.getCUnidad() != null && !dto.getCUnidad().isBlank())
      e.setCUnidad(dto.getCUnidad());
    if (dto.getCantid() != null)
      e.setCantid(dto.getCantid());
    if (dto.getNcantidadRecibida() != null)
      e.setNcantidadRecibida(dto.getNcantidadRecibida());
    if (dto.getObserv() != null)
      e.setObserv(dto.getObserv());
    if (dto.getCcodProveedor() != null) {
      validarProveedor(dto.getCcodProveedor());
      e.setCcodProveedor(dto.getCcodProveedor());
    }
    if (dto.getEstado() != null && !dto.getEstado().isBlank())
      e.setEstado(dto.getEstado());
    if (dto.getOpcMant() != null && !dto.getOpcMant().isBlank())
      e.setOpcMant(dto.getOpcMant());
    return mapper.map(repo.save(e), MmRequisDetDTO.class);
  }

  @Override
  @Transactional
  public void eliminar(String codSociedad, String nroDoc, Long nroItem) {
    validarCabecera(codSociedad, nroDoc);
    MmRequisDetId id = new MmRequisDetId(codSociedad, nroDoc, nroItem);
    if (!repo.existsById(id))
      throw new ModelNotFoundException("Detalle no encontrado: " + nroItem);
    repo.deleteById(id);
  }

  private MmRequisDet findOrThrow(String codSociedad, String nroDoc, Long nroItem) {
    return repo.findById(new MmRequisDetId(codSociedad, nroDoc, nroItem))
        .orElseThrow(() -> new ModelNotFoundException("Detalle no encontrado: " + nroItem));
  }

  private MmRequisCab validarCabecera(String codSociedad, String nroDoc) {
    MmRequisCab cab = cabRepo.findById(new MmRequisCabId(codSociedad, nroDoc))
        .orElseThrow(() -> new ModelNotFoundException("Requisición no encontrada: " + nroDoc));
    if ("ANULADO".equals(cab.getEstado()))
      throw new ModelNotFoundException("No se puede modificar documento anulado");
    return cab;
  }

  private void validarMaterial(String codMaterial) {
    if (codMaterial != null && !codMaterial.isBlank() && !matRepo.existsById(codMaterial))
      throw new ModelNotFoundException("Material no encontrado: " + codMaterial);
  }

  private void validarProveedor(String ccodProveedor) {
    if (ccodProveedor != null && !ccodProveedor.isBlank() && !provRepo.existsById(ccodProveedor))
      throw new ModelNotFoundException("Proveedor no encontrado: " + ccodProveedor);
  }

  private Long siguienteItem(String codSociedad, String nroDoc) {
    return repo.findByCodSociedadAndNroDocOrderByNroItemAsc(codSociedad, nroDoc).stream()
        .map(MmRequisDet::getNroItem).max(Long::compareTo).orElse(0L) + 1;
  }
}
