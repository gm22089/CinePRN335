package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.*;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.utils.TreeNodeBuilder;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.*;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class frmReservaCliente extends AbstractFrm<Reserva> implements Serializable {
    private TreeNode root;
    private TreeNode selectedNode;
    @Inject
    SucursalBean sucursalBean;

    @Inject
    ReservaBean reBean;
    @Inject
    SalaBean saBean;
    @Inject
    PeliculaBean peBean;
    @Inject
    ProgramacionBean proBean;
    @Inject
    FacesContext fc;

    //propiedades temporaneas

    Sucursal sucursalSelecionada;
    int sucursalSelecionadaId;
    Sala salaSelecionada;
    Asiento asientoSelecionada;
    Programacion programacionSelecionada;
    TipoReserva tipoReservaSelecionada;

    List<Sucursal> sucursales;
    List<Sala> salasPOrSucursal;
    List<Asiento> AsientosPorSala;
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
        registro=new Reserva();
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<Reserva> getAbstractDataPersist() {
        return reBean;
    }

    @Override
    public String getIdByObject(Reserva object) {
        if (object != null) {
            return object.getIdReserva().toString();
        }
        return "";
    }

    @Override
    public Reserva getObjectById(String id) {
        if (id != null && modelo.getWrappedData()!=null && modelo!=null) {
            return modelo.getWrappedData().stream().filter(r->id.equals(r.getIdReserva().toString())).findFirst().orElse(null);
        };
        return null;
    }

    @Override
    public Pelicula findByIdPelicula(String id) {
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<Reserva> event) {
        registro=event.getObject();
        fc.addMessage(null,new FacesMessage("se ha escogido la reserva #:"+registro.getIdReserva()));
    }

    @Override
    public String paginaNombre() {
        return "";
    }

    /**
     *  @Override
     *     public void inicioRegistros(){
     *         root=new TreeNodeBuilder< Sucursal, Sala >(sucursalBean.getSucursalAnsSalas()).getRoot();
     *         sucursales=sucursalBean.getAllSucursales();
     *
     *     }
     * @param event
     */

    public void onNodeSelect(NodeSelectEvent event) {
        // Verificar si el nodo seleccionado es padre o hijo
        Object selecion=selectedNode.getData();

        String nombre=(selectedNode.getData() instanceof Sucursal)?((Sucursal)selectedNode.getData()).getNombre():((Sala)selectedNode.getData()).getNombre();
        if (selectedNode != null) {
            nombre=nombre==null?"no hay":nombre;
            if (selecion instanceof Sucursal) {
                sucursalSelecionada=(Sucursal) selecion;
                fc.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "has selecionado una sucursal:"+sucursalSelecionada.getNombre(),
                        " debes selecionar una sala para continuar "));
            }else{
                salaSelecionada=(Sala) selecion;
                fc.addMessage(null,new FacesMessage("has seleccionada la sala: ",nombre));
            }
        }

    }

    public void sucursalSelected() {
        sucursalSelecionada=getSucursales().stream().filter(r->r.getIdSucursal().equals(sucursalSelecionadaId)).findFirst().orElse(null);
        fc.addMessage(null,new FacesMessage("se seleccionado una sucursal:"+sucursalSelecionada.getNombre()));
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




    //provisionales


    public TipoReserva getTipoReservaSelecionada() {
        return tipoReservaSelecionada;
    }

    public void setTipoReservaSelecionada(TipoReserva tipoReservaSelecionada) {
        this.tipoReservaSelecionada = tipoReservaSelecionada;
    }

    public Sala getSalaSelecionada() {
        return salaSelecionada;
    }

    public void setSalaSelecionada(Sala salaSelecionada) {
        this.salaSelecionada = salaSelecionada;
    }

    public Asiento getAsientoSelecionada() {
        return asientoSelecionada;
    }

    public void setAsientoSelecionada(Asiento asientoSelecionada) {
        this.asientoSelecionada = asientoSelecionada;
    }

    public Programacion getProgramacionSelecionada() {
        return programacionSelecionada;
    }

    public void setProgramacionSelecionada(Programacion programacionSelecionada) {
        this.programacionSelecionada = programacionSelecionada;
    }

    public Sucursal getSucursalSelecionada() {
        return sucursalSelecionada;
    }

    public void setSucursalSelecionada(Sucursal sucursalSelecionada) {
        this.sucursalSelecionada = sucursalSelecionada;
    }

    public int getSucursalSelecionadaId() {
        return sucursalSelecionadaId;
    }

    public void setSucursalSelecionadaId(int sucursalSelecionadaId) {
        this.sucursalSelecionadaId = sucursalSelecionadaId;
    }

    public List<Sala> getSalasPOrSucursal() {
        return salasPOrSucursal;
    }

    public void setSalasPOrSucursal(List<Sala> salasPOrSucursal) {
        this.salasPOrSucursal = salasPOrSucursal;
    }

    public List<Programacion> getProgramaciones() {
        return programaciones;
    }

    public void setProgramaciones(List<Programacion> programaciones) {
        this.programaciones = programaciones;
    }

    public List<Asiento> getAsientosPorSala() {
        return AsientosPorSala;
    }

    public void setAsientosPorSala(List<Asiento> asientosPorSala) {
        AsientosPorSala = asientosPorSala;
    }

    public List<Sucursal> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<Sucursal> sucursales) {
        this.sucursales = sucursales;
    }
}