package ru.edu.pgtk.library.jsf;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import ru.edu.pgtk.library.ejb.CategoriesEJB;
import ru.edu.pgtk.library.entity.Category;

@ManagedBean(name = "categoriesMB")
@ViewScoped
public class CategoriesMB extends GenericBean<Category> {
  
  @EJB
  private CategoriesEJB ejb;
  
  @Override
  public void add() {
    item = new Category();
    edit = true;
  }
  
  public void confirmDelete() {
    try {
      if ((null != item) && delete) {
        ejb.delete(item);
        resetState();
      }
    } catch (Exception e) {
      addMessage(e);
    }
  }

  public void save() {
    try {
      if ((null != item) && edit) {
        ejb.save(item);
        resetState();
      }
    } catch (Exception e) {
      addMessage(e);
    }
  }
}
