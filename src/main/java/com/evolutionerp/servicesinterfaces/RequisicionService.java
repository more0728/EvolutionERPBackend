
package com.evolutionerp.servicesinterfaces;

import com.evolutionerp.dtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;

public interface RequisicionService {
  MmRequisCabDTO crear(MmRequisCabDTO dto, String username);

  MmRequisCabDTO obtener(String codSoc, String nroDoc);

  MmRequisCabDTO actualizar(String codSoc, String nroDoc, MmRequisCabDTO dto, String username);

  void anular(String codSoc, String nroDoc, String username);

  void eliminar(String codSoc, String nroDoc);

  Page<MmRequisCabDTO> listar(String codSoc, String estado, String cencos, String prio, LocalDateTime fecIni,
      LocalDateTime fecFin, String q, Pageable pageable);

  List<EcCostoDTO> listarCentros(String codSoc);

  List<EconstantesDTO> listarPrioridades(String codSoc);

  List<EsociedadDTO> listarSociedades();

  // Estilo KitchenHack: los controllers validan FK vía servicio, nunca vía repository.
  boolean existeSociedad(String codSociedad);

  List<String> listarCodSociedades();

  List<MmRequisCabDTO> listarSociedadesPorUsuario(String username);
}
