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
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.control.SalaCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.SalaCaracteristica;

import java.io.Serializable;
import java.util.List;

@Named("frmSalaCaracteristica")
@ViewScoped
public class frmSalaCaracteristica implements Serializable {

    @Resource(name = "jdbc/pgdb")
    private DataSource dataSource;

    @Inject
    private SalaCaracteristicaBean salaCaracteristicaBean;

    @Inject
    private FacesContext facesContext;

    private ESTADO_CRUD estado;
    private List<SalaCaracteristica> registros;
    private SalaCaracteristica registro;

    @PostConstruct
    public void init() {
        estado = ESTADO_CRUD.NINGUNO;
        try {
            registros = salaCaracteristicaBean.findRange(0, 1000000); // Se puede implementar paginación si es necesario
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al cargar los registros", "No se pudieron cargar los registros de Sala Característica"));
            e.printStackTrace();
        }
    }

    // Selecciona un registro para edición
    public void btnSeleccionarRegistroHandler(final Integer idSalaCaracteristica) {
        this.estado = ESTADO_CRUD.MODIFICAR;
        if (idSalaCaracteristica != null) {
            this.registro = this.registros.stream()
                    .filter(r -> idSalaCaracteristica.equals(r.getIdSalaCaracteristica()))
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
        this.registro = new SalaCaracteristica();
        this.estado = ESTADO_CRUD.CREAR;
    }

    // Obtiene los registros para mostrar en la tabla
    public List<SalaCaracteristica> getRegistros() {
        return registros;
    }

    public void setRegistros(List<SalaCaracteristica> registros) {
        this.registros = registros;
    }

    public Integer getSeleccionado() {
        if (registro != null) {
            return Math.toIntExact(registro.getIdSalaCaracteristica());
        }
        return null;
    }


    // Establece el registro seleccionado y cambia el estado a MODIFICAR
    public void setSelecionado(Integer selecionado) {
        this.registro = this.registros.stream()
                .filter(r -> r.getIdSalaCaracteristica().equals(selecionado))
                .findFirst().orElse(null);
        if (this.registro != null) {
            this.estado = ESTADO_CRUD.MODIFICAR;
        }
    }

    // Obtiene el registro actual
    public SalaCaracteristica getRegistro() {
        return registro;
    }

    public void setRegistro(SalaCaracteristica registro) {
        this.registro = registro;
    }

    // Guarda un nuevo registro o modifica el existente
    public void btnGuardarHandler(ActionEvent actionEvent) {
        FacesMessage mensaje;
        try {
            if (estado == ESTADO_CRUD.CREAR) {
                salaCaracteristicaBean.create(registro);  // Crear un nuevo registro
                mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro guardado con éxito", "La Sala Característica se ha creado correctamente");
            } else if (estado == ESTADO_CRUD.MODIFICAR) {
                salaCaracteristicaBean.update(registro);  // Modificar el registro existente
                mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro modificado con éxito", "La Sala Característica se ha modificado correctamente");
            } else {
                return;
            }
            facesContext.addMessage(null, mensaje);
            this.registro = null;
            this.registros = salaCaracteristicaBean.findRange(0, 1000000);  // Actualiza la lista de registros
            this.estado = ESTADO_CRUD.NINGUNO;
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al guardar el registro", "No se pudo guardar el registro"));
            e.printStackTrace();
        }
    }

    // Elimina un registro
    public void btnEliminarHandler(ActionEvent actionEvent) {
        FacesMessage mensaje;
        try {
            salaCaracteristicaBean.delete(registro);
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Registro eliminado con éxito", "La Sala Característica ha sido eliminada correctamente");
            facesContext.addMessage(null, mensaje);
            this.registro = null;
            this.estado = ESTADO_CRUD.NINGUNO;
            this.registros = salaCaracteristicaBean.findRange(0, 1000000); // Puede ser optimizado con paginación
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