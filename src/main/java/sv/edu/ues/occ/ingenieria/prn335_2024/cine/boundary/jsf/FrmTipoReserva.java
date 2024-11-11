package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoReservaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoProducto;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoReserva;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmTipoReserva extends AbstractFrm<TipoReserva> {
    @Inject
    TipoReservaBean trBean;
    @Inject
    FacesContext fc;

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

        this.registro = new TipoReserva();
        this.registro.setActivo(true);
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<TipoReserva> getAbstractDataPersist() {
        return trBean;
    }



    @Override
    public String getIdByObject(TipoReserva object) {
        if (object.getIdTipoReserva() != null) {
            return object.getIdTipoReserva().toString();
        }
        return null;
    }

    @Override
    public TipoReserva getObjectById(String id) {
        if (id != null && modelo != null && modelo.getWrappedData()!=null) {
            return  modelo.getWrappedData().stream().
                    filter(r -> r.getIdTipoReserva().toString().equals(id)).findFirst().
                    orElseGet(()->{
                        Logger.getLogger("el object no se ha encontrado");
                        return null;
                    });
        }
        return null;
    }

    @Override
    public Pelicula findByIdPelicula(String id) {
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<TipoReserva> event) {
        TipoReserva tipoReservaSelceted = (TipoReserva) event.getObject();
        FacesMessage mensaje=new FacesMessage("Tipo de reserva seleccionada",tipoReservaSelceted.getNombre());
        fc.addMessage(null, mensaje);
        this.estado=ESTADO_CRUD.MODIFICAR;
    }

    @Override
    public String paginaNombre() {
        return "Tipo Reserva";
    }
}