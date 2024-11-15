package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import javax.sql.DataSource;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoPagoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPago;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named("frmTipoPago")
@ViewScoped
public class frmTipoPago implements Serializable {

    @Resource(name = "jdbc/pgdb")
    private DataSource dataSource;

    @Inject
    private TipoPagoBean tpBean;


    @Inject
    private FacesContext facesContext;

    private ESTADO_CRUD estado;
    private List<TipoPago> registros;
    private TipoPago registro;

    public List<TipoPago> getRegistros() {
        return registros;
    }

    public void setRegistro(TipoPago registro) {
        this.registro = registro;
    }

    @PostConstruct
    public void init() {
        estado = ESTADO_CRUD.NINGUNO;
        try {
            registros = tpBean.findRange(0, 1000000);
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al cargar los registros", "No se pudieron cargar los registros de TipoPago"));
            e.printStackTrace();
        }
    }

    public void btnSeleccionarRegistroHandler(final Integer idTipoPago) {
        this.estado = ESTADO_CRUD.MODIFICAR;
        if (idTipoPago != null) {
            this.registro = this.registros.stream()
                    .filter(r -> idTipoPago.equals(r.getIdTipoPago()))
                    .findFirst().orElse(null);
            if (this.registro == null) {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Registro no encontrado", "El registro seleccionado no existe"));
            }
        }
    }

    public void btnCancelarHandler(ActionEvent actionEvent) {
        this.registro = null;
        this.estado = ESTADO_CRUD.NINGUNO;
    }

    public void btnNuevoHandler(ActionEvent actionEvent) {
        this.registro = new TipoPago();
        this.registro.setActivo(true);
        this.estado = ESTADO_CRUD.CREAR;
    }

    public void btnGuardarHandler(ActionEvent actionEvent) {
        FacesMessage mensaje;
        try {
            if (estado == ESTADO_CRUD.CREAR) {
                tpBean.create(registro); // Deja que la base de datos maneje el ID
                mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro guardado con éxito", "El tipo de pago se ha creado correctamente");
            } else if (estado == ESTADO_CRUD.MODIFICAR) {
                tpBean.update(registro); // Actualizar el registro
                mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro modificado con éxito", "El tipo de pago se ha modificado correctamente");
            } else {
                return;
            }
            facesContext.addMessage(null, mensaje);
            this.registro = null;
            this.registros = tpBean.findRange(0, 1000000); // Recargar los registros
            this.estado = ESTADO_CRUD.NINGUNO;
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al guardar el registro", "No se pudo guardar el registro"));
            e.printStackTrace();
        }
    }

    public void btnEliminarHandler(ActionEvent actionEvent) {
        FacesMessage mensaje;
        try {
            tpBean.delete(registro); // Eliminar el registro
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Registro eliminado con éxito", "El tipo de pago ha sido eliminado correctamente");
            facesContext.addMessage(null, mensaje);
            this.registro = null;
            this.estado = ESTADO_CRUD.NINGUNO;
            this.registros = tpBean.findRange(0, 1000000); // Recargar los registros
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al eliminar", "Ocurrió un error al eliminar el registro"));
            e.printStackTrace();
        }
    }

    public ESTADO_CRUD getEstado() {
        return estado;
    }

    public TipoPago getRegistro() {
        return registro;
    }

}
