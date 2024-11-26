package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.*;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class AsientoBean extends AbstractDataPersist<Asiento> implements Serializable {
    @PersistenceContext(unitName = "cinePU")
    EntityManager em;
    public AsientoBean() {
        super(Asiento.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }



    public List<Asiento> findAsientosBySalaandProgramacion(Sala sala, Programacion programacion) {
        try {
            return em.createNamedQuery("Asiento.findAsientosBySalaandProgramacion", Asiento.class).
                    setParameter("idSala", sala).setParameter("idProgramacion",programacion).getResultList();
        }catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return List.of();
    }

    public List<Asiento> findIdAsientoBySala(final Sala idSala, int first, int last) {
        try {
            TypedQuery<Asiento> q = em.createNamedQuery("Asiento.findIdAsientoBySala", Asiento.class);
            q.setParameter("idSala", idSala.getIdSala());
            q.setFirstResult(first);
            q.setMaxResults(last);
            return q.getResultList();
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        }
        return List.of();
    }

    public int countAsientos(final Sala idSala) {
        try {
            TypedQuery<Long> q = em.createNamedQuery("Asiento.countByIdAsiento", Long.class);
            q.setParameter("idSala", idSala.getIdSala());
            return q.getSingleResult().intValue();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }

    public List<AsientoCaracteristica> findAllAsientoCaracteristica() {
        try {
            TypedQuery<AsientoCaracteristica> q = em.createNamedQuery("Asiento.findAllAsientoCaracteristica", AsientoCaracteristica.class);
            q.setFirstResult(0);
            q.setMaxResults(Integer.MAX_VALUE);
            return q.getResultList();
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        }
        return List.of();
    }


}
