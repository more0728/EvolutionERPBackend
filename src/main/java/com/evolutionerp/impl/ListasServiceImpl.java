
package com.evolutionerp.impl;
import com.evolutionerp.model.*;
import com.evolutionerp.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@Service @RequiredArgsConstructor
public class ListasServiceImpl implements com.evolutionerp.service.ListasService {
  private final EPersonalRepo perRepo;
  private final BProveedorRepo provRepo;
  private final MmaterialRepo matRepo;
  public List<EPersonal> listarPersonal(String codSoc){ return perRepo.findByCodSociedad(codSoc); }
  public List<BProveedor> listarProveedores(String q){ return q==null||q.isBlank()? provRepo.findAll(): provRepo.search(q.toUpperCase()); }
  public List<Mmaterial> buscarMaterial(String q){ return matRepo.search(q.toUpperCase()); }
}
