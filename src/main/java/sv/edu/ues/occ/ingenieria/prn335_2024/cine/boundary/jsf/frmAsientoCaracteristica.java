package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AsientoCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.*;


import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Named
@ViewScoped
public class frmAsientoCaracteristica extends AbstractFrm<AsientoCaracteristica> implements Serializable {

    @Inject
    AsientoCaracteristicaBean asientoCaracteristicaBean;
    @Inject
    FacesContext fc;


    List<TipoAsiento> tipoAsientoslist;
    Integer idTipoAsiento;
    Asiento idAsientoSelecionado;

    @Override
    public void instanciarRegistro() {
        registro = new AsientoCaracteristica();
    }

    @Override
    public void inicioRegistros() {
        super.inicioRegistros();
        try {
            this.tipoAsientoslist=asientoCaracteristicaBean.findAllTiposAsiento();
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<AsientoCaracteristica> getAbstractDataPersist() {
        return asientoCaracteristicaBean;
    }

    @Override
    public String getIdByObject(AsientoCaracteristica object) {
        if (object != null) {
            return object.getIdAsientoCaracteristica().toString();
        }
        return null;
    }

    @Override
    public AsientoCaracteristica getObjectById(String id) {
        if (id != null && modelo != null && modelo.getWrappedData() != null) {
            return modelo.getWrappedData().stream()
                    .filter(s -> s.getIdAsientoCaracteristica().toString().equals(id))
                    .findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<AsientoCaracteristica> event) {
        AsientoCaracteristica filaSelelcted = event.getObject();
        if(filaSelelcted!=null){
            FacesMessage mensaje=new FacesMessage("caracteristica selecionada ", registro.getValor());
            fc.addMessage(null, mensaje);
            this.registro = filaSelelcted;
            this.estado=ESTADO_CRUD.MODIFICAR;

        }else {
            fc.addMessage(null,  new FacesMessage(FacesMessage.SEVERITY_ERROR, "no se ha encontrado ", " "));
        }

    }

    @Override
    public String paginaNombre() {
        return "Asiento Caracteristica";
    }

    //Metodos random y SobreEscritos-----------------------------------------------------

    @Override
    public int contar() {
        return asientoCaracteristicaBean.countByIdAsientoCaracteristica(idAsientoSelecionado);
    }

    @Override
    public List<AsientoCaracteristica> cargar(int first, int max) {
        return asientoCaracteristicaBean.findByIdAsiento(idAsientoSelecionado, first, max);
    }




    //Getters and Setters-------------------------------------------

    public List<TipoAsiento> getTipoAsientoslist() {
        return tipoAsientoslist;
    }

    public void setTipoAsientoslist(List<TipoAsiento> tipoAsientoslist) {
        this.tipoAsientoslist = tipoAsientoslist;
    }

    public Integer getIdTipoAsiento() {
        return idTipoAsiento;
    }

    public void setIdTipoAsiento(Integer idTipoAsiento) {
        this.idTipoAsiento = idTipoAsiento;
    }

    public Asiento getIdAsientoSelecionado() {
        return idAsientoSelecionado;
    }

    public void setIdAsientoSelecionado(Asiento idAsientoSelecionado) {
        this.idAsientoSelecionado = idAsientoSelecionado;

    }

    //Botones -----------------------------------------------------

    @Override
    public void btnGuardarHandler(ActionEvent e) {
        registro.setIdAsiento(idAsientoSelecionado);
        try {
            TipoAsiento idAsientoSelecionado = tipoAsientoslist.stream().filter(ts-> ts.getIdTipoAsiento().
                    toString().equals(String.valueOf(idTipoAsiento))).findFirst().orElse(null);
            registro.setIdTipoAsiento(idAsientoSelecionado);
            super.btnGuardarHandler(e);

        }catch (Exception ec){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ec.getMessage(), ec);
        }

    }

    @Override
    public void btnModificarHandler(ActionEvent ex) {
        registro.setIdAsiento(idAsientoSelecionado);
        try {
            if (!tipoAsientoslist.isEmpty()){
                TipoAsiento idAsientoSelecionado = tipoAsientoslist.stream().filter(ts-> ts.getIdTipoAsiento().
                        toString().equals(String.valueOf(idTipoAsiento))).findFirst().orElse(null);
                registro.setIdTipoAsiento(idAsientoSelecionado);
                super.btnModificarHandler(ex);
            }
        }catch (Exception ec){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ec.getMessage(), ec);
        }
    }
}
