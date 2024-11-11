package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;
import java.io.Serializable;

@Stateless
@LocalBean
public class TipoSalaBean extends AbstractDataPersist<TipoSala> implements Serializable {
    @PersistenceContext(unitName = "CinePU")
    EntityManager en;

    public TipoSalaBean(){
        super(TipoSala.class);
    }

    /**
     * BUsca un TIpoSala en el repositorio por un ID
     * @param idTipoSala Identificador del tipo sala
     * @return null si no lo encuentra
     * @throws IllegalArgumentException SI le ID pasado es NULL o < a cero
     * @throws IllegalStateException SI hay un problema con el repositorio.
     */
 public TipoSala finById(final Integer idTipoSala)throws IllegalArgumentException, IllegalStateException{
        if(idTipoSala==null||idTipoSala<=0){
            throw new IllegalArgumentException("Parametro no valido: idTipoSala");
        }
        try {
            if (en==null){
                throw new IllegalStateException("Error al acceder al repositorio");
            }
        }catch (Exception ex){
            throw new IllegalStateException("Error de acceso al repositorio", ex);
        }
        return en.find(TipoSala.class, idTipoSala);
    }

    public int sumar (int primer, int segundo){
        return primer + segundo;
    }

    @Override
    public EntityManager getEntityManager(){
        return en;
    }

}
