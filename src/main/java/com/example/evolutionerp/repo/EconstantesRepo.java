
package com.example.evolutionerp.repo;
import com.example.evolutionerp.model.Econstantes;
import com.example.evolutionerp.model.EconstantesId;
import java.util.List;
public interface EconstantesRepo extends IGenericRepo<Econstantes,EconstantesId> {
  List<Econstantes> findByCodSociedadAndApp(String codSociedad, String app);
}
