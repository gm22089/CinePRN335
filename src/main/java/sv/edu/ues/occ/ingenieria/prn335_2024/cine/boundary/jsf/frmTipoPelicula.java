package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoPeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPelicula;

import java.util.logging.Logger;

@Named
@ViewScoped
public class frmTipoPelicula extends AbstractFrm<TipoPelicula> {
    @Inject
    TipoPeliculaBean tpBean;
    @Inject
    FacesContext fc;

    @Override
    public void instanciarRegistro() {
        this.registro = new TipoPelicula();
        registro.setActivo(true);
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<TipoPelicula> getAbstractDataPersist() {
        return tpBean;
    }



    @Override
    public String getIdByObject(TipoPelicula object) {
        if (object.getIdTipoPelicula() != null) {
            return object.getIdTipoPelicula().toString();
        }
        return null;
    }

    @Override
    public TipoPelicula getObjectById(String id) {
        if (id != null && modelo != null && modelo.getWrappedData()!=null) {
            return modelo.getWrappedData().stream().
                    filter(r -> r.getIdTipoPelicula().toString().equals(id)).findFirst().
                    orElseGet(()->{
                        Logger.getLogger("no se ha encontrado el objeto");
                        return null;
                    });
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<TipoPelicula> event) {
        TipoPelicula tipoPelicula = (TipoPelicula) event.getObject();
        FacesMessage mensaje=new FacesMessage("pelicula Seleccionada", tipoPelicula.getNombre());
        fc.addMessage(null, mensaje);
        this.estado=ESTADO_CRUD.MODIFICAR;
    }

    @Override
    public String paginaNombre() {
        return "Tipo Pelicula";
    }
}
