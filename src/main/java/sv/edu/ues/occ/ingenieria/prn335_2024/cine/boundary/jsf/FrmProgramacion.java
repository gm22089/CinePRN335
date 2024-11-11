package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ProgramacionBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Programacion;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoProducto;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class FrmProgramacion extends AbstractFrm<Programacion> implements Serializable {
    @Inject
    ProgramacionBean pBean;

    @Inject FacesContext fc;

    List<Programacion> programaciones;

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
        registro=new Programacion();
        programaciones=pBean.findAll();
        System.out.println(programaciones.size());
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<Programacion> getAbstractDataPersist() {
        return pBean;
    }


    @Override
    public String getIdByObject(Programacion object) {
        if (object.getIdProgramacion()!=null){
            return object.getIdProgramacion().toString();
        }
        return "";
    }

    @Override
    public Programacion getObjectById(String id) {
        fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Programacion encontrado", ""));
        if (id!=null && modelo.getWrappedData()!=null){
            return modelo.getWrappedData().stream().
                    filter(p -> p.getIdProgramacion().toString().equals(id)).findFirst().orElse(null);
        }

        return null;
    }

    @Override
    public Pelicula findByIdPelicula(String id) {
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<Programacion> event) {
        Programacion programacionSelected = (Programacion) event.getObject();
        this.registro= programacionSelected;
        estado =ESTADO_CRUD.MODIFICAR;
        fc.addMessage(null,new FacesMessage("se ha selecionado una programacion"+programacionSelected));
    }

    @Override
    public String paginaNombre() {
        return "";
    }

    public List<Programacion> getProgramaciones() {
        return programaciones;
    }

    public void setProgramaciones(List<Programacion> programaciones) {
        this.programaciones = programaciones;
    }

}