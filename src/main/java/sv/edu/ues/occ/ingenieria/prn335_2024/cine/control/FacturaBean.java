package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Factura;

import java.io.Serializable;

@Stateless
@LocalBean
public class FacturaBean extends AbstractDataPersist<Factura> implements Serializable {
    @PersistenceContext(unitName = "cinePU")
    EntityManager m;

    public FacturaBean() {
        super(FacturaBean.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return null;
    }
}