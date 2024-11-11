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

@Named
@ViewScoped
public class FrmTipoSalaX implements Serializable {
    @Resource(name = "jdbc/pgdb")
    private DataSource dataSource;


    @Inject
    TipoSalaBean tsBean;

    @Inject
    FacesContext facesContext;

    ESTADO_CRUD estado;

    List<TipoSala> registros;

    @PostConstruct
    public void init() {
        estado = ESTADO_CRUD.NINGUNO;
        registros = tsBean.findRange(0, 1000000);
    }

    TipoSala registro;

    public void btnSeleccionarRegistroHandler(final Integer idTipoSala) {
        this.estado = ESTADO_CRUD.MODIFICAR;
        if (idTipoSala != null) {
            this.registro = this.registros.stream().filter(r -> idTipoSala.equals(r.getIdTipoSala())).findFirst().orElse(null);
            return;
        }
        this.registro = null;
    }

    public void btnCancelarHandler(ActionEvent actionEvent) {
        this.registro = null;
        this.estado = ESTADO_CRUD.NINGUNO;
    }

    public void btnNuevoHandler(ActionEvent actionEvent) {
        this.registro = new TipoSala();
        this.registro.setActivo(true);
        this.registro.setExpresionRegular(".");
        this.estado = ESTADO_CRUD.CREAR;
    }

    public List<TipoSala> getRegistros() {
        return registros;
    }

    public void setRegistros(List<TipoSala> registros) {
        this.registros = registros;
    }

    public Integer getSeleccionado() {
        if (registro == null) {
            return null;
        }
        return registro.getIdTipoSala();
    }

    /**
     * public void setSelecionado(Integer selecionado) {
     * this.registro = this.registros.stream().filter(r -> r.getIdTipoSala() == selecionado).collect(Collectors.toList()).getFirst();
     * if (this.registro != null) {
     * this.estado = ESTADO_CRUD.MODIFICAR;
     * }
     * }
     */

    public TipoSala getRegistro() {
        return registro;
    }

    public void setRegistro(TipoSala registro) {
        this.registro = registro;
    }

    public void btnGuardarHandler(ActionEvent actionEvent) {
        this.tsBean.create(registro);
        this.registro = null;
        this.registros = tsBean.findRange(0, 1000000);
        this.estado = ESTADO_CRUD.NINGUNO;
    }

    public ESTADO_CRUD getEstado() {
        return estado;
    }

    public void btnModificarHandler(ActionEvent actionEvent) {
        FacesMessage mensaje = new FacesMessage();
        TipoSala actualizado = tsBean.update(registro);
        if (actualizado != null) {
            this.registro = null;
            this.estado = ESTADO_CRUD.NINGUNO;
            mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
            mensaje.setSummary("Registro modifcado con exito");
        } else {
            mensaje.setSeverity(FacesMessage.SEVERITY_ERROR);
            mensaje.setSummary("No se pudo guardar el registro, revise sus datos");
        }
        facesContext.addMessage(null, mensaje);
    }

    public void btnEliminarHandler(ActionEvent actionEvent) {
        FacesMessage mensaje = new FacesMessage();
        tsBean.delete(registro);
        mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
        mensaje.setSummary("Registro eliminado con exito");
        facesContext.addMessage(null, mensaje);
        this.registro = null;
        this.estado = ESTADO_CRUD.NINGUNO;
    }
}
