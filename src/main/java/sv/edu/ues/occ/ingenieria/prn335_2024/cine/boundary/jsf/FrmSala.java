package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.*;
import org.w3c.dom.events.Event;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ProgramacionBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SucursalBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.utils.TreeNodeBuilder;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.*;

import jakarta.faces.event.ValueChangeEvent;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Named
@ViewScoped
public class FrmSala extends AbstractFrm<Sala> implements Serializable {

    @Inject
    SalaBean sbean;
    @Inject
    FacesContext fc;
    @Inject
    SucursalBean suBean;
    @Inject
    ProgramacionBean programacionBean;
    private TreeNode root;
    private TreeNode selectedNode;
    Sucursal sucursalSelected;
    List<Sucursal> sucursales;
    List<Programacion> programaciones;
    Date fecha;

    LazyDataModel modeloPeliculas;

    //registro
    private int sucursalId;

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
        this.registro = new Sala();
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<Sala> getAbstractDataPersist() {
        return sbean;
    }


    @Override
    public String getIdByObject(Sala object) {
        if (object.getIdSala() != null) {
            return object.getIdSala().toString();
        }
        return null;
    }

    @Override
    public Sala getObjectById(String id) {
        if (id != null && modelo != null && modelo.getWrappedData() != null) {
            return modelo.getWrappedData().stream()
                    .filter(r -> r.getIdSala().toString().equals(id))
                    .findFirst()
                    .orElseGet(() -> {
                        fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se ha encontrado el objeto", " "));
                        return null;
                    });
        }
        fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ID o modelo nulo", " "));
        return null;
    }

    @Override
    public Pelicula findByIdPelicula(String id) {
        return null;
    }


    @Override
    public void selecionarFila(SelectEvent<Sala> event) {
//        Logger.getLogger(getClass().getName()).log(Level.INFO,"NO");

        Sala selectedSala = event.getObject();
        if (selectedSala != null) {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Selecionado ", selectedSala.getNombre()));
        } else {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "no se ha encontrado ", " "));
        }

        this.registro = selectedSala;
        estado = ESTADO_CRUD.MODIFICAR;
        getAllSucursales();
        getProgramacionBYSala();
    }

    @Override
    public String paginaNombre() {
        return "";
    }


    //otros
    public void getAllSucursales() {
        setSucursales(suBean.findAll());
    }

    public void sucursalSelecionada() {

        Sucursal sucursalSElecionada = getSucursales().get(getSucursalId() - 1);
        if (getSucursalId() > 0) {
            fc.addMessage(null, new FacesMessage(
                    "Seleccionado", "se ha selecionado la sucursal: " + sucursalSElecionada));
            this.registro.setIdSucursal(getSucursales().get(getSucursalId() - 1));
        }
        getProgramacionBYSala();
    }

    public void getProgramacionBYSala() {
        List<Programacion> programacionesBySala = this.programacionBean.getProgramacionByIdSala(registro);

        setProgramaciones(programacionesBySala);
        fc.addMessage(null, new FacesMessage("se han encontrado: ", String.valueOf(programacionesBySala.size())));
    }

    /**
     *    @Override
     *     public void inicioRegistros() {
     *         root = new TreeNodeBuilder<Sucursal, Sala>(suBean.getSucursalAnsSalas()).getRoot();
     *         System.out.println(root.getChildCount());
     *
     *     }
     */

//    public void onNodeSelect(NodeSelectEvent event) {
//        // Verificar si el nodo seleccionado es padre o hijo
//        String nombre=(selectedNode.getData() instanceof Sucursal)?((Sucursal)selectedNode.getData()).getNombre():((Sala)selectedNode.getData()).getNombre();
//        if (selectedNode != null) {
//            nombre==null?"no hay":nombre;
//            fc.addMessage(null,new FacesMessage("El nodo: ",nombre));
//        }
//    }


    // Inicializar el árbol raíz

    //getter y settter

    public void hello() {
        fc.addMessage(null, new FacesMessage("Hola Mundo"));
    }

    public Sucursal getSucursalSelected() {
        return sucursalSelected;
    }

    public void setSucursalSelected(Sucursal sucursalSelected) {
        this.sucursalSelected = sucursalSelected;
    }

    public List<Sucursal> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<Sucursal> sucursales) {
        this.sucursales = sucursales;
    }

    public int getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(int sucursalId) {
        this.sucursalId = sucursalId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<Programacion> getProgramaciones() {
        return programaciones;
    }

    public void setProgramaciones(List<Programacion> programaciones) {
        this.programaciones = programaciones;
    }

    public LazyDataModel getModeloPeliculas() {
        return modeloPeliculas;
    }

    public void setModeloProgramacion(LazyDataModel modeloPeliculas) {
        this.modeloPeliculas = modeloPeliculas;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }
}