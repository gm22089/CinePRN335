package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sucursal;

import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class SucursalBean extends AbstractDataPersist<Sucursal> implements Serializable {
    @PersistenceContext(unitName = "cinePU")
    EntityManager Em;
    public SucursalBean() {
        super(Sucursal.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return Em;
    }

    public List<Object[]> getSucursalAnsSalas(){
        try {
            return Em.createNamedQuery("Sucursal.findSucursalAndSalas").getResultList();

        }catch (Exception ex){
            Logger.getLogger(SalaBean.class.getName()).log(Level.SEVERE, "error al buscar sucursales y salas", ex);
        }
        return List.of();
    }
    public List<Sucursal> getAllSucursales(){
        try {
            return Em.createNamedQuery("Sucursal.findAll",Sucursal.class).getResultList();
        }catch (Exception ex){
            Logger.getLogger(SalaBean.class.getName()).log(Level.SEVERE, "error al buscar sucursales y salas", ex);
        }


        return List.of();
    }
}