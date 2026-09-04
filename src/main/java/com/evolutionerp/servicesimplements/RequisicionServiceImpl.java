
package com.evolutionerp.servicesimplements;

import com.evolutionerp.dtos.*;
import com.evolutionerp.exception.ModelNotFoundException;
import com.evolutionerp.entities.*;
import com.evolutionerp.entities.EcCosto.EcCostoId;
import com.evolutionerp.entities.Econstantes.EconstantesId;
import com.evolutionerp.entities.MmRequisCab.MmRequisCabId;
import com.evolutionerp.repositories.*;
import com.evolutionerp.util.MapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RequisicionServiceImpl implements com.evolutionerp.servicesinterfaces.RequisicionService {
  private final MmRequisCabRepo cabRepo;
  private final EsociedadRepo socRepo;
  private final EnumRangosRepo rangosRepo;
  private final EcCostoRepo cencosRepo;
  private final EPersonalRepo personalRepo;
  private final BProveedorRepo proveedorRepo;
  private final MmaterialRepo materialRepo;
  private final EconstantesRepo constantesRepo;
  private final MapperUtil mapper;

  public RequisicionServiceImpl(MmRequisCabRepo cabRepo,
      EsociedadRepo socRepo, EnumRangosRepo rangosRepo, EcCostoRepo cencosRepo,
      EPersonalRepo personalRepo, BProveedorRepo proveedorRepo, MmaterialRepo materialRepo,
      EconstantesRepo constantesRepo, MapperUtil mapper) {
    this.cabRepo = cabRepo;
    this.socRepo = socRepo;
    this.rangosRepo = rangosRepo;
    this.cencosRepo = cencosRepo;
    this.personalRepo = personalRepo;
    this.proveedorRepo = proveedorRepo;
    this.materialRepo = materialRepo;
    this.constantesRepo = constantesRepo;
    this.mapper = mapper;
  }

  // Excel: centro/personal/proveedor/prioridad deben venir de sus listas.
  // Se validan contra los maestros para no dejar documentos huérfanos.
  private void validarMaestros(MmRequisCabDTO dto) {
    String soc = dto.getCodSociedad();
    if (!cencosRepo.existsById(new EcCostoId(soc, dto.getCcodCencos())))
      throw new ModelNotFoundException("Centro de costo no existe: " + dto.getCcodCencos());
    if (dto.getCcodPerson() != null && !dto.getCcodPerson().isBlank()
        && !personalRepo.existsById(dto.getCcodPerson()))
      throw new ModelNotFoundException("Personal no existe: " + dto.getCcodPerson());
    if (dto.getCcodProveedor() != null && !dto.getCcodProveedor().isBlank()
        && !proveedorRepo.existsById(dto.getCcodProveedor()))
      throw new ModelNotFoundException("Proveedor no existe: " + dto.getCcodProveedor());
    if (dto.getTipPrio() != null && !dto.getTipPrio().isBlank()
        && !constantesRepo.existsById(new EconstantesId(soc, dto.getTipPrio(), "PRIO")))
      throw new ModelNotFoundException("Prioridad no existe: " + dto.getTipPrio());
    for (MmRequisDetDTO d : dto.getDetalles()) {
      if (d.getCodMaterial() != null && !d.getCodMaterial().isBlank()
          && !materialRepo.existsById(d.getCodMaterial()))
        throw new ModelNotFoundException("Material no existe: " + d.getCodMaterial());
      if (d.getCcodProveedor() != null && !d.getCcodProveedor().isBlank()
          && !proveedorRepo.existsById(d.getCcodProveedor()))
        throw new ModelNotFoundException("Proveedor del detalle no existe: " + d.getCcodProveedor());
    }
  }

  @Transactional
  public MmRequisCabDTO crear(MmRequisCabDTO dto, String username) {
    if (!socRepo.existsById(dto.getCodSociedad()))
      throw new ModelNotFoundException("Sociedad no existe: " + dto.getCodSociedad());
    validarMaestros(dto);
    // Excel 6.1: el correlativo es secuencial y automático (EnumRangos app=COM).
    // Se ignora cualquier nroDoc enviado por el cliente.
    String nro = rangosRepo.nextCorrelativo(dto.getCodSociedad(), "COM");
    MmRequisCab cab = mapper.map(dto, MmRequisCab.class);
    cab.setNroDoc(nro);
    // Campos controlados por el servidor: el cliente no puede crear un
    // documento ANULADO/APROBADO ni alterar condic/opcMant/app.
    cab.setEstado("PENDIENTE");
    cab.setCondic("A");
    cab.setOpcMant("ACTIVO");
    cab.setApp("COM");
    cab.setUserSis(username.toUpperCase());
    cab.setUserMod(username.toUpperCase());
    cab.setUserSisDate(LocalDateTime.now());
    if (cab.getFecDoc() == null)
      cab.setFecDoc(LocalDateTime.now());
    cab.getDetalles().clear();
    int i = 1;
    for (MmRequisDetDTO d : dto.getDetalles()) {
      MmRequisDet det = mapper.map(d, MmRequisDet.class);
      det.setCodSociedad(cab.getCodSociedad());
      det.setNroDoc(nro);
      det.setNroItem((long) i++);
      det.setEstado("PENDIENTE");
      det.setOpcMant("ACTIVO");
      if (det.getNcantidadRecibida() == null)
        det.setNcantidadRecibida(BigDecimal.ZERO);
      det.setCab(cab);
      cab.getDetalles().add(det);
    }
    cab = cabRepo.save(cab);
    return toDTO(cab);
  }

  @Transactional(readOnly = true)
  public MmRequisCabDTO obtener(String codSoc, String nroDoc) {
    MmRequisCabId id = new MmRequisCabId(codSoc, nroDoc);
    MmRequisCab cab = cabRepo.findById(id)
        .orElseThrow(() -> new ModelNotFoundException("Requisicion no encontrada " + nroDoc));
    return toDTO(cab);
  }

  @Transactional
  public MmRequisCabDTO actualizar(String codSoc, String nroDoc, MmRequisCabDTO dto, String username) {
    MmRequisCabId id = new MmRequisCabId(codSoc, nroDoc);
    MmRequisCab cab = cabRepo.findById(id).orElseThrow(() -> new ModelNotFoundException("No existe " + nroDoc));
    if ("ANULADO".equals(cab.getEstado()))
      throw new ModelNotFoundException("No se puede modificar documento anulado");
    dto.setCodSociedad(codSoc);
    validarMaestros(dto);
    cab.setFecReq(dto.getFecReq());
    cab.setCcodCencos(dto.getCcodCencos());
    cab.setCcodPerson(dto.getCcodPerson());
    cab.setLugarEntr(dto.getLugarEntr());
    cab.setCcodProveedor(dto.getCcodProveedor());
    cab.setTipPrio(dto.getTipPrio());
    cab.setObserv(dto.getObserv());
    cab.setNotaEntrada(dto.getNotaEntrada());
    cab.setUserMod(username.toUpperCase());
    cab.setUserSisDate(LocalDateTime.now());
    // orphanRemoval=true se encarga del borrado al hacer clear(): no usar
    // borrado masivo aquí (deleted object would be re-saved by cascade).
    cab.getDetalles().clear();
    int i = 1;
    for (MmRequisDetDTO d : dto.getDetalles()) {
      MmRequisDet det = mapper.map(d, MmRequisDet.class);
      det.setCodSociedad(codSoc);
      det.setNroDoc(nroDoc);
      det.setNroItem((long) i++);
      det.setEstado("PENDIENTE");
      det.setOpcMant("ACTIVO");
      if (det.getNcantidadRecibida() == null)
        det.setNcantidadRecibida(BigDecimal.ZERO);
      det.setCab(cab);
      cab.getDetalles().add(det);
    }
    cab = cabRepo.save(cab);
    return toDTO(cab);
  }

  @Transactional
  public void anular(String codSoc, String nroDoc, String username) {
    MmRequisCab cab = cabRepo.findById(new MmRequisCabId(codSoc, nroDoc))
        .orElseThrow(() -> new ModelNotFoundException("No existe " + nroDoc));
    cab.setEstado("ANULADO");
    cab.setOpcMant("ANULADO");
    cab.setUserMod(username.toUpperCase());
    cab.setUserSisDate(LocalDateTime.now());
    cabRepo.save(cab);
  }

  @Transactional
  public void eliminar(String codSoc, String nroDoc) {
    MmRequisCabId id = new MmRequisCabId(codSoc, nroDoc);
    if (!cabRepo.existsById(id))
      throw new ModelNotFoundException("No existe " + nroDoc);
    cabRepo.deleteById(id);
  }

  @Transactional(readOnly = true)
  public Page<MmRequisCabDTO> listar(String codSoc, String estado, String cencos, String prio, LocalDateTime fecIni,
      LocalDateTime fecFin, String q, Pageable pageable) {
    String qq = q != null ? q.toUpperCase() : null;
    return cabRepo.filtrar(codSoc, estado, cencos, prio, fecIni, fecFin, qq, pageable).map(this::toDTO);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existeSociedad(String codSociedad) {
    return codSociedad != null && socRepo.existsById(codSociedad);
  }

  @Override
  @Transactional(readOnly = true)
  public List<String> listarCodSociedades() {
    return socRepo.findAll().stream().map(s -> s.getCodSociedad()).toList();
  }

  private MmRequisCabDTO toDTO(MmRequisCab cab) {
    MmRequisCabDTO dto = mapper.map(cab, MmRequisCabDTO.class);
    dto.setDetalles(mapper.mapList(cab.getDetalles(), MmRequisDetDTO.class));
    return dto;
  }
}
