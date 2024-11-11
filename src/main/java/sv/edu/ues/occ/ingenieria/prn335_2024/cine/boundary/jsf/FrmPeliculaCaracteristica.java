package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.el.MethodExpression;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoPeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.*;

import jakarta.faces.application.FacesMessage;

import jakarta.faces.component.UIInput;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
@Dependent
public class FrmPeliculaCaracteristica extends AbstractFrm<PeliculaCaracteristica> implements Serializable {
    @Inject
    PeliculaCaracteristicaBean pcBean;
    @Inject
    TipoPeliculaBean tpBean;
    @Inject
    PeliculaBean pBean;
    @Inject
    FacesContext fc;

    List<TipoPelicula> tipoPeliculaList;
    Long idPelicula;

    @Override
    public String paginaNombre() {
        return "Pelicula Carracteristica";
    }

    @PostConstruct
    @Override
    public void inicioRegistros() {
        super.inicioRegistros();
        try {
            this.tipoPeliculaList=pcBean.findAllTiposPelicula();
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        }
    }
    public void validarDatos(FacesContext fc , UIComponent components, Object valor) {
//        UIInput input = (UIInput) components;
//        String expresion =registro.getIdTipoPelicula().getExpresionRegular();
//        System.out.println("el valor de es "+registro.getIdTipoPelicula().getExpresionRegular());
//        if (expresion.equals(".") || expresion.isEmpty()) {
//            return;
//        }
//        if (registro != null && registro.getIdPelicula() != null) {
//            String nuevo = valor.toString();
//            Pattern patron = Pattern.compile(this.registro.getIdTipoPelicula().getExpresionRegular());
//            Matcher validator = patron.matcher(nuevo);
//            if (validator.find()) {
//                return;
//            }
//        }
//
//        ((UIInput) components).setValid(false);
//        enviarMensaje("VALOR NO VALIDO",valor.toString(), FacesMessage.SEVERITY_ERROR);
    }

    @Override
    public List<PeliculaCaracteristica> cargar(int first, int max) {
        try {
            if (this.idPelicula!=null && this.pcBean!=null){

                System.out.println(idPelicula);
                return pcBean.findByIdPelicula(this.idPelicula,first,max);
            }
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        }
        return List.of();
    }

    @Override
    public int contar() {
        try {
            return pcBean.countPeliculaCaracteristica(idPelicula);
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage() +"jajaj",e);
        }
        return 0;
    }

    @Override
    public AbstractDataPersist<TipoSala> getDataAccess() {
        return null;
    }

    @Override
    public FacesContext getFacesContext() {
        return null;
    }

    @Override
    public String Rowkey(TipoSala object) {
        return "";
    }

    @Override
    public TipoSala RowData(String rowKey) {
        return null;
    }

    @Override
    public String getIdPorObjeto(TipoSala object) {
        return "";
    }

    @Override
    public TipoProducto getObjetoPorId(String id) {
        return null;
    }

    @Override
    public void instanciarRegistro() {
        PeliculaCaracteristica pc=new PeliculaCaracteristica();

//        if (idPelicula!=null){
//            pc.setIdPelicula(new Pelicula(idPelicula));
//        }
//        if (tipoPeliculaList!=null && tipoPeliculaList.isEmpty()){
//            pc.setIdTipoPelicula(tipoPeliculaList.getLast());
//        }

        registro=pc;

    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<PeliculaCaracteristica> getAbstractDataPersist() {
        return pcBean;
    }

    @Override
    public String getIdByObject(PeliculaCaracteristica object) {
        if (object != null) {
            return object.getIdPeliculaCaracteristica().toString();
        }
        return "";
    }

    @Override
    public PeliculaCaracteristica getObjectById(String id) {
        if (id!=null && modelo.getWrappedData()!=null && modelo!=null) {
            return modelo.getWrappedData().stream().filter(r->id.equals(r.getIdPeliculaCaracteristica().toString())).findFirst().orElse(null);
        };
        return null;
    }

    @Override
    public Pelicula findByIdPelicula(String id) {
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<PeliculaCaracteristica> event) {
        FacesMessage mensaje=new FacesMessage("pelicula selecionada ", registro.getValor());
        fc.addMessage(null,mensaje);
        this.estado=ESTADO_CRUD.MODIFICAR;
    }

    public Integer getIdTipoPeliculaSelecionada(){
        if (registro!=null && registro.getIdPelicula()!=null){
            enviarMensaje("",registro.getIdTipoPelicula().getNombre(),FacesMessage.SEVERITY_INFO);
            return registro.getIdTipoPelicula().getIdTipoPelicula();
        }
        return null;
    }
    public void setIdTipoPeliculaSelecionada(final Integer idTipoPelicula){
        if (registro!=null && registro.getIdPelicula()!=null && !tipoPeliculaList.isEmpty()){
            this.registro.setIdTipoPelicula(this.tipoPeliculaList.stream().filter(r->r.getIdTipoPelicula().equals(idTipoPelicula)).findFirst().orElse(null));
        }
    }

    public void enviarMensaje(String mensaje, String detalle, FacesMessage.Severity level) {
        FacesMessage ms=new FacesMessage(level,detalle,mensaje);
        fc.addMessage(null,ms);
    }

    public List<TipoPelicula> getTipoPeliculaList() {
        return tipoPeliculaList;
    }

    public void setTipoPeliculaList(List<TipoPelicula> tipoPeliculaList) {
        this.tipoPeliculaList = tipoPeliculaList;
    }

    public Long getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Long idPelicula) {
        this.idPelicula = idPelicula;
    }

}