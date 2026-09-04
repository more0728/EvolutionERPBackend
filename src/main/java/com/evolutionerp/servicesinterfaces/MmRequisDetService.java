package com.evolutionerp.servicesinterfaces;

import com.evolutionerp.dtos.MmRequisDetDTO;
import java.util.List;

public interface MmRequisDetService {
  List<MmRequisDetDTO> listarPorDocumento(String codSociedad, String nroDoc);

  MmRequisDetDTO obtener(String codSociedad, String nroDoc, Long nroItem);

  MmRequisDetDTO crear(String codSociedad, String nroDoc, MmRequisDetDTO dto);

  MmRequisDetDTO actualizar(String codSociedad, String nroDoc, Long nroItem, MmRequisDetDTO dto);

  void eliminar(String codSociedad, String nroDoc, Long nroItem);
}
