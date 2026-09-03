
package com.example.evolutionerp.service.impl;
import com.example.evolutionerp.repo.IGenericRepo;
import com.example.evolutionerp.service.ICRUD;
import java.util.List;
public abstract class CRUDImpl<T,ID> implements ICRUD<T,ID> {
  protected abstract IGenericRepo<T,ID> getRepo();
  public T save(T t){ return getRepo().save(t); }
  public T update(T t, ID id){ return getRepo().save(t); }
  public List<T> findAll(){ return getRepo().findAll(); }
  public T findById(ID id){ return getRepo().findById(id).orElse(null); }
  public void delete(ID id){ getRepo().deleteById(id); }
}
