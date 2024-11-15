package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("frmPelicula")
@ViewScoped
public class frmPelicula implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(frmPelicula.class.getName());

    @Inject
    private PeliculaBean peliculaBean;

    private List<Pelicula> registros;
    private Pelicula registro;
    private EstadoCrud estado;

    private String busquedaNombre;
    private String busquedaSinopsis;

    public enum EstadoCrud {
        CREAR, MODIFICAR, NINGUNO;
    }

    @PostConstruct
    public void init() {
        estado = EstadoCrud.NINGUNO;
        cargarRegistros();
    }

    private void cargarRegistros() {
        try {
            registros = peliculaBean.findAll();
            if (registros == null || registros.isEmpty()) {
                registros = Collections.emptyList();
                mostrarMensaje("No se encontraron registros de películas.");
            }
        } catch (Exception e) {
            mostrarMensajeError("Error al cargar registros", "No se pudo cargar la lista de películas.");
            logger.log(Level.SEVERE, "Error al cargar registros", e);
        }
    }

    public void seleccionarRegistro(Integer idPelicula) {
        if (idPelicula == null || idPelicula <= 0) {
            mostrarMensajeError("Selección inválida", "El ID de la película es inválido.");
            return;
        }
        registro = peliculaBean.findById(idPelicula);
        if (registro != null) {
            estado = EstadoCrud.MODIFICAR;
        } else {
            mostrarMensajeError("Error", "No se encontró la película seleccionada.");
            estado = EstadoCrud.NINGUNO;
        }
    }

    public void prepararNuevoRegistro() {
        registro = new Pelicula();
        estado = EstadoCrud.CREAR;
    }

    public void guardar() {
        if (registro == null) {
            mostrarMensajeError("Error al guardar", "El registro de la película no puede ser nulo.");
            return;
        }
        try {
            if (estado == EstadoCrud.CREAR) {
                peliculaBean.create(registro);
                mostrarMensaje("Registro creado con éxito.");
            } else if (estado == EstadoCrud.MODIFICAR) {
                peliculaBean.update(registro);
                mostrarMensaje("Registro modificado con éxito.");
            }
            cargarRegistros();
            limpiarFormulario();
        } catch (Exception e) {
            mostrarMensajeError("Error al guardar", "No se pudo guardar el registro.");
            logger.log(Level.SEVERE, "Error al guardar la película", e);
        }
    }

    public void eliminarRegistro(Integer idPelicula) {
        if (idPelicula == null || idPelicula <= 0) {
            mostrarMensajeError("Eliminación inválida", "El ID de la película es inválido.");
            return;
        }
        try {
            peliculaBean.delete(registro);
            mostrarMensaje("Registro eliminado con éxito.");
            cargarRegistros();
        } catch (Exception e) {
            mostrarMensajeError("Error al eliminar", "No se pudo eliminar el registro.");
            logger.log(Level.SEVERE, "Error al eliminar la película", e);
        }
    }

    public void buscarPorNombre() {
        if (busquedaNombre == null || busquedaNombre.trim().isEmpty()) {
            mostrarMensaje("El campo de búsqueda de nombre está vacío. Mostrando todos los registros.");
            cargarRegistros();
            return;
        }
        try {
            registros = peliculaBean.findByName(busquedaNombre);
            if (registros == null || registros.isEmpty()) {
                registros = Collections.emptyList();
                mostrarMensaje("No se encontraron resultados para el nombre proporcionado.");
            }
        } catch (Exception e) {
            mostrarMensajeError("Error en la búsqueda", "No se pudo realizar la búsqueda por nombre.");
            logger.log(Level.SEVERE, "Error al buscar películas por nombre", e);
        }
    }

    public void buscarPorSinopsis() {
        if (busquedaSinopsis == null || busquedaSinopsis.trim().isEmpty()) {
            mostrarMensaje("El campo de búsqueda de sinopsis está vacío. Mostrando todos los registros.");
            cargarRegistros();
            return;
        }
        try {
            registros = peliculaBean.findBySinopsis(busquedaSinopsis);
            if (registros == null || registros.isEmpty()) {
                registros = Collections.emptyList();
                mostrarMensaje("No se encontraron resultados para la sinopsis proporcionada.");
            }
        } catch (Exception e) {
            mostrarMensajeError("Error en la búsqueda", "No se pudo realizar la búsqueda por sinopsis.");
            logger.log(Level.SEVERE, "Error al buscar películas por sinopsis", e);
        }
    }

    private void limpiarFormulario() {
        registro = null;
        estado = EstadoCrud.NINGUNO;
    }

    private void mostrarMensaje(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
    }

    private void mostrarMensajeError(String titulo, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, detalle));
    }

    // Getters y Setters

    public List<Pelicula> getRegistros() {
        return registros;
    }

    public Pelicula getRegistro() {
        return registro;
    }

    public EstadoCrud getEstado() {
        return estado;
    }

    public String getBusquedaNombre() {
        return busquedaNombre;
    }

    public void setBusquedaNombre(String busquedaNombre) {
        this.busquedaNombre = busquedaNombre;
    }

    public String getBusquedaSinopsis() {
        return busquedaSinopsis;
    }

    public void setBusquedaSinopsis(String busquedaSinopsis) {
        this.busquedaSinopsis = busquedaSinopsis;
    }
}
