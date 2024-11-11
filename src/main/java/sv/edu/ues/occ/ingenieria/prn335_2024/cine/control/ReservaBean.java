package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Reserva;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@LocalBean
@Stateless
public class ReservaBean extends AbstractDataPersist<Reserva> implements Serializable {
    @PersistenceContext(unitName = "cinePU")
    private EntityManager em;

    public ReservaBean() {
        super(Reserva.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Reserva> findByUsuario(Long usuarioId) {
        try {
            return em.createNamedQuery("Reserva.findByUsuario", Reserva.class)
                    .setParameter("usuarioId", usuarioId)
                    .getResultList();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al buscar reservas del usuario", e);
        }
        return List.of();
    }

}