
package com.example.evolutionerp.service;
import com.example.evolutionerp.model.BProveedor;
import com.example.evolutionerp.model.EPersonal;
import com.example.evolutionerp.model.Mmaterial;
import java.util.List;
public interface ListasService {
  List<EPersonal> listarPersonal(String codSoc);
  List<BProveedor> listarProveedores(String q);
  List<Mmaterial> buscarMaterial(String q);
}
