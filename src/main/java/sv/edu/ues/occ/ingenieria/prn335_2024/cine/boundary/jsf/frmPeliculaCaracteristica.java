package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.PeliculaCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPelicula;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("frmPeliculaCaracteristica")
@ViewScoped
public class frmPeliculaCaracteristica implements Serializable {

    @Inject
    private FacesContext facesContext;

    @Inject
    private PeliculaCaracteristicaBean pcBean;

    private List<TipoPelicula> tipoPeliculaList;

    private ESTADO_CRUD estado;
    private List<PeliculaCaracteristica> registros;
    private PeliculaCaracteristica registro;
    private Long idPelicula; // Filtro para asociar las características a una película específica
    private String regexPattern;

    private static final int PAGE_SIZE = 1000;

    /**
     *     @PostConstruct
     *     public void init() {
     *         estado = ESTADO_CRUD.NINGUNO;
     *         regexPattern = "^[a-zA-Z0-9_ ]+$"; // Expresión regular para la validación
     *         cargarRegistros(idPelicula);
     *         tipoPeliculaList = pcBean.findAllTipoPelicula(); // Cargar la lista de tipos de película
     *     }
     *
     */

    private void cargarRegistros(Long idPelicula) {
        try {
            if (idPelicula != null) {
                registros = pcBean.findByIdPelicula(idPelicula, 0, PAGE_SIZE);
            } else {
                registros = pcBean.findAll();
            }
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al cargar registros", "No se pudieron cargar los registros de características"));
            logError(e);
        }
    }

    /**
     *
     *   public void btnSeleccionarRegistroHandler(TipoPelicula tipoPelicula) {
     *         try {
     *             this.registro = new PeliculaCaracteristica();
     *             this.registro.setTipoPelicula(tipoPelicula);
     *             registros = pcBean.findByTipoPelicula(tipoPelicula); // Cargar las características asociadas a este tipo
     *
     *             facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
     *                     "Registro seleccionado", "La característica de la película se ha seleccionado correctamente"));
     *         } catch (Exception e) {
     *             facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
     *                     "Error al seleccionar", "Ocurrió un error al seleccionar el registro"));
     *             logError(e);
     *         }
     *     }
     */

    public void btnCancelarHandler(ActionEvent actionEvent) {
        this.registro = null;
        this.estado = ESTADO_CRUD.NINGUNO;
    }

    public void btnNuevoHandler(ActionEvent actionEvent) {
        this.registro = new PeliculaCaracteristica();
        if (idPelicula != null) {
            Pelicula pelicula = new Pelicula();
            pelicula.setIdPelicula(idPelicula);
            this.registro.setPelicula(pelicula);
        }
        this.estado = ESTADO_CRUD.CREAR;
    }

    public void btnGuardarHandler(ActionEvent actionEvent) {
        FacesMessage mensaje;
        try {
            if (estado == ESTADO_CRUD.CREAR) {
                pcBean.create(registro);
                mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro guardado con éxito", "La característica se ha creado correctamente");
            } else if (estado == ESTADO_CRUD.MODIFICAR) {
                pcBean.update(registro);
                mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro modificado con éxito", "La característica se ha modificado correctamente");
            } else {
                return;
            }
            facesContext.addMessage(null, mensaje);
            this.registro = null;
            this.estado = ESTADO_CRUD.NINGUNO;
            cargarRegistros(idPelicula);
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al guardar el registro", "No se pudo guardar la característica"));
            logError(e);
        }
    }

    public void btnEliminarHandler(ActionEvent actionEvent) {
        try {
            if (registro != null && registro.getIdPeliculaCaracteristica() != null) {
                pcBean.delete(registro);
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro eliminado", "La característica se ha eliminado correctamente"));
                this.registro = null;
                cargarRegistros(idPelicula);
            }
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al eliminar el registro", "No se pudo eliminar la característica"));
            logError(e);
        }
    }

    public enum ESTADO_CRUD {
        NINGUNO,
        CREAR,
        MODIFICAR
    }

    private void logError(Exception e) {
        // Implementa un log de errores para depuración
    }
}
