package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoAsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoAsiento;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoProducto;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.io.Serializable;
import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmTipoAsiento extends AbstractFrm<TipoAsiento> implements Serializable{

    @Inject
    private FacesContext fc;
    @Inject
    private TipoAsientoBean taBean;

    @Override
    public AbstractDataPersist<TipoAsiento> getAbstractDataPersist() {
        return taBean;
    }

    @Override
    public FacesContext getFC() {
        return fc;
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
    public void instanciarRegistro() {
        this.registro = new TipoAsiento();
        this.registro.setActivo(true);
    }

    @Override
    public String getIdByObject(TipoAsiento object) {
        return object != null && object.getIdTipoAsiento() != null ? object.getIdTipoAsiento().toString() : null;
    }

    @Override
    public TipoAsiento getObjectById(String id) {
        if (id != null && this.modelo != null && this.modelo.getWrappedData() != null) {
            return this.modelo.getWrappedData().stream()
                    .filter(t -> t.getIdTipoAsiento().equals(Integer.parseInt(id)))
                    .findFirst()
                    .orElseGet(() -> {
                        Logger.getLogger(getClass().getName()).warning("No se encontró el objeto con ID: " + id);
                        return null;
                    });
        }
        Logger.getLogger(getClass().getName()).warning("Problema para obtener objeto TipoAsiento por ID: " + id);
        return null;
    }

    @Override
    public Pelicula findByIdPelicula(String id) {
        return null;
    }

    public void btnSeleccionarRegistroHandler(Object id) {
        if (id != null) {
            this.registro = this.registros.stream()
                    .filter(t -> t.getIdTipoAsiento().equals(id))
                    .findFirst()
                    .orElse(null);
            this.estado = ESTADO_CRUD.MODIFICAR;
        } else {
            this.registro = null;
        }
    }

    @Override
    public void selecionarFila(SelectEvent<TipoAsiento> event) {
        TipoAsiento selectedAsiento = event.getObject();
        FacesMessage msg = new FacesMessage("Asiento seleccionado", selectedAsiento.getNombre());
        fc.addMessage(null, msg);

        this.registro = selectedAsiento;
        estado = ESTADO_CRUD.MODIFICAR;
    }

    @Override
    public String paginaNombre() {
        return "";
    }
}
