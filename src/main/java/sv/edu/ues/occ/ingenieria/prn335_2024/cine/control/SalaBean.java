package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class SalaBean extends AbstractDataPersist<Sala> implements Serializable {
    @PersistenceContext(unitName = "cinePU")
    EntityManager em;

    public SalaBean() {
        super(Sala.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<TipoSala> findByIdTipoSala(Integer idTipoSala, int first, int max) {
        if (idTipoSala != null && first >= 0 && max > 0) {
            try {
                if (em != null) {
                    Query q = em.createNamedQuery("Sala.findByIdTipoSala");
                    q.setParameter("idTipoSala", idTipoSala);
                    q.setFirstResult(first);
                    q.setMaxResults(max);
                    return q.getResultList();
                }
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }

        }
        return Collections.emptyList();
    }

    //PARCIAL XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    // String jpql="SELECT p FROM Pelicula p";
    // Query query=em.createQuery(jpql);


}