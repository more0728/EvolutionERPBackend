
package com.example.evolutionerp.repo;
import com.example.evolutionerp.model.EPersonal;
import java.util.List;
public interface EPersonalRepo extends IGenericRepo<EPersonal,String> {
  List<EPersonal> findByCodSociedad(String codSociedad);
}
