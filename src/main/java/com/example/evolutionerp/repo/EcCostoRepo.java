
package com.example.evolutionerp.repo;
import com.example.evolutionerp.model.EcCosto;
import com.example.evolutionerp.model.EcCostoId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
public interface EcCostoRepo extends IGenericRepo<EcCosto,EcCostoId> {
  List<EcCosto> findByCodSociedad(String codSociedad);
  @Query(value="SELECT * FROM evo.sp_list_eccosto(:soc)", nativeQuery=true)
  List<EcCosto> spListEccosto(@Param("soc") String soc);
}
