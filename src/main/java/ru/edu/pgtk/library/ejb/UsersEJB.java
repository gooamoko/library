package ru.edu.pgtk.library.ejb;

import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import ru.edu.pgtk.library.entity.User;

@Stateless
@Named("usersEJB")
public class UsersEJB {
  
  @PersistenceContext(unitName = "defaultPU")
  private EntityManager em;
  
  public User get(final int id) {
    User result = em.find(User.class, id);
    if (null != result) {
      return result;
    }
    throw new EJBException("User not found with id " + id);
  }
  
  public User get(final String login, final String password) {
    TypedQuery<User> q = em.createQuery(
            "SELECT p FROM User p WHERE (p.login LIKE :l) AND (p.passwordHash LIKE :p)", User.class);
    q.setParameter("l", login);
    q.setParameter("p", Utils.getHash(password));
    return q.getSingleResult();
  }
  
  public List<User> fetchAll() {
    TypedQuery<User> q = em.createQuery("SELECT p FROM User p", User.class);
    return q.getResultList();
  }
  
  public List<User> findByFirstName(final String firstName) {
    TypedQuery<User> q = em.createQuery(
        "SELECT p FROM User p WHERE p.fullName = :fname", User.class);
    q.setParameter("fname", firstName);
    return q.getResultList();
  }
  
  public User save(User item) {
    if (item.getId() == 0) {
      item.updatePassword();
      em.persist(item);
      return item;
    } else {
      return em.merge(item);
    }
  }
  
  public void delete(User item) {
    User psn = em.find(User.class, item.getId());
    if (null != psn) {
      em.remove(psn);
    }
  }
}
