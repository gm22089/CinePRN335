package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SucursalBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sucursal;

import java.io.Serializable;
import java.util.List;
@Named("frmSala")
@ViewScoped
public class frmSala implements Serializable {

    @Inject
    private SalaBean salaBean;

    @Inject
    private SucursalBean sucursalBean;

    @Inject
    private FacesContext facesContext;

    private ESTADO_CRUD estado;
    private List<Sala> registros;
    private Sala registro;
    private List<Sucursal> listaSucursales;

    @PostConstruct
    public void init() {
        try {
            registros = salaBean.findRange(0, Integer.MAX_VALUE);
            listaSucursales = sucursalBean.findAll();
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al cargar datos", e.getMessage()));
            e.printStackTrace();
        }
    }

    public void btnSeleccionarRegistroHandler(final Integer idSala) {
        this.estado = ESTADO_CRUD.MODIFICAR;
        this.registro = registros.stream()
                .filter(r -> idSala.equals(r.getIdSala()))
                .findFirst()
                .orElse(null);

        if (this.registro == null) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Registro no encontrado", "El registro seleccionado no existe"));
        }
    }

    public void btnCancelarHandler(ActionEvent actionEvent) {
        this.registro = null;
        this.estado = ESTADO_CRUD.NINGUNO;
    }

    public void btnNuevoHandler(ActionEvent actionEvent) {
        this.registro = new Sala();
        this.registro.setActivo(true);
        this.estado = ESTADO_CRUD.CREAR;
    }

    public void btnGuardarHandler(ActionEvent actionEvent) {
        if (registro == null) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "No hay registro seleccionado", "Por favor, seleccione o cree un registro"));
            return;
        }

        try {
            if (estado == ESTADO_CRUD.CREAR) {
                salaBean.create(registro);
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro guardado con éxito", "La sala se ha creado correctamente"));
            } else if (estado == ESTADO_CRUD.MODIFICAR) {
                salaBean.update(registro);
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro modificado con éxito", "La sala se ha modificado correctamente"));
            }
            registros = salaBean.findRange(0, Integer.MAX_VALUE);
            this.registro = null;
            this.estado = ESTADO_CRUD.NINGUNO;
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al guardar el registro", e.getMessage()));
            e.printStackTrace();
        }
    }

    public void btnEliminarHandler(ActionEvent actionEvent) {
        try {
            salaBean.delete(registro);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Registro eliminado con éxito", "La sala ha sido eliminada correctamente"));
            registros = salaBean.findRange(0, Integer.MAX_VALUE);
            this.registro = null;
            this.estado = ESTADO_CRUD.NINGUNO;
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al eliminar el registro", e.getMessage()));
            e.printStackTrace();
        }
    }

    public Integer getIdSucursalSeleccionado() {
        return registro != null && registro.getIdSucursal() != null ? registro.getIdSucursal().getIdSucursal() : null;
    }

    public void setIdSucursalSeleccionado(final Integer idSucursal) {
        if (registro != null && idSucursal != null) {
            registro.setIdSucursal(
                    listaSucursales.stream()
                            .filter(s -> s.getIdSucursal().equals(idSucursal))
                            .findFirst()
                            .orElse(null)
            );
        }
    }

    public String paginaNombre(){
        return "Sala";
    }

    // Getters y Setters
    public ESTADO_CRUD getEstado() { return estado; }
    public void setEstado(ESTADO_CRUD estado) { this.estado = estado; }
    public List<Sala> getRegistros() { return registros; }
    public void setRegistros(List<Sala> registros) { this.registros = registros; }
    public Sala getRegistro() { return registro; }
    public void setRegistro(Sala registro) { this.registro = registro; }
    public List<Sucursal> getListaSucursales() { return listaSucursales; }
    public void setListaSucursales(List<Sucursal> listaSucursales) { this.listaSucursales = listaSucursales; }
}
