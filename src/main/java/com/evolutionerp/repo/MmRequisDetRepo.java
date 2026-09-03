
package com.evolutionerp.repo;
import com.evolutionerp.model.MmRequisDet;
import com.evolutionerp.model.MmRequisDetId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
public interface MmRequisDetRepo extends IGenericRepo<MmRequisDet,MmRequisDetId> {
  @Modifying @Transactional void deleteByCodSociedadAndNroDoc(String codSociedad, String nroDoc);
}
