package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;

import java.io.Serializable;
import java.util.List;

@Named("frmPelicula")
@ViewScoped
public class frmPelicula implements Serializable {

    @Inject
    private PeliculaBean pBean;

    @Inject
    private FacesContext facesContext;

    private ESTADO_CRUD estado;
    private List<Pelicula> registros;
    private Pelicula registro;

    @PostConstruct
    public void init() {
        estado = ESTADO_CRUD.NINGUNO;
        try {
            registros = pBean.findRange(0, 1000000);  // Cargar todos los registros de películas
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al cargar los registros", "No se pudieron cargar los registros de Película"));
            e.printStackTrace();
        }
    }

    // Manejo de la selección del registro
    public void btnSeleccionarRegistroHandler(final Integer idPelicula) {
        this.estado = ESTADO_CRUD.MODIFICAR;
        if (idPelicula != null) {
            this.registro = this.registros.stream()
                    .filter(r -> idPelicula.equals(r.getIdPelicula()))
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

    public void btnNuevoHandler(ActionEvent actionEvent) {
        this.registro = new Pelicula();
        // Cambiar la forma en la que se asigna el valor de "activo"
        this.registro.setActivaInterna(true);
        this.estado = ESTADO_CRUD.CREAR;
    }


    // Guarda un nuevo registro o modifica el existente
    public void btnGuardarHandler(ActionEvent actionEvent) {
        FacesMessage mensaje;
        try {
            if (estado == ESTADO_CRUD.CREAR) {
                pBean.create(registro);  // Crear un nuevo registro
                mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro guardado con éxito", "La película se ha creado correctamente");
            } else if (estado == ESTADO_CRUD.MODIFICAR) {
                pBean.update(registro);  // Modificar el registro existente
                mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro modificado con éxito", "La película se ha modificado correctamente");
            } else {
                return;
            }
            facesContext.addMessage(null, mensaje);
            this.registro = null;
            this.registros = pBean.findRange(0, 1000000);  // Actualiza la lista de registros
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
            pBean.delete(registro);  // Eliminar el registro seleccionado
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Registro eliminado con éxito", "La película ha sido eliminada correctamente");
            facesContext.addMessage(null, mensaje);
            this.registro = null;
            this.estado = ESTADO_CRUD.NINGUNO;
            this.registros = pBean.findRange(0, 1000000);  // Actualiza la lista de registros
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al eliminar", "Ocurrió un error al eliminar el registro"));
            e.printStackTrace();
        }
    }

    // Métodos de acceso y manipulación de registros
    public List<Pelicula> getRegistros() {
        return registros;
    }

    public void setRegistros(List<Pelicula> registros) {
        this.registros = registros;
    }

    public Integer getSeleccionado() {
        if (registro != null) {
            return Math.toIntExact(registro.getIdPelicula());
        } else {
            return null;
        }
    }

    public void setSeleccionado(Integer seleccionado) {
        this.registro = this.registros.stream()
                .filter(r -> r.getIdPelicula().equals(seleccionado))
                .findFirst().orElse(null);
        if (this.registro != null) {
            this.estado = ESTADO_CRUD.MODIFICAR;
        }
    }

    public Pelicula getRegistro() {
        return registro;
    }

    public void setRegistro(Pelicula registro) {
        this.registro = registro;
    }

    public ESTADO_CRUD getEstado() {
        return estado;
    }

    public void setEstado(ESTADO_CRUD estado) {
        this.estado = estado;
    }
}
