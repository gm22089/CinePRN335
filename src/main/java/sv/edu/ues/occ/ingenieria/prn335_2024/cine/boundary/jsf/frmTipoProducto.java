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
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoProductoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoProducto;

import java.io.Serializable;
import java.util.List;

@Named("frmTipoProducto")
@ViewScoped
public class frmTipoProducto implements Serializable {

    private static final int CONSULTA_INICIO = 0;
    private static final int CONSULTA_LIMITE = 1000000;

    @Resource(name = "jdbc/pgdb")
    private DataSource dataSource;

    @Inject
    private TipoProductoBean tpBean;

    @Inject
    private FacesContext facesContext;

    private ESTADO_CRUD estado;
    private List<TipoProducto> registros;
    private TipoProducto registro;

    @PostConstruct
    public void init() {
        estado = ESTADO_CRUD.NINGUNO;
        try {
            registros = tpBean.findRange(CONSULTA_INICIO, CONSULTA_LIMITE);
        } catch (Exception e) {
            agregarMensaje(FacesMessage.SEVERITY_ERROR, "Error al cargar los registros", "No se pudieron cargar los registros de TipoProducto");
            e.printStackTrace();
        }
    }

    // Método para seleccionar un registro y cambiar el estado a 'MODIFICAR'
    public void btnSeleccionarRegistroHandler(final Integer idTipoProducto) {
        this.estado = ESTADO_CRUD.MODIFICAR;  // Cambia el estado a 'MODIFICAR'
        if (idTipoProducto != null) {
            this.registro = this.registros.stream()
                    .filter(r -> idTipoProducto.equals(r.getIdTipoProducto()))
                    .findFirst().orElse(null);
            if (this.registro == null) {
                agregarMensaje(FacesMessage.SEVERITY_WARN, "Registro no encontrado", "El registro seleccionado no existe");
            }
        }
    }

    // Método para cancelar la acción y restablecer el estado
    public void btnCancelarHandler(ActionEvent actionEvent) {
        this.registro = null;
        this.estado = ESTADO_CRUD.NINGUNO;  // Restablecer el estado a 'NINGUNO'
    }

    // Método para crear un nuevo registro y cambiar el estado a 'CREAR'
    public void btnNuevoHandler(ActionEvent actionEvent) {
        this.registro = new TipoProducto();
        this.registro.setActivo(true);
        this.estado = ESTADO_CRUD.CREAR;  // Cambiar el estado a 'CREAR'
    }

    public List<TipoProducto> getRegistros() {
        return registros;
    }

    public void setRegistros(List<TipoProducto> registros) {
        this.registros = registros;
    }

    public Integer getSeleccionado() {
        return (registro != null) ? registro.getIdTipoProducto() : null;
    }

    public void setSeleccionado(Integer seleccionado) {
        btnSeleccionarRegistroHandler(seleccionado);
    }

    public TipoProducto getRegistro() {
        return registro;
    }

    public void setRegistro(TipoProducto registro) {
        this.registro = registro;
    }

    // Método para guardar el registro según el estado (CREAR o MODIFICAR)
    public void btnGuardarHandler(ActionEvent actionEvent) {
        FacesMessage mensaje;
        try {
            if (estado == ESTADO_CRUD.CREAR) {
                tpBean.create(registro);
                mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro guardado con éxito", "El tipo de producto se ha creado correctamente");
            } else if (estado == ESTADO_CRUD.MODIFICAR) {
                tpBean.update(registro);
                mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro modificado con éxito", "El tipo de producto se ha modificado correctamente");
            } else {
                return;
            }
            facesContext.addMessage(null, mensaje);
            limpiarFormulario();
            registros = tpBean.findRange(CONSULTA_INICIO, CONSULTA_LIMITE);
        } catch (Exception e) {
            agregarMensaje(FacesMessage.SEVERITY_ERROR, "Error al guardar el registro", "No se pudo guardar el registro");
            e.printStackTrace();
        }
    }

    public void btnModificarHandler(ActionEvent actionEvent) {
        try {
            TipoProducto actualizado = tpBean.update(registro);
            if (actualizado != null) {
                agregarMensaje(FacesMessage.SEVERITY_INFO, "Registro modificado con éxito", "El tipo de producto se ha modificado correctamente");
            } else {
                agregarMensaje(FacesMessage.SEVERITY_ERROR, "Error al modificar", "No se pudo modificar el registro");
            }
            limpiarFormulario();
        } catch (Exception e) {
            agregarMensaje(FacesMessage.SEVERITY_ERROR, "Error al modificar", "Ocurrió un error al modificar el registro");
            e.printStackTrace();
        }
    }

    public void btnEliminarHandler(ActionEvent actionEvent) {
        try {
            tpBean.delete(registro);
            agregarMensaje(FacesMessage.SEVERITY_INFO, "Registro eliminado con éxito", "El tipo de producto ha sido eliminado correctamente");
            limpiarFormulario();
            registros = tpBean.findRange(CONSULTA_INICIO, CONSULTA_LIMITE);
        } catch (Exception e) {
            agregarMensaje(FacesMessage.SEVERITY_ERROR, "Error al eliminar", "Ocurrió un error al eliminar el registro");
            e.printStackTrace();
        }
    }

    public ESTADO_CRUD getEstado() {
        return estado;
    }

    private void limpiarFormulario() {
        this.registro = null;
        this.estado = ESTADO_CRUD.NINGUNO;
    }

    private void agregarMensaje(FacesMessage.Severity severidad, String resumen, String detalle) {
        facesContext.addMessage(null, new FacesMessage(severidad, resumen, detalle));
    }
}
