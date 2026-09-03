
package com.evolutionerp.repositories;
import com.evolutionerp.entities.EnumRangos;
import com.evolutionerp.entities.EnumRangosId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface EnumRangosRepo extends IGenericRepo<EnumRangos,EnumRangosId> {
  @Query(value="SELECT evo.next_correlativo(:soc,:app)", nativeQuery=true)
  String nextCorrelativo(@Param("soc") String soc, @Param("app") String app);
}
