package ru.edu.pgtk.library.ejb;

import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import ru.edu.pgtk.library.entity.Category;
import ru.edu.pgtk.library.entity.Publication;
import ru.edu.pgtk.library.entity.User;

@Stateless
@Named("publicationsEJB")
public class PublicationsEJB {

  @PersistenceContext(unitName = "defaultPU")
  private EntityManager em;

  public Publication get(final int id) {
    Publication result = em.find(Publication.class, id);
    if (null != result) {
      return result;
    }
    throw new EJBException("Publication not found with id " + id);
  }

  public List<Publication> fetchAll() {
    TypedQuery<Publication> q = em.createQuery(
            "SELECT p FROM Publication p ORDER BY p.name", Publication.class);
    return q.getResultList();
  }
  
  public List<Publication> findByUser(final User user) {
    TypedQuery<Publication> q = em.createQuery(
            "SELECT p FROM Publication p WHERE (p.user = :u) ORDER BY p.name", Publication.class);
    q.setParameter("u", user);
    return q.getResultList();
  }
  
  public Publication save(Publication item) {
    if (item.getCategoryCode() > 0) {
      Category c = em.find(Category.class, item.getCategoryCode());
      if (null != c) {
        item.setCategory(c);
      } else {
        throw new EJBException("Category not found with id " + item.getCategoryCode());
      }
    } else {
      throw new EJBException("Wrong category code " + item.getCategoryCode());
    }
    if (null == item.getUser()) {
      throw new EJBException("User is null!");
    }
    if (0 == item.getId()) {
      em.persist(item);
      return item;
    } else {
      return em.merge(item);
    }
  }
  
  public void delete(final Publication item) {
    Publication p = em.find(Publication.class, item.getId());
    if (null != p) {
      em.remove(p);
    }
  }
}
