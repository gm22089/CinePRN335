package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.*;

import jakarta.faces.view.ViewScoped;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class frmAsiento extends AbstractFrm<Asiento> implements Serializable {

    @Inject
    AsientoBean asientoBean;
    @Inject
    FacesContext fc;
    @Inject
    frmAsientoCaracteristica frmAsientoCaracteristica;

    Sala idSalaSelecionada;
    List<AsientoCaracteristica> asientoCaracteristicaList;
    Integer idAsientoCaracteristica;

    @Override
    public void instanciarRegistro() {
        registro = new Asiento();

    }

    @PostConstruct
    @Override
    public void inicioRegistros() {
        super.inicioRegistros();
        try {
            this.asientoCaracteristicaList=asientoBean.findAllAsientoCaracteristica();
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<Asiento> getAbstractDataPersist() {
        return asientoBean;
    }

    @Override
    public String getIdByObject(Asiento object) {
        if (object != null) {
            return object.getIdAsiento().toString();
        }
        return "";
    }

    @Override
    public Asiento getObjectById(String id) {
        if (id!=null && modelo!=null && modelo.getWrappedData()!=null) {
            return modelo.getWrappedData().stream()
                    .filter(s -> s.getIdAsiento().toString().equals(id))
                    .findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<Asiento> event) {
        Asiento asientoSelected =  event.getObject();
        if (asientoSelected!=null){
            FacesMessage mensaje=new FacesMessage("Se ha Seleccionado el Asiento°",registro.getNombre());
            fc.addMessage(null, mensaje);
            this.registro=asientoSelected;
            this.estado=ESTADO_CRUD.MODIFICAR;
            frmAsientoCaracteristica.estado=ESTADO_CRUD.MODIFICAR;
            frmAsientoCaracteristica.setIdAsientoSelecionado(registro);
        }else {
            fc.addMessage(null,  new FacesMessage(FacesMessage.SEVERITY_ERROR, "no se ha encontrado ", " "));
        }
    }

    @Override
    public String paginaNombre() {
        return "Asientos";
    }

    //Metodo para seleccionar la sala -------------------------------

    @Override
    public int contar() {
        return asientoBean.countAsientos(idSalaSelecionada);
    }

    @Override
    public List<Asiento> cargar(int first, int max) {
        return  asientoBean.findIdAsientoBySala(idSalaSelecionada, first, max);
    }

    //Getters y Setters -----------------------------------------------------
    public Sala getIdSalaSelecionada() {
        return idSalaSelecionada;
    }

    public void setIdSalaSelecionada(Sala idSala) {
        this.idSalaSelecionada = idSala;
    }

    public List<AsientoCaracteristica> getAsientoCaracteristicaList() {
        return asientoCaracteristicaList;
    }

    public void setAsientoCaracteristicaList(List<AsientoCaracteristica> asientoCaracteristicaList) {
        this.asientoCaracteristicaList = asientoCaracteristicaList;
    }

    public frmAsientoCaracteristica getFrmAsientoCaracteristica() {
        return frmAsientoCaracteristica;
    }

    public void setFrmAsientoCaracteristica(frmAsientoCaracteristica frmAsientoCaracteristica) {
        this.frmAsientoCaracteristica = frmAsientoCaracteristica;
    }

    public Integer getIdAsientoCaracteristica() {
        return idAsientoCaracteristica;
    }

    public void setIdAsientoCaracteristica(Integer idAsientoCaracteristica) {
        this.idAsientoCaracteristica = idAsientoCaracteristica;
    }


    public AsientoBean getAsientoBean() {
        return asientoBean;
    }

    public void setAsientoBean(AsientoBean asientoBean) {
        this.asientoBean = asientoBean;
    }



//Botones -----------------------------------------------------


}
