
package com.evolutionerp.servicesimplements;

import com.evolutionerp.entities.*;
import com.evolutionerp.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ListasServiceImpl implements com.evolutionerp.servicesinterfaces.ListasService {
  private final EPersonalRepo perRepo;
  private final BProveedorRepo provRepo;
  private final MmaterialRepo matRepo;

  public ListasServiceImpl(EPersonalRepo perRepo, BProveedorRepo provRepo, MmaterialRepo matRepo) {
    this.perRepo = perRepo;
    this.provRepo = provRepo;
    this.matRepo = matRepo;
  }

  @Transactional(readOnly = true)
  public List<EPersonal> listarPersonal(String codSoc) {
    return perRepo.findByCodSociedad(codSoc);
  }

  @Transactional(readOnly = true)
  public List<BProveedor> listarProveedores(String q) {
    return q == null || q.isBlank() ? provRepo.findAll() : provRepo.search(q.toUpperCase());
  }

  @Transactional(readOnly = true)
  public List<Mmaterial> buscarMaterial(String q) {
    return matRepo.search(q.toUpperCase());
  }
}
