package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoReserva;

@Stateless
@LocalBean
@Dependent
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

    @Override
    public TipoReserva findById(Object idTipoReserva) throws IllegalArgumentException, IllegalStateException {
        return super.findById(idTipoReserva);
    }
}