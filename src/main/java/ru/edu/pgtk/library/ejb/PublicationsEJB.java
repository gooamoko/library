package ru.edu.pgtk.library.ejb;

import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import ru.edu.pgtk.library.entity.Publication;

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
}
