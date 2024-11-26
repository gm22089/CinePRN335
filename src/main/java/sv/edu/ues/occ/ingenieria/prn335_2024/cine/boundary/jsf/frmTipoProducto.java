package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoProductoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoProducto;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class frmTipoProducto extends AbstractFrm<TipoProducto> implements Serializable {
    @Inject
    TipoProductoBean tpBean;
    @Inject
    FacesContext fc;
    @Override
    public void instanciarRegistro() {
        this.registro = new TipoProducto();
        this.registro.setActivo(true);
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<TipoProducto> getAbstractDataPersist() {
        return tpBean;
    }




    @Override
    public String getIdByObject(TipoProducto object) {
        if (object.getIdTipoProducto() != null) {

            return object.getIdTipoProducto().toString();
        }
        return null;
    }

    @Override
    public TipoProducto getObjectById(String id) {
        fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Selecionado", "Selecionado"));
        if (id != null && this.modelo != null && this.modelo.getWrappedData() != null) {
            return this.modelo.getWrappedData().stream()
                    .filter(r -> r.getIdTipoProducto().toString().equals(id))
                    .findFirst() // Utiliza findFirst para obtener un solo resultado
                    .orElseGet(() -> {
                        Logger.getLogger("no hay objeto");
                        return null;
                    });
        }

        Logger.getLogger("problema para obtener objeto  Tipo producto por id");
        return null;
    }
    @Override
    public void selecionarFila(SelectEvent<TipoProducto> event) {

        TipoProducto selectedProduct = event.getObject();
        FacesMessage msg = new FacesMessage("Producto seleccionado", selectedProduct.getNombre());
        fc.addMessage(null, msg);
        this.registro=selectedProduct;
        estado=ESTADO_CRUD.MODIFICAR;
    }

    @Override
    public String paginaNombre() {
        return "Tipo Producto";
    }
}
