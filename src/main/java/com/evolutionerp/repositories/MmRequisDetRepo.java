
package com.evolutionerp.repositories;

import com.evolutionerp.entities.MmRequisDet;
import com.evolutionerp.entities.MmRequisDet.MmRequisDetId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface MmRequisDetRepo extends IGenericRepo<MmRequisDet, MmRequisDetId> {
  @Modifying
  @Transactional
  void deleteByCodSociedadAndNroDoc(String codSociedad, String nroDoc);

  List<MmRequisDet> findByCodSociedadAndNroDocOrderByNroItemAsc(String codSociedad, String nroDoc);
}
