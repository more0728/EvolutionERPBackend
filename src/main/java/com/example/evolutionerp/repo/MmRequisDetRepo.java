
package com.example.evolutionerp.repo;
import com.example.evolutionerp.model.MmRequisDet;
import com.example.evolutionerp.model.MmRequisDetId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
public interface MmRequisDetRepo extends IGenericRepo<MmRequisDet,MmRequisDetId> {
  @Modifying @Transactional void deleteByCodSociedadAndNroDoc(String codSociedad, String nroDoc);
}
