package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoReservaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoReserva;

import java.io.Serializable;
import java.util.List;

@Named("FrmTipoReserva")
@ViewScoped
public class FrmTipoReserva implements Serializable {

    @Inject
    private TipoReservaBean trBean;

    private List<TipoReserva> registros;
    private TipoReserva registro;
    private String estado; // "CREAR" o "MODIFICAR"

    @PostConstruct
    public void init() {
        this.registros = trBean.findAll();
    }

    public List<TipoReserva> getRegistros() {
        return registros;
    }

    public void setRegistros(List<TipoReserva> registros) {
        this.registros = registros;
    }

    public TipoReserva getRegistro() {
        return registro;
    }

    public void setRegistro(TipoReserva registro) {
        this.registro = registro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void btnNuevoHandler(ActionEvent event) {
        this.registro = new TipoReserva();
        this.estado = "CREAR";
    }

    public void btnCancelarHandler(ActionEvent event) {
        this.registro = null;
        this.estado = null;
    }

    public void btnGuardarHandler(ActionEvent actionEvent) {
        FacesMessage mensaje;
        try {
            if ("CREAR".equals(estado)) {
                trBean.create(registro);
                mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro guardado con éxito", "El tipo de reserva se ha creado correctamente");
            } else if ("MODIFICAR".equals(estado)) {
                trBean.update(registro);
                mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro modificado con éxito", "El tipo de reserva se ha modificado correctamente");
            } else {
                return;
            }

            FacesContext.getCurrentInstance().addMessage(null, mensaje);
            this.registro = null;
            this.registros = trBean.findAll();
            this.estado = null;

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al guardar el registro", "No se pudo guardar el registro"));
            e.printStackTrace();
        }
    }

    public void btnEliminarHandler(ActionEvent event) {
        FacesMessage mensaje;
        try {
            if (this.registro != null) {
                this.registro.setActivo(false);
                trBean.update(this.registro);
                mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro eliminado con éxito", "El tipo de reserva ha sido marcado como inactivo");
                FacesContext.getCurrentInstance().addMessage(null, mensaje);

                this.registros = trBean.findAll();
                this.registro = null;
                this.estado = null;
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "No hay registro seleccionado", "Por favor seleccione un registro para eliminar"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al eliminar el registro", "Ocurrió un error al eliminar el registro"));
            e.printStackTrace();
        }
    }

    public void selecionarFila(ActionEvent event) {
        if (this.registro != null) {
            this.estado = "MODIFICAR";
        }
    }
}
