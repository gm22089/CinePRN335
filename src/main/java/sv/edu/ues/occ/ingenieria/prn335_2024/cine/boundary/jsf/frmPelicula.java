package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;

import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

@ViewScoped
@Named
public class frmPelicula extends AbstractFrm<Pelicula> implements Serializable {
    @Inject
    PeliculaBean pBean;
    @Inject
    FacesContext fc;

    @Inject
    frmPeliculaCaracteristica frmPeliculaCarractreistica;


    @Override
    public void instanciarRegistro() {
        this.registro=new Pelicula();
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<Pelicula> getAbstractDataPersist() {
        return pBean;
    }


    @Override
    public String getIdByObject(Pelicula object) {
        if (object!=null){
            return object.getIdPelicula().toString();
        }
        return null;
    }

    @Override
    public Pelicula getObjectById(String id) {
        if (id!=null && modelo!=null & modelo.getWrappedData()!=null){
            return modelo.getWrappedData().stream().
                    filter(r->id.equals(r.getIdPelicula().toString())).findFirst().
                    orElseGet(()->{
                        Logger.getLogger(getClass().getName()).log(Level.INFO,"Objeto no encontradoo");
                        return null;
                    });
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<Pelicula> event) {
        frmPeliculaCarractreistica.estado=ESTADO_CRUD.NINGUNO;
        FacesMessage mensaje=new FacesMessage("pelicula selecionada ", registro.getNombre());
        fc.addMessage(null,mensaje);
        this.estado=ESTADO_CRUD.MODIFICAR;
        this.frmPeliculaCarractreistica.idPelicula=registro.getIdPelicula();
    }

    @Override
    public String paginaNombre() {
        return "Pelicula";
    }
    public void cambiarTab(TabChangeEvent event){

        if(event.getTab().getTitle().equals("Caracteristicas")){
            System.out.println("enviando");
            frmPeliculaCarractreistica.setIdPelicula(registro.getIdPelicula());
            frmPeliculaCarractreistica.inicioRegistros();
            frmPeliculaCarractreistica.setPeliculaSelecionada(registro);
        }
    }


    public frmPeliculaCaracteristica getFrmPeliculaCarractreistica() {
        return frmPeliculaCarractreistica;
    }

    public void setFrmPeliculaCarractreistica(frmPeliculaCaracteristica frmPeliculaCarractreistica) {
        this.frmPeliculaCarractreistica = frmPeliculaCarractreistica;
    }


    @Override
    public void btnCancelarHandler(ActionEvent actionEvent) {
        frmPeliculaCarractreistica.estado=ESTADO_CRUD.NINGUNO;
        frmPeliculaCarractreistica.registro=null;
        super.btnCancelarHandler(actionEvent);
    }
}