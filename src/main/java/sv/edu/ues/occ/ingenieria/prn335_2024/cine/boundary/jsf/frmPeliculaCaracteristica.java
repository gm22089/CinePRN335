package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.PeliculaCaracteristica;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class frmPeliculaCaracteristica implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    PeliculaCaracteristicaBean pcBean;
    LazyDataModel<PeliculaCaracteristica> modelo;
    PeliculaCaracteristica registro;

    Long idPelicula;

    @PostConstruct
    public void inicializar() {
        modelo = new LazyDataModel<PeliculaCaracteristica>() {

            @Override
            public String getRowKey(PeliculaCaracteristica object) {
                if (object != null && object.getIdPeliculaCaracteristica() != null) {
                    return object.getIdPeliculaCaracteristica().toString();
                }
                return null;
            }

            @Override
            public PeliculaCaracteristica getRowData(String rowKey) {
                if (rowKey != null && getWrappedData() != null) {
                    return getWrappedData().stream().filter(r -> rowKey.trim().equals(r.getIdPeliculaCaracteristica().toString())).findFirst().orElse(null);
                }
                return null;
            }

            @Override
            public int count(Map<String, FilterMeta> map) {
                try {
                    if (idPelicula != null) {
                        return pcBean.countPelicula(idPelicula);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }

            @Override
            public List<PeliculaCaracteristica> load(int desde, int max, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                try {
                    if (idPelicula != null && desde >= 0 && max > 0) {
                        return pcBean.findByIdPelicula(idPelicula, desde, max);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return List.of();
            }
        };
    }

    public LazyDataModel<PeliculaCaracteristica> getModelo() {
        return modelo;
    }

    public PeliculaCaracteristica getRegistro() {
        return registro;
    }

    public void setRegistro(PeliculaCaracteristica registro) {
        this.registro = registro;
    }

    public Long getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Long idPelicula) {
        this.idPelicula = idPelicula;
    }

    public void btnNuevoHandler(ActionEvent actionEvent) {
        this.registro = new PeliculaCaracteristica();
        if (idPelicula != null) {
            Pelicula pelicula = new Pelicula();
            pelicula.setIdPelicula(idPelicula); // Asigna el ID de la película
            this.registro.setIdPelicula(pelicula); // Establece la relación
        }
        this.estado = ESTADO_CRUD.CREAR;
    }

    public void btnCancelarHandler(ActionEvent ae) {
        this.registro = null;
    }

    public void btnGuardarHandler(ActionEvent ae) {
        pcBean.create(registro);
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos guardados exitosamente", null);
        facesContext.addMessage(null, mensaje);
    }

    public void btnEliminarHandler(ActionEvent ae) {
        pcBean.delete(registro);
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado exitosamente", null);
        facesContext.addMessage(null, mensaje);
    }

    public void btnModificarHandler(ActionEvent ae) {
        pcBean.update(registro);
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificado exitosamente", null);
        facesContext.addMessage(null, mensaje);
    }
}
