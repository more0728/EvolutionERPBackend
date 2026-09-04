
package com.evolutionerp.servicesinterfaces;

import com.evolutionerp.entities.BProveedor;
import com.evolutionerp.entities.EPersonal;
import com.evolutionerp.entities.Mmaterial;
import java.util.List;

public interface ListasService {
  List<EPersonal> listarPersonal(String codSoc);

  List<BProveedor> listarProveedores(String q);

  List<Mmaterial> buscarMaterial(String q);
}
