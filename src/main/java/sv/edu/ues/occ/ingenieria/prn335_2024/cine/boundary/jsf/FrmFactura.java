package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.FacturaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Factura;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmFactura extends AbstractFrm<Factura> implements Serializable {
    @Override
    public String paginaNombre() {
        return "";
    }

    @Inject
    FacturaBean fBean;
    @Inject
    FacesContext fc;

    @Override
    public void instanciarRegistro() {
        this.registro= new Factura();
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<Factura> getAbstractDataPersist() {
        return fBean;
    }



    @Override
    public String getIdByObject(Factura object) {
        return "";
    }

    @Override
    public Factura getObjectById(String id) {
        if (id!=null && modelo!=null && modelo.getWrappedData()!=null) {
            return (Factura) modelo.getWrappedData().stream().filter(r->id.equals(r.getIdFactura().toString())).findFirst().orElseGet(()-> {
                        Logger.getLogger(getClass().getName()).log(Level.INFO, "no se ha encontrado el objeto mediante le id");
                        return null;
                    }
            );

        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<Factura> event) {
        Factura facturaSelected = (Factura) event.getObject();
        FacesMessage mensaje=new FacesMessage("se selecionado la factura n°",facturaSelected.getIdFactura().toString());
        fc.addMessage(null, mensaje);
        this.estado=ESTADO_CRUD.MODIFICAR;

    }
}
