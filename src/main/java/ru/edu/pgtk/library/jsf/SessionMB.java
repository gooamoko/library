package ru.edu.pgtk.library.jsf;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import ru.edu.pgtk.library.ejb.UsersEJB;
import ru.edu.pgtk.library.entity.Publication;
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

  protected void addMessage(final Exception e) {
    FacesContext context = FacesContext.getCurrentInstance();
    String message = "Exception class " + e.getClass().getName() + " with message " + e.getMessage();
    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "Error"));
  }

  public String doLogin() {
    user = usersBean.get(login, password);
    return "/index";
  }

  public String doLogout() {
    user = null;
    return "/index";
  }

  public String changePassword() {
    try {
      // Обновляем пароль и сохраняем пользователя
      user.updatePassword();
      usersBean.save(user);
      return "/index";
    } catch (Exception e) {
      // Скорее всего парль не совпадает с подтверждением
      addMessage(e);
      return null;
    }
  }

  public void download(Publication item) throws IOException {
    // Get the FacesContext
    FacesContext facesContext = FacesContext.getCurrentInstance();
    // Get HTTP response
    ExternalContext ec = facesContext.getExternalContext();
    // Set response headers
    ec.responseReset();   // Reset the response in the first place
    ec.setResponseContentType(item.getContentType());  // Set only the content type
    // Установка данного заголовка будет иннициировать процесс скачки файла вместо его отображения в браузере.
    ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + item.getFileName() + "\"");
    try (OutputStream responseOutputStream = ec.getResponseOutputStream()) {
      responseOutputStream.write(item.getData());
      responseOutputStream.flush();
    } catch (IOException e) {
      addMessage(e);
    }
    facesContext.responseComplete();
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
