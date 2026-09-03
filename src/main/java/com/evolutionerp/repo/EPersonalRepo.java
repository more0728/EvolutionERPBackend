
package com.evolutionerp.repo;
import com.evolutionerp.model.EPersonal;
import java.util.List;
public interface EPersonalRepo extends IGenericRepo<EPersonal,String> {
  List<EPersonal> findByCodSociedad(String codSociedad);
}
