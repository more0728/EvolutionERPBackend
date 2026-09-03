
package com.example.evolutionerp.repo;
import com.example.evolutionerp.model.BProveedor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
public interface BProveedorRepo extends IGenericRepo<BProveedor,String> {
  @Query("SELECT b FROM BProveedor b WHERE UPPER(b.nomProv) LIKE %:q% OR b.ccodProveedor LIKE %:q%")
  List<BProveedor> search(@Param("q") String q);
}
