
package com.evolutionerp.repositories;

import com.evolutionerp.entities.EcCosto;
import com.evolutionerp.entities.EcCosto.EcCostoId;
import java.util.List;

public interface EcCostoRepo extends IGenericRepo<EcCosto, EcCostoId> {
  List<EcCosto> findByCodSociedad(String codSociedad);
}
