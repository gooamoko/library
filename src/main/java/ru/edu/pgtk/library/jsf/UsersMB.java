package ru.edu.pgtk.library.jsf;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import ru.edu.pgtk.library.ejb.UsersEJB;
import ru.edu.pgtk.library.entity.User;

@ManagedBean(name = "personsMB")
@ViewScoped
public class UsersMB extends GenericBean<User> implements Serializable {

  @EJB
  private UsersEJB ejb;

  @Override
  public void add() {
    item = new User();
    edit = true;
  }

  public void changePassword() {
    try {
      item.updatePassword();
      ejb.save(item);
      resetState();
    } catch (Exception e) {
      addMessage(e);
    }
  }

  public List<User> getUsers() {
    return ejb.fetchAll();
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
