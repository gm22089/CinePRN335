package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoPagoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPago;

import java.io.Serializable;
import java.util.logging.Logger;

@Named
@ViewScoped
public class frmTipoPago extends AbstractFrm<TipoPago> implements Serializable {
    @Override
    public String paginaNombre() {
        return "Tipo Pago";
    }

    @Inject
    TipoPagoBean tpBean;
    @Inject
    FacesContext fc;

    @Override
    public void instanciarRegistro() {
        this.registro = new TipoPago();
        registro.setActivo(true);
    }

    String Titulo = "TipoPago";

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<TipoPago> getAbstractDataPersist() {
        return tpBean;
    }


    @Override
    public String getIdByObject(TipoPago object) {
        if (object.getIdTipoPago() != null) {
            return object.getIdTipoPago().toString();
        }
        return null;
    }

    @Override
    public TipoPago getObjectById(String id) {
        if (id != null & modelo != null & modelo.getWrappedData() != null) {
            return modelo.getWrappedData().stream().
                    filter(r -> id.equals(r.getIdTipoPago().toString())).findFirst().
                    orElseGet(() -> {
                        Logger.getLogger("no se ha encontrado el objeto");
                        return null;
                    });
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<TipoPago> event) {
        TipoPago filaSelelcted = event.getObject();
        FacesMessage mensaje = new FacesMessage("Tipo de pago selecionado co exito");
        fc.addMessage(null, mensaje);
        this.registro = filaSelelcted;
        this.estado = ESTADO_CRUD.MODIFICAR;

    }

    public String getTitulo() {
        return Titulo;
    }
}
