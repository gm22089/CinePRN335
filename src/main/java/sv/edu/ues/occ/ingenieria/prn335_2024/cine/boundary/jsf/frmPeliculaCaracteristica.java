package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoPeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.PeliculaCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPelicula;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Named
@Dependent
public class frmPeliculaCaracteristica extends AbstractFrm<PeliculaCaracteristica> implements Serializable {
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
    Integer idTipoPeliculaSelecionada;
    String expresionTipoPelicula;


    TipoPelicula tipoPeliculaSelecionada;
    Pelicula peliculaSelecionada;

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


    public void validarValor(FacesContext fc, UIComponent component, Object value){
        String inputText = (String) value;
        System.out.println(expresionTipoPelicula);
        if (inputText != null && !inputText.matches(expresionTipoPelicula) && !expresionTipoPelicula.equals(".")) {
            FacesMessage msg = new FacesMessage("El texto no es válido.", "El texto debe ser uno de los valores permitidos: "+expresionTipoPelicula);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }


    public String obtenerSugerenciaFormato() {
        if (registro != null && registro.getIdTipoPelicula() != null) {
            TipoPelicula tipoSeleccionado = registro.getIdTipoPelicula();
            String sugerencia = "";

            // Obtener el comentario del tipo de película si existe
            if (tipoSeleccionado.getComentarios() != null && !tipoSeleccionado.getComentarios().isEmpty()) {
                sugerencia = tipoSeleccionado.getComentarios();
            } else {
                // Si no hay comentario, construir una sugerencia basada en la expresión regular
                String expresion = tipoSeleccionado.getExpresionRegular();
                if (expresion != null && !expresion.isEmpty()) {
                    sugerencia = "Formato requerido para " + tipoSeleccionado.getNombre() + ": ";

                    // Ejemplos comunes de expresiones regulares y sus descripciones
                    if (expresion.equals("[A-Z]")) {
                        sugerencia += "Una letra mayúscula";
                    } else if (expresion.equals("[0-9]+")) {
                        sugerencia += "Solo números";
                    } else if (expresion.equals("[A-Za-z]+")) {
                        sugerencia += "Solo letras";
                    } else if (expresion.equals("\\d{4}")) {
                        sugerencia += "4 dígitos (ejemplo: 2024)";
                    } else if (expresion.equals("^[A-Z]{1,5}$")) {
                        sugerencia += "Entre 1 y 5 letras mayúsculas";
                    } else {
                        // Si no coincide con ningún patrón común, mostrar la expresión regular
                        sugerencia += expresion;
                    }
                }
            }
            return sugerencia;
        }
        return "Seleccione un tipo de película";
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
            return pcBean.countPeliculaCarracteristica(idPelicula);
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage() +"jajaj",e);
        }
        return 0;
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
    public void selecionarFila(SelectEvent<PeliculaCaracteristica> event) {

        FacesMessage mensaje=new FacesMessage("pelicula selecionada ", registro.getValor());
        fc.addMessage(null,mensaje);
        this.estado=ESTADO_CRUD.MODIFICAR;

        if (registro!=null){
            expresionTipoPelicula=registro.getIdTipoPelicula().getExpresionRegular();
        }
    }

    public Integer getIdTipoPeliculaSelecionada() {
        return idTipoPeliculaSelecionada;
    }

    public void setIdTipoPeliculaSelecionada(Integer idTipoPeliculaSelecionada) {
        this.idTipoPeliculaSelecionada = idTipoPeliculaSelecionada;
    }

    public void enviarMensaje(String mensaje, String detalle, FacesMessage.Severity level) {
        FacesMessage ms=new FacesMessage(level,detalle,mensaje);
        fc.addMessage(null,ms);
    }

    public void SelecionarTipoPelicula(){
        TipoPelicula tp =tipoPeliculaList.stream().filter(p->p.getIdTipoPelicula().equals(idTipoPeliculaSelecionada)).findFirst().orElse(null);

        if (tp==null && peliculaSelecionada!=null){
            System.out.println("no se encontro tipo pelicula");
            return;
        }
        System.out.println("tipo pelicula: "+tp.getNombre());
        System.out.println(peliculaSelecionada.getNombre());
        tipoPeliculaSelecionada=tp;
        expresionTipoPelicula=tp.getExpresionRegular();

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

    public Pelicula getPeliculaSelecionada() {
        return peliculaSelecionada;
    }

    public void setPeliculaSelecionada(Pelicula peliculaSelecionada) {
        this.peliculaSelecionada = peliculaSelecionada;
    }

    public TipoPelicula getTipoPeliculaSelecionada() {
        return tipoPeliculaSelecionada;
    }

    public void setTipoPeliculaSelecionada(TipoPelicula tipoPeliculaSelecionada) {
        this.tipoPeliculaSelecionada = tipoPeliculaSelecionada;
    }

    public String getExpresionTipoPelicula() {
        return expresionTipoPelicula;
    }

    public void setExpresionTipoPelicula(String expresionTipoPelicula) {
        this.expresionTipoPelicula = expresionTipoPelicula;
    }

    @Override
    public void btnGuardarHandler(ActionEvent e) {
        if (tipoPeliculaSelecionada==null){
            tipoPeliculaSelecionada= tipoPeliculaList.getFirst();
        }
        PeliculaCaracteristica pc=new PeliculaCaracteristica();

        pc.setIdTipoPelicula(tipoPeliculaSelecionada);
        pc.setIdPelicula(peliculaSelecionada);
        pc.setValor(registro.getValor());

        registro=pc;
        super.btnGuardarHandler(e);
    }
}