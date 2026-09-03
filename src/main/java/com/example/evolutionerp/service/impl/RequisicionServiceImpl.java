
package com.example.evolutionerp.service.impl;
import com.example.evolutionerp.dtos.*;
import com.example.evolutionerp.exception.ModelNotFoundException;
import com.example.evolutionerp.model.*;
import com.example.evolutionerp.repo.*;
import com.example.evolutionerp.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
@Service @RequiredArgsConstructor
public class RequisicionServiceImpl implements com.example.evolutionerp.service.RequisicionService {
  private final MmRequisCabRepo cabRepo;
  private final MmRequisDetRepo detRepo;
  private final EcCostoRepo costoRepo;
  private final EconstantesRepo constRepo;
  private final EsociedadRepo socRepo;
  private final EnumRangosRepo rangosRepo;
  private final MapperUtil mapper;

  @Transactional
  public MmRequisCabDTO crear(MmRequisCabDTO dto, String username){
    if(!socRepo.existsById(dto.getCodSociedad())) throw new ModelNotFoundException("Sociedad no existe: "+dto.getCodSociedad());
    String nro = dto.getNroDoc();
    if(nro==null || nro.isBlank()) nro = rangosRepo.nextCorrelativo(dto.getCodSociedad(),"COM");
    MmRequisCab cab = mapper.map(dto, MmRequisCab.class);
    cab.setNroDoc(nro);
    cab.setUserSis(username.toUpperCase());
    cab.setUserMod(username.toUpperCase());
    cab.setUserSisDate(LocalDateTime.now());
    if(cab.getFecDoc()==null) cab.setFecDoc(LocalDateTime.now());
    cab.getDetalles().clear();
    int i=1;
    for(MmRequisDetDTO d: dto.getDetalles()){
      MmRequisDet det = mapper.map(d, MmRequisDet.class);
      det.setCodSociedad(cab.getCodSociedad());
      det.setNroDoc(nro);
      det.setNroItem((long)i++);
      det.setCab(cab);
      cab.getDetalles().add(det);
    }
    cab = cabRepo.save(cab);
    return toDTO(cab);
  }

  @Transactional(readOnly=true)
  public MmRequisCabDTO obtener(String codSoc, String nroDoc){
    MmRequisCabId id=new MmRequisCabId(codSoc,nroDoc);
    MmRequisCab cab=cabRepo.findById(id).orElseThrow(()->new ModelNotFoundException("Requisicion no encontrada "+nroDoc));
    return toDTO(cab);
  }

  @Transactional
  public MmRequisCabDTO actualizar(String codSoc, String nroDoc, MmRequisCabDTO dto, String username){
    MmRequisCabId id=new MmRequisCabId(codSoc,nroDoc);
    MmRequisCab cab=cabRepo.findById(id).orElseThrow(()->new ModelNotFoundException("No existe "+nroDoc));
    if("ANULADO".equals(cab.getEstado())) throw new ModelNotFoundException("No se puede modificar documento anulado");
    cab.setFecReq(dto.getFecReq()); cab.setCcodCencos(dto.getCcodCencos()); cab.setCcodPerson(dto.getCcodPerson());
    cab.setLugarEntr(dto.getLugarEntr()); cab.setCcodProveedor(dto.getCcodProveedor()); cab.setTipPrio(dto.getTipPrio());
    cab.setObserv(dto.getObserv()); cab.setNotaEntrada(dto.getNotaEntrada()); cab.setUserMod(username.toUpperCase());
    cab.setUserSisDate(LocalDateTime.now());
    detRepo.deleteByCodSociedadAndNroDoc(codSoc,nroDoc);
    cab.getDetalles().clear();
    int i=1;
    for(MmRequisDetDTO d: dto.getDetalles()){
      MmRequisDet det = mapper.map(d, MmRequisDet.class);
      det.setCodSociedad(codSoc); det.setNroDoc(nroDoc); det.setNroItem((long)i++); det.setCab(cab);
      cab.getDetalles().add(det);
    }
    cab=cabRepo.save(cab);
    return toDTO(cab);
  }

  @Transactional
  public void anular(String codSoc, String nroDoc, String username){
    MmRequisCab cab=cabRepo.findById(new MmRequisCabId(codSoc,nroDoc)).orElseThrow(()->new ModelNotFoundException("No existe "+nroDoc));
    cab.setEstado("ANULADO"); cab.setOpcMant("ANULADO"); cab.setUserMod(username.toUpperCase()); cab.setUserSisDate(LocalDateTime.now());
    cabRepo.save(cab);
  }

  public void eliminar(String codSoc, String nroDoc){ cabRepo.deleteById(new MmRequisCabId(codSoc,nroDoc)); }

  @Transactional(readOnly=true)
  public Page<MmRequisCabDTO> listar(String codSoc, String estado, String cencos, String prio, LocalDateTime fecIni, LocalDateTime fecFin, String q, Pageable pageable){
    String qq = q!=null ? q.toUpperCase() : null;
    return cabRepo.filtrar(codSoc, estado, cencos, prio, fecIni, fecFin, qq, pageable).map(this::toDTO);
  }

  public List<EcCostoDTO> listarCentros(String codSoc){ return mapper.mapList(costoRepo.findByCodSociedad(codSoc), EcCostoDTO.class); }
  public List<EconstantesDTO> listarPrioridades(String codSoc){ return mapper.mapList(constRepo.findByCodSociedadAndApp(codSoc,"PRIO"), EconstantesDTO.class); }
  public List<EsociedadDTO> listarSociedades(){ return mapper.mapList(socRepo.findAll(), EsociedadDTO.class); }
  public List<MmRequisCabDTO> listarSociedadesPorUsuario(String username){ return listarSociedades().stream().map(s->MmRequisCabDTO.builder().codSociedad(s.getCodSociedad()).build()).toList(); }

  private MmRequisCabDTO toDTO(MmRequisCab cab){
    MmRequisCabDTO dto=mapper.map(cab, MmRequisCabDTO.class);
    dto.setDetalles(mapper.mapList(cab.getDetalles(), MmRequisDetDTO.class));
    return dto;
  }
}
