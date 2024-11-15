package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.utils.LazyDataModelBuilder;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoProducto;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.io.Serializable;

@Named
@ViewScoped
public class frmPruebaLzy extends AbstractFrm<Pelicula> implements Serializable {
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
    public AbstractDataPersist<TipoSala> getDataAccess() {
        return null;
    }

    @Override
    public FacesContext getFacesContext() {
        return null;
    }

    @Override
    public String Rowkey(TipoSala object) {
        return "";
    }

    @Override
    public TipoSala RowData(String rowKey) {
        return null;
    }

    @Override
    public String getIdPorObjeto(TipoSala object) {
        return "";
    }

    @Override
    public TipoProducto getObjetoPorId(String id) {
        return null;
    }

    @Override
    public Pelicula findByIdPelicula(String id) {
        if (id != null && modelo != null && modelo.getWrappedData() != null) {
            return modelo.getWrappedData().stream()
                    .filter(p -> p.getIdPelicula().toString().equals(id))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }



    @Override
    public void selecionarFila(SelectEvent<Pelicula> event) {
        Pelicula ps = (Pelicula) event.getObject();
        fc.addMessage(null,new FacesMessage(" se ha escogido la pelicula:"+ps.getNombre()));
    }

    @Override
    public String paginaNombre() {
        return "";
    }


}