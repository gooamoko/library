package ru.edu.pgtk.library.jsf;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import ru.edu.pgtk.library.ejb.UsersEJB;
import ru.edu.pgtk.library.entity.User;

/**
 * Сессионный бин, который будет хранить корзину и т.п.
 */
@ManagedBean(name = "sessionMB")
@SessionScoped
public class SessionMB implements Serializable {

  private transient User user;
  private String login;
  private String password;
  @EJB
  private transient UsersEJB usersBean;

  public String doLogin() {
    user = usersBean.get(login, password);
    return "/index";
  }
  
  public String doLogout() {
    user = null;
    return "/index";
  }
  
  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  
  public User getUser() {
    return user;
  }

  public boolean isLogged() {
    return user != null;
  }
  
  public boolean isAdmin() {
    return (null != user) && user.isAdmin();
  }
}
