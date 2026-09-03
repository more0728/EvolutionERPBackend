
package com.evolutionerp.repositories;
import com.evolutionerp.entities.EPersonal;
import java.util.List;
public interface EPersonalRepo extends IGenericRepo<EPersonal,String> {
  List<EPersonal> findByCodSociedad(String codSociedad);
}
