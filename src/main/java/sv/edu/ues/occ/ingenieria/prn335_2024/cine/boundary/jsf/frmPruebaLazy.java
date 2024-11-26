package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.filter.GreaterThanEqualsFilterConstraint;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.utils.LazyDataModelBuilder;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;

import java.io.Serializable;
import java.security.PrivateKey;
import java.util.List;

@Named
@ViewScoped
public class frmPruebaLazy extends AbstractFrm<Pelicula> implements Serializable {
    @Inject
    PeliculaBean peliculaBean;
    @Inject
    FacesContext fc;


    @Override
    public void instanciarRegistro() {
        registro = new Pelicula();

    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<Pelicula> getAbstractDataPersist() {
        return peliculaBean;
    }


    @Override
    public String paginaNombre() {
        return "";
    }

    @Override
    public String getIdByObject(Pelicula object) {
        if (object != null) {
            return object.getIdPelicula().toString();
        }
        return "";
    }

    @Override
    public Pelicula getObjectById(String id) {
        if (id != null && modelo != null && modelo.getWrappedData()!=null) {
            return modelo.getWrappedData().stream().filter(p -> p.getIdPelicula().toString().equals(id)).findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<Pelicula> event) {
        Pelicula ps = (Pelicula) event.getObject();
        fc.addMessage(null,new FacesMessage(" se ha escogido la pelicula:"+ps.getNombre()));
    }
    @Override
    public  void inicioRegistros(){
        this.modelo=new LazyDataModelBuilder<Pelicula>(
                (Void) -> peliculaBean.countAllPeliculas(),
                this::getIdByObject,
                this::getObjectById,
                (init,max,campo,orden)->peliculaBean.getAllPeliculas(init,max,campo,orden)
        ).getModelo();
    }
}
