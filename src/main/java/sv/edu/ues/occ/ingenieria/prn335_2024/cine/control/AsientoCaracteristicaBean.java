package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.Stateless;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.*;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@Stateless
public class AsientoCaracteristicaBean extends AbstractDataPersist<AsientoCaracteristica> implements Serializable {
    @PersistenceContext(unitName = "cinePU")
    EntityManager em;

    public AsientoCaracteristicaBean() {
        super(AsientoCaracteristica.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public int countAsientoCaracteristica() {
        try {
            TypedQuery<Long> q = em.createNamedQuery("AsientoCaracteristica.countAll", Long.class);
            return q.getSingleResult().intValue();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }

    public List<TipoAsiento> findAllTiposAsiento() {
        try {
            TypedQuery<TipoAsiento> q = em.createNamedQuery("TipoAsiento.findAll", TipoAsiento.class);
            q.setFirstResult(0);
            q.setMaxResults(Integer.MAX_VALUE);
            return q.getResultList();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return List.of();
    }

    public int countByIdAsientoCaracteristica(final Asiento idAsiento) {
        try {
            TypedQuery<Long> q = em.createNamedQuery("AsientoCaracteristica.countCaracteristicaByIdAsiento", Long.class);
            q.setParameter("idAsiento", idAsiento.getIdAsiento());
            return q.getSingleResult().intValue();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }

    public List<AsientoCaracteristica> findByIdAsiento(final Asiento idAsiento, int first, int last) {
        try {
            TypedQuery<AsientoCaracteristica> q = em.createNamedQuery("AsientoCaracteristica.findCaracteristicaByIdAsiento", AsientoCaracteristica.class);
            q.setParameter("idAsiento", idAsiento.getIdAsiento());
            q.setFirstResult(first);
            q.setMaxResults(last);
            return q.getResultList();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return List.of();
    }

   /* public List<Object[]> findCaracterAndTipo(Asiento asiento) {
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
    }*/





}
