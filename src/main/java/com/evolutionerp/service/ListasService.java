
package com.evolutionerp.service;
import com.evolutionerp.model.BProveedor;
import com.evolutionerp.model.EPersonal;
import com.evolutionerp.model.Mmaterial;
import java.util.List;
public interface ListasService {
  List<EPersonal> listarPersonal(String codSoc);
  List<BProveedor> listarProveedores(String q);
  List<Mmaterial> buscarMaterial(String q);
}
