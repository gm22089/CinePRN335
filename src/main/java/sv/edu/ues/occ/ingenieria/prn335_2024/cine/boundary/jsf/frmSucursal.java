package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SucursalBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sucursal;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class frmSucursal extends AbstractFrm<Sucursal> implements Serializable {

    @Override
    public String paginaNombre() {
        return "Sucursal";
    }

    @Inject
    SucursalBean scBean;
    @Inject
    FacesContext fc;

    @Override
    public void instanciarRegistro() {
        this.registro=new Sucursal();
    }

    String Titulo = "Sucursal";


    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<Sucursal> getAbstractDataPersist() {
        return scBean;
    }


    @Override
    public String getIdByObject(Sucursal object) {
        if (object.getIdSucursal()!=null){
            return object.getIdSucursal().toString();
        }
        return null;
    }

    @Override
    public Sucursal getObjectById(String id) {
        if (id!=null && modelo!=null & modelo.getWrappedData()!=null){
            return modelo.getWrappedData().stream().
                    filter(r->id.equals(r.getIdSucursal().toString())).findFirst().
                    orElseGet(()->{
                        Logger.getLogger(getClass().getName()).log(Level.INFO,"Objeto no encontradoo");
                        return null;
                    });
        }
        return null;
    }


    @Override
    public void selecionarFila(SelectEvent<Sucursal> event) {
        Sucursal filaSelelcted = (Sucursal) event.getObject();
        FacesMessage mensaje=new FacesMessage("Sucursal selecionada ", registro.getNombre());
        fc.addMessage(null, mensaje);
        this.registro = filaSelelcted;
        this.estado=ESTADO_CRUD.MODIFICAR;

    }




    public String getTitulo() {
        return Titulo;
    }




}