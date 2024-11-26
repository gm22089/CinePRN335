package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;


import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.*;
import org.w3c.dom.events.Event;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.*;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.utils.LazyDataModelBuilder;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.utils.TreeNodeBuilder;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.*;

import jakarta.faces.event.ValueChangeEvent;
import java.io.Serializable;
import java.rmi.registry.Registry;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Named
@ViewScoped
public class frmSala extends AbstractFrm<Sala> implements Serializable {

    @Inject
    SalaBean salaBean;
    @Inject
    SucursalBean sucursalBean;
    @Inject
    ProgramacionBean programacionBean;
    @Inject
    AsientoBean asientoBean;
    @Inject
    frmAsiento frmAsiento;
    @Inject
    SalaCaracteristicaBean SalaCBean;
    @Inject
    frmSalaCaracteristica frmSalaCaracteristica;
    @Inject
    SalaCaracteristicaBean salaCaracteristicaBean;
    @Inject
    FacesContext fc;
    @Inject
    FrmProgramacion  frmProgramacion;


    List<Sucursal> sucursales;
    Sucursal sucursalSelecionada;
    String idSucursalSelecionado;


    List<Programacion> programaciones;
    List<Asiento> asientos;
    private int sucursalId;
    List<TipoSala> tipoSalasDisponibles;
    List<Asiento> asientosDisponibles;
    List<Asiento> asientosSelecionados;
    Date fechaReservaSelecionada;
    Programacion programacionSelecionada;
    String idProgramacion;
    String fechaProgramacion;
    String idAsientoSelecionado;
    String idAsientoELiminado;


    public void cargarSucursales() {
        sucursales = sucursalBean.getAllSucursales();
    }

    public void cambiarTab(TabChangeEvent event){
        System.out.println("cambiando tab");
        try {
            //obtener sucursales
            cargarSucursales();
            cargarDatosIniciales();
            if(event.getTab().getTitle().equals("Caracteristicas")){
                frmSalaCaracteristica.setIdSalaSelecionada(registro);

                frmSalaCaracteristica.selecionarTipoSala();
                System.out.println("se envio datos");
            }
            if(event.getTab().getTitle().equals("programaciones")){
                frmProgramacion.setSalaSelecionada(registro);
                frmProgramacion.inicioRegistros();
                System.out.println("se envio datos para programaciones");
            }
        }catch (Exception e){
            Logger.getLogger(frmSala.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    @Override
    public void instanciarRegistro() {
        this.registro=new Sala();
        this.estado=ESTADO_CRUD.CREAR;
    }

    @Override
    public void inicioRegistros() {
        super.inicioRegistros();
        cargarDatosIniciales();
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<Sala> getAbstractDataPersist() {
        return salaBean;
    }

    @Override
    public String getIdByObject(Sala object) {
        if (object.getIdSala()!=null){
            return object.getIdSala().toString();
        }
        return null;
    }

    @Override
    public Sala getObjectById(String id) {
        if (id!=null && modelo!=null & modelo.getWrappedData()!=null){
            return modelo.getWrappedData().stream().
                    filter(r->id.equals(r.getIdSala().toString())).findFirst().
                    orElseGet(()->{
                        Logger.getLogger(getClass().getName()).log(Level.INFO,"Objeto no encontradoo");
                        return null;
                    });
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<Sala> event) {
        Sala filaSelelcted = event.getObject();
        if(filaSelelcted!=null){
            FacesMessage mensaje=new FacesMessage("Sala selecionada ", registro.getNombre());
            fc.addMessage(null, mensaje);
            this.registro = filaSelelcted;
            this.estado=ESTADO_CRUD.MODIFICAR;
            cargarSucursales();
            frmSalaCaracteristica.estado=ESTADO_CRUD.MODIFICAR;
            frmSalaCaracteristica.setIdSalaSelecionada(registro);
            frmAsiento.estado=ESTADO_CRUD.MODIFICAR;
            frmAsiento.setIdSalaSelecionada(registro);
            frmSalaCaracteristica.idSalaSelecionada=registro;
            cargarDatosIniciales();

        }else {
            fc.addMessage(null,  new FacesMessage(FacesMessage.SEVERITY_ERROR, "no se ha encontrado ", " "));
        }
    }


    public void cargarDatosIniciales(){
        cargarSucursales();
        frmSalaCaracteristica.estado=ESTADO_CRUD.MODIFICAR;
        frmSalaCaracteristica.setIdSalaSelecionada(registro);
        frmProgramacion.setSalaSelecionada(registro);
    }

    @Override
    public String paginaNombre() {
        return "Sala";
    }

    public void selecionarTipoSala(){
        sucursalSelecionada=sucursales.stream().filter(s->s.getIdSucursal().toString().equals(idSucursalSelecionado)).findFirst().orElse(null);

    }
    public void buscarProgramaciones() {
        programaciones=programacionBean.findProgramacionesByDate(fechaReservaSelecionada);
        programaciones=programaciones.stream().filter(p->(fechaReservaSelecionada.compareTo(p.getDesde())<0 && fechaReservaSelecionada.compareTo(p.getHasta())>0)).collect(Collectors.toList());

    }

    public List<String> sugerencias(String clave){
        List<String> sugerencias=new ArrayList<>();
        if (!programaciones.isEmpty()){
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            programaciones.forEach(p->{

                sugerencias.add(
                        p.getIdProgramacion() + "_" +
                                p.getIdPelicula().getNombre() + "," +
                                p.getIdSala().getNombre() + " (" +
                                sdf.format(p.getDesde()) + "-" + sdf.format(p.getHasta()) + ")");
            });

            List<String> results = new ArrayList<>();
            // Filtrar las sugerencias que coincidan con el texto ingresado
            for (String option : sugerencias) {
                if (option.toLowerCase().contains(clave.toLowerCase())) {
                    results.add(option);
                }
            }
            return results;
        }
        return List.of();
    }

    public void onProgramacionChange() {

        if (!programaciones.isEmpty()){
            programacionSelecionada = programaciones.stream().filter(p->p.getIdProgramacion().toString().equals(idProgramacion)).findFirst().orElse(null);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            fechaProgramacion="hora (" +sdf.format(programacionSelecionada.getDesde()) + "-" + sdf.format(programacionSelecionada.getHasta()) + ")";

        }
    }

    //Asientos ------------------------------------------------------------------

    public void buscarAsientosByProgramacion() {
        //buscar asientos asientos libres de una sala y programacion

        //buscar todos los asientos que tiene una sala
        try {
            asientosDisponibles=asientoBean.findAsientosBySalaandProgramacion(programacionSelecionada.getIdSala(),programacionSelecionada);

        }catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(),e);
            System.out.println("error al cargar los asientos");
        }

    }

    public void agregarAsiento(){
        try {
            Asiento asientoSelecionado=asientosDisponibles.stream().
                    filter(a->a.getIdAsiento().toString().equals(idAsientoSelecionado)).findFirst().orElse(null);
            if (asientosSelecionados==null){
                asientosSelecionados=new ArrayList<>();
            }
            if (asientoSelecionado!=null){
                asientosDisponibles.remove(asientoSelecionado);
                asientosSelecionados.add(asientoSelecionado);
            }
            idAsientoSelecionado="";
        }catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(),e);
        }
    }


    public void eliminarAsiento(){
        try {
            Asiento asientoEliminado=asientosSelecionados.stream().
                    filter(a->a.getIdAsiento().toString().equals(idAsientoELiminado)).findFirst().orElse(null);

            if (asientoEliminado!=null){
                asientosSelecionados.remove(asientoEliminado);
                asientosDisponibles.add(asientoEliminado);
                asientosDisponibles.sort(Comparator.comparingLong(Asiento::getIdAsiento));
            }
            idAsientoSelecionado="";
        }catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(),e);
        }
    }



    //SalaCaracteristica -------------------------------------------------------

    //SalaCaracteristica -------------------------------------------------------

   /* public FrmSalaCaracteristica getFrmSalaCaracteristica() {
        return frmSalaCaracteristica;
    }

    public void setFrmSalaCaracteristica(FrmSalaCaracteristica frmSalaCaracteristica) {
        this.frmSalaCaracteristica = frmSalaCaracteristica;
    }*/

    public List<Sucursal> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<Sucursal> sucursales) {
        this.sucursales = sucursales;
    }

    public Sucursal getSucursalSelecionada() {
        return sucursalSelecionada;
    }

    public void setSucursalSelecionada(Sucursal sucursalSelecionada) {
        this.sucursalSelecionada = sucursalSelecionada;
    }

    public String getIdSucursalSelecionado() {
        return idSucursalSelecionado;
    }

    public void setIdSucursalSelecionado(String idSucursalSelecionado) {
        this.idSucursalSelecionado = idSucursalSelecionado;
    }

    public frmSalaCaracteristica getFrmSalaCaracteristica() {
        return frmSalaCaracteristica;
    }

    public void setFrmSalaCaracteristica(frmSalaCaracteristica frmSalaCaracteristica) {
        this.frmSalaCaracteristica = frmSalaCaracteristica;
    }

    public FrmProgramacion getFrmProgramacion() {
        return frmProgramacion;
    }


    public void setFrmProgramacion(FrmProgramacion frmProgramacion) {
        this.frmProgramacion = frmProgramacion;
    }

    public void sugerencias(){

    }


    public frmAsiento getFrmAsiento() {
        return frmAsiento;
    }

    public void setFrmAsiento(frmAsiento frmAsiento) {
        this.frmAsiento = frmAsiento;
    }



    @Override
    public void btnCancelarHandler(ActionEvent actionEvent) {
        frmSalaCaracteristica.estado=ESTADO_CRUD.NINGUNO;
        frmSalaCaracteristica.registro=null;
        super.btnCancelarHandler(actionEvent);
    }

    @Override
    public void btnNuevoHandler(ActionEvent actionEvent) {
        frmSalaCaracteristica.estado=ESTADO_CRUD.NINGUNO;
        frmSalaCaracteristica.registro=null;
        super.btnNuevoHandler(actionEvent);
    }



    public void metodoPruebas(){
        System.out.println("probando");

    }




}