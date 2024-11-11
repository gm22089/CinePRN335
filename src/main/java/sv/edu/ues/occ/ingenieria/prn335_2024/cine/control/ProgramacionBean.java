package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Programacion;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@LocalBean
@Stateless
public class ProgramacionBean extends AbstractDataPersist<Programacion> implements Serializable {
    @PersistenceContext(unitName = "cinePU")
    EntityManager em;

    public ProgramacionBean() {
        super(Programacion.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Programacion> getProgramacionByIdSala(Sala sala) {
        if (sala != null) {
            try {
                return em.createNamedQuery("Programacion.findProgramacionBySala", Programacion.class)
                        .setParameter("sala", sala)
                        .getResultList();
            } catch (Exception ex) {
                Logger.getLogger(ProgramacionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return List.of();
    }
}
