package ru.edu.pgtk.library.entity;

import java.io.Serializable;
import javax.ejb.EJBException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import static ru.edu.pgtk.library.ejb.Utils.getHash;

@Entity
@Table(name = "users")
public class User implements Serializable {

  @Id
  @Column(name = "usr_pcode")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "usr_fullname", nullable = false, length = 128)
  private String fullName;

  @Column(name = "usr_admin", nullable = false)
  private boolean admin;

  @Column(name = "usr_login", nullable = false, length = 50)
  private String login;

  @Column(name = "usr_password", nullable = false, length = 128)
  private String passwordHash;

  @Transient
  private String password = null;

  @Transient
  private String confirm = null;

  public void updatePassword() {
    if ((null != password) && (!password.isEmpty())) {
      if (password.contentEquals(confirm)) {
        passwordHash = getHash(password);
      } else {
        throw new EJBException("Password and confirmation doesn't equals!");
      }
    } else {
      throw new EJBException("Empty passwords doesn't allowed!");
    }
  }
  
  public int getId() {
    return id;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
  
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirm() {
    return confirm;
  }

  public void setConfirm(String confirm) {
    this.confirm = confirm;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public boolean isAdmin() {
    return admin;
  }

  public void setAdmin(boolean admin) {
    this.admin = admin;
  }
}
