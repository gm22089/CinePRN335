package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoPeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPelicula;

import java.io.Serializable;
import java.util.List;

@Named("frmTipoPelicula")
@ViewScoped
public class frmTipoPelicula implements Serializable {

    @Inject
    private TipoPeliculaBean tpBean;

    @Inject
    private FacesContext facesContext;

    private ESTADO_CRUD estado;
    private List<TipoPelicula> registros;
    private TipoPelicula registro;

    @PostConstruct
    public void init() {
        estado = ESTADO_CRUD.NINGUNO;
        try {
            registros = tpBean.findRange(0, 1000000); // Carga todos los registros
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al cargar los registros", "No se pudieron cargar los registros de TipoPelicula"));
        }
    }

    public void btnNuevoHandler(ActionEvent actionEvent) {
        this.registro = new TipoPelicula();
        this.registro.setActivo(true);  // Inicializa un nuevo registro
        this.estado = ESTADO_CRUD.CREAR;  // Cambia el estado a CREAR
    }

    public void btnSeleccionarRegistroHandler(final Integer idTipoPelicula) {
        this.estado = ESTADO_CRUD.MODIFICAR;
        if (idTipoPelicula != null) {
            this.registro = this.registros.stream()
                    .filter(r -> idTipoPelicula.equals(r.getIdTipoPelicula()))
                    .findFirst().orElse(null);
        }
    }

    public void btnGuardarHandler(ActionEvent actionEvent) {
        try {
            if (estado == ESTADO_CRUD.CREAR) {
                tpBean.create(registro);
            } else if (estado == ESTADO_CRUD.MODIFICAR) {
                tpBean.update(registro);
            }
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Operación exitosa", "Los cambios se han realizado correctamente"));

            // Refrescar la lista de registros
            this.registros = tpBean.findRange(0, 1000000);
            this.estado = ESTADO_CRUD.NINGUNO;

            // Renderizar la tabla para mostrar los datos actualizados
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("frmTabla");

        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al guardar", "No se pudo realizar la operación"));
        }
    }

    public void btnModificarHandler(ActionEvent actionEvent) {
        try {
            if (this.registro != null) {
                tpBean.update(this.registro);
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Modificación exitosa", "El tipo de película ha sido modificado."));
            }

            // Después de modificar, renderizar la tabla con los datos actualizados
            this.registros = tpBean.findRange(0, 1000000);
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("frmTabla");

        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al modificar", "No se pudo modificar el tipo de película"));
        }
    }

    public void btnEliminarHandler(ActionEvent actionEvent) {
        try {
            if (this.registro != null) {
                // Marcar el registro como inactivo
                this.registro.setActivo(false);
                tpBean.update(this.registro);  // Actualiza el registro en la base de datos
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Eliminación lógica exitosa", "El tipo de película ha sido desactivado."));
                this.registros = tpBean.findRange(0, 1000000);  // Refresca la lista
                this.estado = ESTADO_CRUD.NINGUNO;
                this.registro = null;
            }
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al desactivar", "No se pudo desactivar el tipo de película"));
        }
    }

    public void btnCancelarHandler(ActionEvent actionEvent) {
        this.registro = null;
        this.estado = ESTADO_CRUD.NINGUNO;
        // Cuando se cancela, también se renderiza la tabla
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("frmTabla");
    }

    public List<TipoPelicula> getRegistros() {
        return registros;
    }

    public void setRegistros(List<TipoPelicula> registros) {
        this.registros = registros;
    }

    public TipoPelicula getRegistro() {
        return registro;
    }

    public void setRegistro(TipoPelicula registro) {
        this.registro = registro;
    }

    public ESTADO_CRUD getEstado() {
        return estado;
    }
}
