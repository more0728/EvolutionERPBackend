
package com.evolutionerp.repositories;

import com.evolutionerp.entities.MmRequisCab;
import com.evolutionerp.entities.MmRequisCabId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MmRequisCabRepo extends IGenericRepo<MmRequisCab, MmRequisCabId> {
  @Query("SELECT c FROM MmRequisCab c WHERE c.codSociedad=:soc AND (:estado IS NULL OR c.estado=:estado) AND (:cencos IS NULL OR c.ccodCencos=:cencos) AND (:prio IS NULL OR c.tipPrio=:prio) AND (CAST(:fecIni AS timestamp) IS NULL OR c.fecDoc >= :fecIni) AND (CAST(:fecFin AS timestamp) IS NULL OR c.fecDoc <= :fecFin) AND (:q IS NULL OR UPPER(c.observ) LIKE %:q% OR c.nroDoc LIKE %:q%)")
  Page<MmRequisCab> filtrar(@Param("soc") String soc, @Param("estado") String estado, @Param("cencos") String cencos,
      @Param("prio") String prio, @Param("fecIni") java.time.LocalDateTime fecIni,
      @Param("fecFin") java.time.LocalDateTime fecFin, @Param("q") String q, Pageable pageable);
}
