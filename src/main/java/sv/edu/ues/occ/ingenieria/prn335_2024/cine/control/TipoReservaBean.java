package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.resource.spi.IllegalStateException;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoReserva;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;
@Stateless
@LocalBean
public class TipoReservaBean extends AbstractDataPersist<TipoReserva> {

    @PersistenceContext(unitName = "cinePU")
    EntityManager em;

    public TipoReservaBean() {
        super(TipoReserva.class);
    }
    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}