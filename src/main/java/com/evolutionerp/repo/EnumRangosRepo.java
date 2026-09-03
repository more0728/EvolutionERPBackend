
package com.evolutionerp.repo;
import com.evolutionerp.model.EnumRangos;
import com.evolutionerp.model.EnumRangosId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface EnumRangosRepo extends IGenericRepo<EnumRangos,EnumRangosId> {
  @Query(value="SELECT evo.next_correlativo(:soc,:app)", nativeQuery=true)
  String nextCorrelativo(@Param("soc") String soc, @Param("app") String app);
}
