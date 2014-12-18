package ru.edu.pgtk.library.ejb;

import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import ru.edu.pgtk.library.entity.Category;

@Stateless
@Named("categoriesEJB")
public class CategoriesEJB {

  @PersistenceContext(unitName = "defaultPU")
  private EntityManager em;

  public Category get(final int id) {
    Category result = em.find(Category.class, id);
    if (null != result) {
      return result;
    }
    throw new EJBException("Category not found with id " + id);
  }

  public List<Category> fetchAll() {
    TypedQuery<Category> q = em.createQuery("SELECT c FROM Category c", Category.class);
    return q.getResultList();
  }

  public List<Category> findByName(final String name) {
    TypedQuery<Category> q = em.createQuery(
            "SELECT c FROM Category c WHERE c.name LIKE :name", Category.class);
    q.setParameter("name", "%" + name + "%");
    return q.getResultList();
  }

  public Category save(Category item) {
    if (item.getId() == 0) {
      em.persist(item);
      return item;
    } else {
      return em.merge(item);
    }
  }

  public void delete(Category item) {
    Category cat = em.find(Category.class, item.getId());
    if (null != cat) {
      em.remove(cat);
    }
  }
}
