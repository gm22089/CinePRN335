package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.orden.LazySorted;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoProducto;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public abstract class AbstractFrm<T> implements Serializable {
    public abstract AbstractDataPersist<TipoSala> getDataAccess();

    public abstract FacesContext getFacesContext();

    public abstract String Rowkey(TipoSala object);

    public abstract TipoSala RowData(String rowKey);

    public abstract String getIdPorObjeto(TipoSala object);

    public abstract TipoProducto getObjetoPorId(String id);

    //metodos abstractos
    public abstract void instanciarRegistro();
    public abstract FacesContext getFC();
    public abstract AbstractDataPersist<T> getAbstractDataPersist();
    //   public abstract void btnSelecionarRegistroHandler(final Object id);
    public abstract String getIdByObject(T object);
    public abstract T getObjectById(String id);

    public abstract Pelicula findByIdPelicula(String id);

    public abstract void selecionarFila(SelectEvent<T> event);
    public abstract String paginaNombre();

    //propiedades
    ESTADO_CRUD estado=ESTADO_CRUD.NINGUNO;
    T registro;
    List<T> registros;
    LazyDataModel<T> modelo;
    //botones
    public void btnCancelarHandler(ActionEvent actionEvent) {
        this.estado=ESTADO_CRUD.NINGUNO;
        this.registro=null;
    }
    public void btnNuevoHandler(ActionEvent actionEvent) {
        instanciarRegistro();
        this.estado=ESTADO_CRUD.CREAR;
    }
    //arranque

    @PostConstruct
    public void init() {
        estado=ESTADO_CRUD.NINGUNO;

        inicioRegistros();
    }
    public void  inicioRegistros(){
        this.modelo=new LazyDataModel<T>() {

            //se indica cuantas filas tiene el entity atravas del metod count
            @Override
            public int count(Map<String, FilterMeta> map) {
                int result=0;
                try {
                    result=contar();
                }catch (Exception ex){
                    Logger.getLogger(AbstractFrm.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

                return result;
            }
            //se cargarn elelmetos de acuerdo al findrabge
//         public List<T> load(int init, int max, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
//
//           if (init >= 0 && max > 0){
//
//              try {
//                 AbstractDataPersist<T> clBean=getAbstractDataPersist();
//
////                 implementacion para ordenar
//                 if (!map.isEmpty()){
//                    String CampoOrden=map.values().stream().findFirst().get().getField();
//                    String direcion=map.values().stream().findFirst().get().getOrder().toString();
//                    return clBean.findRange(init,max,CampoOrden,direcion);
//                 }
//                 return clBean.findRange(init,max);
//              }catch (Exception e) {
//                 Logger.getLogger(AbstractFrm.class.getName()).log(Level.SEVERE, null, e);
//              }
//           }
//            return List.of();
//         }
            @Override
            public List<T> load(int init, int max, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {

                if (init >= 0 && max > 0){

                    try {
                        //implementacion para ordenar
                        return cargar(init,max);
                    }catch (Exception e) {
                        Logger.getLogger(AbstractFrm.class.getName()).log(Level.SEVERE, e.getMessage(), e);
                    }
                }
                return List.of();
            }
            @Override
            public String getRowKey(T object) {
                if (object != null) {
                    return getIdByObject(object);
                }
                return null;
            }

            @Override
            public T getRowData(String rowKey) {
                if (rowKey != null) {
                    return getObjectById(rowKey);

                }
                return null;
            }
        };

    }
    //persistencia
    public void btnGuardarHandler(ActionEvent e) {
        FacesMessage mensaje=new FacesMessage();;
        try {
            AbstractDataPersist<T> clBean = null;
            clBean = getAbstractDataPersist();
            clBean.create(registro);
            this.estado = ESTADO_CRUD.NINGUNO;
            mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
            mensaje.setSummary("registro guardado");
            getFC().addMessage(null,mensaje);
            this.registro = null;
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            mensaje.setSeverity(FacesMessage.SEVERITY_ERROR);
            mensaje.setSummary("no se pudo guardar el datos");
            mensaje.setDetail(ex.getMessage());
            getFC().addMessage(null,mensaje);
        }

    }

    public void btnModificarHandler(ActionEvent ex) {
        System.out.println("se esta modificando");
        T modificado = null;
        FacesMessage mensaje=new FacesMessage();;
        try {
            AbstractDataPersist<T> clBean = null;
            clBean = getAbstractDataPersist();
            modificado = clBean.update(registro);
            if (modificado != null) {
                //notificar que se elimino3
                mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
                mensaje.setSummary("registro modificado");
                getFC().addMessage(null,mensaje);

            }
        } catch (Exception e) {
//         Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            mensaje.setSeverity(FacesMessage.SEVERITY_ERROR);
            mensaje.setSummary("registro no se pudo modificar");
            mensaje.setDetail(e.getMessage());
            getFC().addMessage(null,mensaje);
        }
        this.estado = ESTADO_CRUD.NINGUNO;
        this.registro = null;

    }

    public void btneEliminarHandler(ActionEvent ex) {
        FacesMessage mensaje=new FacesMessage();;
        try {
            AbstractDataPersist<T> clBean = null;
            clBean = getAbstractDataPersist();
            clBean.delete(registro);
            this.estado = ESTADO_CRUD.NINGUNO;
            this.registro = null;
            mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
            mensaje.setSummary("registro eliminado");
            getFC().addMessage(null,mensaje);
            return;
        } catch (Exception e) {
            mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
            mensaje.setSummary("registro no se pudo eliminar");
            mensaje.setDetail(e.getMessage());
            getFC().addMessage(null,mensaje);
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);

        }
    }
    //modelo


    public LazyDataModel<T> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<T> modelo) {
        this.modelo = modelo;
    }

    public int contar(){
        try {
            return getAbstractDataPersist().count();
        }catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }
    public List<T> cargar(int first,int max){
        try {
            return getAbstractDataPersist().findRange(first,max);
        }catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return List.of();
    }

    //otros

    public List<T> getUpdateList(){
        AbstractDataPersist<T> clBean=getAbstractDataPersist();
        List<T> list;
        int max=clBean.count();
        try {
            list =clBean.findRange(0,max);
            return list;
        }catch (Exception e){
            return Collections.emptyList();
        }
    }

    //getters y setters

    public List<T> getRegistros() {
        return registros;
    }

    public void setRegistros(List<T> registros) {
        this.registros = registros;
    }

    public T getRegistro() {
        return registro;
    }

    public void setRegistro(T registro) {
        this.registro = registro;
    }

    public ESTADO_CRUD getEstado() {
        return estado;
    }

    public void setEstado(ESTADO_CRUD estado) {
        this.estado = estado;
    }

}