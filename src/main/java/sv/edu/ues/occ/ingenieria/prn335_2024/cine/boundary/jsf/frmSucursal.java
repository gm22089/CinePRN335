package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SucursalBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sucursal;

import java.io.Serializable;
import java.util.List;

@Named("frmSucursal")
@ViewScoped
public class frmSucursal implements Serializable {

    @Inject
    private SucursalBean sucursalBean;

    @Inject
    private FacesContext facesContext;

    private ESTADO_CRUD estado;
    private List<Sucursal> registros;
    private Sucursal registro;

    @PostConstruct
    public void init() {
        estado = ESTADO_CRUD.NINGUNO;
        try {
            registros = sucursalBean.findRange(0, Integer.MAX_VALUE);
            System.out.println("Registros cargados: " + registros.size());
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al cargar los registros", "No se pudieron cargar los registros de Sucursal"));
            e.printStackTrace();
        }
    }

    public void btnSeleccionarRegistroHandler(final Integer idSucursal) {
        this.estado = ESTADO_CRUD.MODIFICAR;
        if (idSucursal != null) {
            this.registro = this.registro = this.registros.stream()
                    .filter(r -> idSucursal.equals(r.getIdSucursal()))
                    .findFirst()
                    .orElse(null);

            if (this.registro == null) {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Registro no encontrado", "El registro seleccionado no existe"));
            }
        }
    }

    public void btnCancelarHandler(ActionEvent actionEvent) {
        this.registro = null;
        this.estado = ESTADO_CRUD.NINGUNO;
    }

    public void btnNuevoHandler(ActionEvent actionEvent) {
        this.registro = new Sucursal();
        this.registro.setActivo(true);
        this.estado = ESTADO_CRUD.CREAR;
    }

    public void btnGuardarHandler(ActionEvent actionEvent) {
        FacesMessage mensaje;
        try {
            if (estado == ESTADO_CRUD.CREAR) {
                sucursalBean.create(registro);
                mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro guardado con éxito", "La sucursal se ha creado correctamente");
            } else if (estado == ESTADO_CRUD.MODIFICAR) {
                sucursalBean.update(registro);
                mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro modificado con éxito", "La sucursal se ha modificado correctamente");
            } else {
                return;
            }
            facesContext.addMessage(null, mensaje);
            this.registro = null;
            this.registros = sucursalBean.findRange(0, 1000000);
            this.estado = ESTADO_CRUD.NINGUNO;
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al guardar el registro", "No se pudo guardar el registro"));
            e.printStackTrace();
        }
    }

    public void btnEliminarHandler(ActionEvent actionEvent) {
        FacesMessage mensaje;
        try {
            sucursalBean.delete(registro);
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Registro eliminado con éxito", "La sucursal ha sido eliminada correctamente");
            facesContext.addMessage(null, mensaje);
            this.registro = null;
            this.estado = ESTADO_CRUD.NINGUNO;
            this.registros = sucursalBean.findRange(0, 1000000);
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al eliminar", "Ocurrió un error al eliminar el registro"));
            e.printStackTrace();
        }
    }

    public ESTADO_CRUD getEstado() {
        return estado;
    }

    public void setEstado(ESTADO_CRUD estado) {
        this.estado = estado;
    }

    public List<Sucursal> getRegistros() {
        return registros;
    }

    public void setRegistros(List<Sucursal> registros) {
        this.registros = registros;
    }

    public Sucursal getRegistro() {
        return registro;
    }

    public void setRegistro(Sucursal registro) {
        this.registro = registro;
    }
}
