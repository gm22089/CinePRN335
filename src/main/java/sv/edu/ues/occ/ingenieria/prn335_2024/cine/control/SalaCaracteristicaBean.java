package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.Stateless;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.SalaCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@Stateless
public class SalaCaracteristicaBean extends AbstractDataPersist<SalaCaracteristica> implements Serializable {
    @PersistenceContext(unitName = "cinePU")
    EntityManager em;

    public SalaCaracteristicaBean() {
        super(SalaCaracteristica.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<TipoSala> findAllTiposSala() {
        try {
            TypedQuery<TipoSala> q = em.createNamedQuery("SalaCaracteristica.findAllTipoSala", TipoSala.class);
            q.setFirstResult(0);
            q.setMaxResults(Integer.MAX_VALUE);
            return q.getResultList();
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        }
        return List.of();
    }


    public int countPeliculaCarracteristica(final Sala idSala) {
        try {
            TypedQuery<Long> q = em.createNamedQuery("SalaCaracteristica.countByIdSalaCaracteristica", Long.class);
            q.setParameter("idSala", idSala.getIdSala());
            return q.getSingleResult().intValue();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }
    public List<SalaCaracteristica> findByIdSala(final Sala idTipoSala, int first, int last) {
        try {

            System.out.println();
            TypedQuery<SalaCaracteristica> q = em.createNamedQuery("SalaCaracteristica.findByIdCaracteristicasBySala", SalaCaracteristica.class);
            q.setParameter("idSala", idTipoSala.getIdSala());
            q.setFirstResult(first);
            q.setMaxResults(last);
            return q.getResultList();
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        }
        return List.of();
    }



}