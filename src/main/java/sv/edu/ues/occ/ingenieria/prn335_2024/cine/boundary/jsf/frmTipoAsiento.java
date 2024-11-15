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
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoAsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoAsiento;

import java.io.Serializable;
import java.util.List;

@Named("frmTipoAsiento")
@ViewScoped
public class frmTipoAsiento implements Serializable {

    @Resource(name = "jdbc/pgdb")
    private DataSource dataSource;

    @Inject
    private TipoAsientoBean taBean;

    @Inject
    private FacesContext facesContext;

    private ESTADO_CRUD estado;
    private List<TipoAsiento> registros;
    private TipoAsiento registro;

    @PostConstruct
    public void init() {
        estado = ESTADO_CRUD.NINGUNO;
        try {
            registros = taBean.findRange(0, 1000000); // Se puede implementar paginación si es necesario.
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al cargar los registros", "No se pudieron cargar los registros de TipoAsiento"));
            e.printStackTrace();
        }
    }

    // Selecciona un registro para edición
    public void btnSeleccionarRegistroHandler(final Integer idTipoAsiento) {
        this.estado = ESTADO_CRUD.MODIFICAR;
        if (idTipoAsiento != null) {
            this.registro = this.registros.stream()
                    .filter(r -> idTipoAsiento.equals(r.getIdTipoAsiento()))
                    .findFirst().orElse(null);
            if (this.registro == null) {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Registro no encontrado", "El registro seleccionado no existe"));
            }
        }
    }

    // Cancela la acción actual y limpia el formulario
    public void btnCancelarHandler(ActionEvent actionEvent) {
        this.registro = null;
        this.estado = ESTADO_CRUD.NINGUNO;
    }

    // Prepara el formulario para crear un nuevo registro
    public void btnNuevoHandler(ActionEvent actionEvent) {
        this.registro = new TipoAsiento();
        this.registro.setActivo(true);
        this.registro.setExpresionRegular(".");
        this.estado = ESTADO_CRUD.CREAR;
    }

    // Obtiene los registros para mostrar en la tabla
    public List<TipoAsiento> getRegistros() {
        return registros;
    }

    public void setRegistros(List<TipoAsiento> registros) {
        this.registros = registros;
    }

    public Integer getSeleccionado() {
        return (registro != null) ? registro.getIdTipoAsiento() : null;
    }

    // Establece el registro seleccionado y cambia el estado a MODIFICAR
    public void setSeleccionado(Integer seleccionado) {
        this.registro = this.registros.stream()
                .filter(r -> r.getIdTipoAsiento().equals(seleccionado))
                .findFirst().orElse(null);
        if (this.registro != null) {
            this.estado = ESTADO_CRUD.MODIFICAR;
        }
    }

    // Obtiene el registro actual
    public TipoAsiento getRegistro() {
        return registro;
    }

    public void setRegistro(TipoAsiento registro) {
        this.registro = registro;
    }

    // Guarda un nuevo registro o modifica el existente
    public void btnGuardarHandler(ActionEvent actionEvent) {
        FacesMessage mensaje;
        try {
            if (estado == ESTADO_CRUD.CREAR) {
                taBean.create(registro);  // Crear un nuevo registro
                mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro guardado con éxito", "El tipo de asiento se ha creado correctamente");
            } else if (estado == ESTADO_CRUD.MODIFICAR) {
                taBean.update(registro);  // Modificar el registro existente
                mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro modificado con éxito", "El tipo de asiento se ha modificado correctamente");
            } else {
                return;
            }
            facesContext.addMessage(null, mensaje);
            this.registro = null;
            this.registros = taBean.findRange(0, 1000000);  // Actualiza la lista de registros
            this.estado = ESTADO_CRUD.NINGUNO;
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al guardar el registro", "No se pudo guardar el registro"));
            e.printStackTrace();
        }
    }

    // Modifica un registro existente
    public void btnModificarHandler(ActionEvent actionEvent) {
        FacesMessage mensaje;
        try {
            TipoAsiento actualizado = taBean.update(registro);
            if (actualizado != null) {
                mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro modificado con éxito", "El tipo de asiento se ha modificado correctamente");
            } else {
                mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error al modificar", "No se pudo modificar el registro");
            }
            facesContext.addMessage(null, mensaje);
            this.registro = null;
            this.estado = ESTADO_CRUD.NINGUNO;
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al modificar", "Ocurrió un error al modificar el registro"));
            e.printStackTrace();
        }
    }

    // Elimina un registro
    public void btnEliminarHandler(ActionEvent actionEvent) {
        FacesMessage mensaje;
        try {
            taBean.delete(registro);
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Registro eliminado con éxito", "El tipo de asiento ha sido eliminado correctamente");
            facesContext.addMessage(null, mensaje);
            this.registro = null;
            this.estado = ESTADO_CRUD.NINGUNO;
            this.registros = taBean.findRange(0, 1000000); // Puede ser optimizado con paginación
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al eliminar", "Ocurrió un error al eliminar el registro"));
            e.printStackTrace();
        }
    }

    public ESTADO_CRUD getEstado() {
        return estado;
    }
}