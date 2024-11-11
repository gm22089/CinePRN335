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
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoSalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named("frmTipoSala")
@ViewScoped
public class frmTipoSala implements Serializable {

    @Resource(name = "jdbc/pgdb")
    private DataSource dataSource;

    @Inject
    private TipoSalaBean tsBean;

    @Inject
    private FacesContext facesContext;

    private ESTADO_CRUD estado;
    private List<TipoSala> registros;
    private TipoSala registro;

    @PostConstruct
    public void init() {
        estado = ESTADO_CRUD.NINGUNO;
        try {
            registros = tsBean.findRange(0, 1000000); // Se puede implementar paginación si es necesario.
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al cargar los registros", "No se pudieron cargar los registros de TipoSala"));
            e.printStackTrace();
        }
    }

    // Selecciona un registro para edición
    public void btnSeleccionarRegistroHandler(final Integer idTipoSala) {
        this.estado = ESTADO_CRUD.MODIFICAR;
        if (idTipoSala != null) {
            this.registro = this.registros.stream()
                    .filter(r -> idTipoSala.equals(r.getIdTipoSala()))
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
        this.registro = new TipoSala();
        this.registro.setActivo(true);
        this.registro.setExpresionRegular(".");
        this.estado = ESTADO_CRUD.CREAR;
    }

    // Obtiene los registros para mostrar en la tabla
    public List<TipoSala> getRegistros() {
        return registros;
    }

    public void setRegistros(List<TipoSala> registros) {
        this.registros = registros;
    }

    public Integer getSeleccionado() {
        return (registro != null) ? registro.getIdTipoSala() : null;
    }

    // Establece el registro seleccionado y cambia el estado a MODIFICAR
    public void setSelecionado(Integer selecionado) {
        this.registro = this.registros.stream()
                .filter(r -> r.getIdTipoSala().equals(selecionado))
                .findFirst().orElse(null);
        if (this.registro != null) {
            this.estado = ESTADO_CRUD.MODIFICAR;
        }
    }

    // Obtiene el registro actual
    public TipoSala getRegistro() {
        return registro;
    }

    public void setRegistro(TipoSala registro) {
        this.registro = registro;
    }

    // Guarda un nuevo registro o modifica el existente
    public void btnGuardarHandler(ActionEvent actionEvent) {
        FacesMessage mensaje;
        try {
            if (estado == ESTADO_CRUD.CREAR) {
                tsBean.create(registro);  // Crear un nuevo registro
                mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro guardado con éxito", "El tipo de sala se ha creado correctamente");
            } else if (estado == ESTADO_CRUD.MODIFICAR) {
                tsBean.update(registro);  // Modificar el registro existente
                mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro modificado con éxito", "El tipo de sala se ha modificado correctamente");
            } else {
                return;
            }
            facesContext.addMessage(null, mensaje);
            this.registro = null;
            this.registros = tsBean.findRange(0, 1000000);  // Actualiza la lista de registros
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
            TipoSala actualizado = tsBean.update(registro);
            if (actualizado != null) {
                mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro modificado con éxito", "El tipo de sala se ha modificado correctamente");
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
            tsBean.delete(registro);
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Registro eliminado con éxito", "El tipo de sala ha sido eliminado correctamente");
            facesContext.addMessage(null, mensaje);
            this.registro = null;
            this.estado = ESTADO_CRUD.NINGUNO;
            this.registros = tsBean.findRange(0, 1000000); // Puede ser optimizado con paginación
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
