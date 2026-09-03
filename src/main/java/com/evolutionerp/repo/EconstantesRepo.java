
package com.evolutionerp.repo;
import com.evolutionerp.model.Econstantes;
import com.evolutionerp.model.EconstantesId;
import java.util.List;
public interface EconstantesRepo extends IGenericRepo<Econstantes,EconstantesId> {
  List<Econstantes> findByCodSociedadAndApp(String codSociedad, String app);
}
