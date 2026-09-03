
package com.evolutionerp.repositories;
import com.evolutionerp.entities.Econstantes;
import com.evolutionerp.entities.EconstantesId;
import java.util.List;
public interface EconstantesRepo extends IGenericRepo<Econstantes,EconstantesId> {
  List<Econstantes> findByCodSociedadAndApp(String codSociedad, String app);
}
