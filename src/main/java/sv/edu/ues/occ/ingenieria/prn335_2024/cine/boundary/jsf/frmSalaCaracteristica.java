package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.PushBuilder;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SalaCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.SalaCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

//import javax.faces.validator.ValidatorException;
import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@Dependent
public class frmSalaCaracteristica extends AbstractFrm<SalaCaracteristica> implements Serializable {


    @Inject
    FacesContext fc;
    @Inject
    SalaCaracteristicaBean salaCaracteristicaBean;

    List<TipoSala> tipoSalaList;
    TipoSala tipoSala;
    Integer idTipoSala;
    Sala idSalaSelecionada;
    String expresionTipoSala;

    @Override
    public void instanciarRegistro() {
        registro = new SalaCaracteristica();
        estado=ESTADO_CRUD.CREAR;
    }

    @PostConstruct
    @Override
    public void inicioRegistros() {
        super.inicioRegistros();
        try {
            this.tipoSalaList=salaCaracteristicaBean.findAllTiposSala();
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<SalaCaracteristica> getAbstractDataPersist() {
        return salaCaracteristicaBean;
    }

    @Override
    public String getIdByObject(SalaCaracteristica object) {
        if (object != null) {
            return object.getIdSalaCaracteristica().toString();
        }
        return null;
    }

    @Override
    public SalaCaracteristica getObjectById(String id) {
        if (id != null && modelo != null && modelo.getWrappedData() != null) {
            return modelo.getWrappedData().stream()
                    .filter(s -> s.getIdSalaCaracteristica().toString().equals(id))
                    .findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<SalaCaracteristica> event) {
        //Seleccionar fila
        if(registro!=null){
            FacesMessage mensaje=new FacesMessage("caracteristica selecionada ", registro.getIdTipoSala().getNombre());
            fc.addMessage(null, mensaje);
            this.estado=ESTADO_CRUD.MODIFICAR;
            tipoSala=registro.getIdTipoSala();
            expresionTipoSala=tipoSala.getExpresionRegular();


            // Eliminar de forma segura
            tipoSalaList.removeIf(e -> Objects.equals(e.getIdTipoSala(), tipoSala.getIdTipoSala()));
            // Agregar al principio de la lista
            tipoSalaList.addFirst(tipoSala);

        }else {
            fc.addMessage(null,  new FacesMessage(FacesMessage.SEVERITY_ERROR, "no se ha encontrado ", " "));
        }


    }
    public void selecionarTipoSala(){
        if (idTipoSala!=null){

            TipoSala ex=tipoSalaList.stream().filter(ts->ts.getIdTipoSala().toString().equals(idTipoSala.toString())).findFirst().orElse(null);
            if (ex!=null) {
                expresionTipoSala=ex.getExpresionRegular();
                return;
            };
        }
        expresionTipoSala=expresionTipoSala==null? ".":getTipoSala().getExpresionRegular();
    }

    public void validarTexto(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String inputText = (String) value;
        System.out.println(expresionTipoSala);
        if (inputText != null && !inputText.matches(expresionTipoSala) && !expresionTipoSala.equals(".")) {
            FacesMessage msg = new FacesMessage("El texto no es válido.", "El texto debe ser uno de los valores permitidos: "+expresionTipoSala);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

//    public List<TipoSala> ordenarList(TipoSala ts ,List<TipoSala> list){
//        Set<TipoSala> set=new HashSet<>();
//    }

    @Override
    public String paginaNombre() {
        return "";
    }

    @Override
    public int contar() {
        return salaCaracteristicaBean.countPeliculaCarracteristica(idSalaSelecionada);
    }

    @Override
    public List<SalaCaracteristica> cargar(int first, int max) {
        return  salaCaracteristicaBean.findByIdSala(idSalaSelecionada,first,max);
    }


    public List<TipoSala> getTipoSalaList() {
        return tipoSalaList;
    }

    public void setTipoSalaList(List<TipoSala> tipoSalaList) {
        this.tipoSalaList = tipoSalaList;
    }

    public Sala getIdSalaSelecionada() {
        return idSalaSelecionada;
    }

    public void setIdSalaSelecionada(Sala idSala) {
        this.idSalaSelecionada = idSala;
    }

    public String getExpresionTipoSala() {
        return expresionTipoSala;
    }

    public void setExpresionTipoSala(String expresionTipoSala) {
        this.expresionTipoSala = expresionTipoSala;
    }

    public Integer getIdTipoSala() {
        return idTipoSala;
    }

    public void setIdTipoSala(Integer idTipoSala) {
        this.idTipoSala = idTipoSala;
    }

    public TipoSala getTipoSala() {
        return tipoSala;
    }

    public void setTipoSala(TipoSala tipoSala) {
        this.tipoSala = tipoSala;
    }

    @Override
    public void btnGuardarHandler(ActionEvent e) {


        try {
            if (!tipoSalaList.isEmpty()) {
                TipoSala tp = tipoSalaList.stream()
                        .filter(ts -> ts.getIdTipoSala().equals(idTipoSala))
                        .findFirst()
                        .orElse(null);
                if (tp != null) {
                    // Asignar idTipoSala en el registro
                    registro.setIdSala(idSalaSelecionada);
                    registro.setIdSala(idSalaSelecionada);
                    registro.setIdTipoSala(tp);

                    super.btnGuardarHandler(e);  // Llamada al método de la clase base
                } else {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "TipoSala no encontrado.");
                }
            } else {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "La lista tipoSalaList está vacía.");
            }
        } catch (Exception ec) {
            // Manejo de excepciones
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ec.getMessage(), ec);
        }


    }

    @Override
    public void btnModificarHandler(ActionEvent ex) {
        try {
            if (!tipoSalaList.isEmpty()) {
                TipoSala tp = tipoSalaList.stream()
                        .filter(ts -> ts.getIdTipoSala().equals(idTipoSala))
                        .findFirst()
                        .orElse(null);
                if (tp != null) {
                    // Asignar idTipoSala en el registro
                    registro.setIdSala(idSalaSelecionada);
                    registro.setIdTipoSala(tp);

                    super.btnModificarHandler(ex);  // Llamada al método de la clase base
                } else {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "TipoSala no encontrado.");
                }
            } else {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "La lista tipoSalaList está vacía.");
            }
        } catch (Exception ec) {
            // Manejo de excepciones
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ec.getMessage(), ec);
        }
    }


}