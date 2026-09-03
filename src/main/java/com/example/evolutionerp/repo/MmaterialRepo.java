
package com.example.evolutionerp.repo;
import com.example.evolutionerp.model.Mmaterial;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
public interface MmaterialRepo extends IGenericRepo<Mmaterial,String> {
  @Query("SELECT m FROM Mmaterial m WHERE UPPER(m.nomMaterial) LIKE %:q% OR m.codMaterial LIKE %:q%")
  List<Mmaterial> search(@Param("q") String q);
}
