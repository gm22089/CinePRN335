package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.*;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ViewScoped
@Named
public class FrmReservas extends AbstractFrm<Reserva> implements Serializable {
    @Inject
    ReservaBean reservaBean;
    @Inject
    ReservaDetalleBean reservaDetalleBean;
    @Inject
    FacesContext fc;
    @Inject
    TipoReservaBean tipoReservaBean;
    @Inject
    ProgramacionBean programacionBean;
    @Inject
    AsientoBean asientoBean;


    //variables para la pagina
    //primer tab
    int indiceTab = 0;
    TipoReserva tipoReservaSelecionada;
    Date fechaReservaSelecionada;
    int idTipoReservaSelecionada;
    //segundo tab
    List<TipoReserva> tipoReservasDisponibles;
    List<Programacion> programaciones;
    Programacion programacionSelecionada;
    String idProgramacion;
    String fechaProgramacion;
    //tercer tab
    List<Asiento> asientosDisponibles;
    List<Asiento> asientosSelecionados;
    String idAsientoSelecionado;
    String idAsientoELiminado;
    Date fechaActual;

    @Override
    public void instanciarRegistro() {
        registro = new Reserva();
    }

    @PostConstruct
    @Override
    public void init() {
        super.init();
        tipoReservasDisponibles = tipoReservaBean.findAll();
        System.out.println("la cantidad de reservas son" + tipoReservasDisponibles.size());
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<Reserva> getAbstractDataPersist() {
        return reservaBean;
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
        if (id != null && modelo != null && modelo.getWrappedData() != null) {
            return modelo.getWrappedData().stream().filter(r -> id.equals(r.getIdReserva().toString())).findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<Reserva> event) {
    }

    @Override
    public String paginaNombre() {
        return "Reserva";
    }

    private boolean esFechaReservaValida() {
        // Obtener fecha actual truncada al inicio del día
        Date fechaActual = truncarFecha(getFechaActual());

        // Verificar si la fecha seleccionada no es nula
        if (fechaReservaSelecionada != null) {
            // Truncar también la fecha seleccionada
            Date fechaSeleccionada = truncarFecha(fechaReservaSelecionada);

            // Validar que la fecha seleccionada no sea menor a la fecha actual
            if (fechaSeleccionada.before(fechaActual)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Fecha inválida",
                                "La fecha seleccionada no puede ser menor a la fecha actual."));
                return false;
            }
        } else {
            // Mensaje si no se selecciona ninguna fecha
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Fecha requerida",
                            "Debe seleccionar una fecha de reserva."));
            return false;
        }
        return true;
    }


    private Date truncarFecha(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }





    //funcionalidad
    public void nextTab() {
        if (!esFechaReservaValida()) {
            // Si la fecha no es válida, salir del método
            return;
        }
        switch (indiceTab) {
            case 0:
                if (tipoReservaSelecionada != null && fechaReservaSelecionada != null) {
                    indiceTab++;
                    buscarProgramaciones();
                } else {
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se puede seguir ", "DEBES SELECIONAR "
                            + (tipoReservaSelecionada == null ? "UN TIPO DE RESERVA" : "UNA FECHA")));
                }

                break;
            case 1:
                if (programacionSelecionada != null) {
                    indiceTab++;
                    buscarAsientosByProgramacion();
                }
                break;
            case 2:
                if (!asientosSelecionados.isEmpty()) {
                    indiceTab++;
                }
                break;
            case 3:
                indiceTab = 0;
                programacionSelecionada = null;
                tipoReservaSelecionada = null;
                fechaReservaSelecionada = null;
                asientosSelecionados.clear();
                asientosDisponibles.clear();

                break;
            default:
                indiceTab = 0;
        }
    }

    public void BackTab() {
        if (indiceTab > 0) {
            indiceTab--;
        }
    }

    public void onTipoReservaChange() {
        if (idTipoReservaSelecionada != 0 && !tipoReservasDisponibles.isEmpty()) {
            tipoReservaSelecionada = tipoReservasDisponibles.stream().filter(r -> r.getIdTipoReserva().equals(idTipoReservaSelecionada)).findFirst().orElse(null);
        }
    }

    public void cambiarTab() {
        buscarProgramaciones();
    }

    //    es creado por hdz
    public void buscarProgramaciones() {
        // Llamar directamente al método que devuelve las programaciones filtradas
        programaciones = programacionBean.findProgramacionesByDate(fechaReservaSelecionada);
        System.out.println("Programaciones encontradas para la fecha " + fechaReservaSelecionada + ":");
        programaciones.forEach(p -> System.out.println("Desde: " + p.getDesde() + ", Hasta: " + p.getHasta()));

    }

//        public void buscarProgramaciones() {
//        programaciones=programacionBean.findProgramacionesByDate(fechaReservaSelecionada);
//        programaciones=programaciones.stream().filter(p->(fechaReservaSelecionada.compareTo(p.getDesde())>0 && fechaReservaSelecionada.compareTo(p.getHasta())<0)).collect(Collectors.toList());
//
//    }

    public void buscarAsientosByProgramacion() {
        //buscar asientos asientos libres de una sala y programacion

        //buscar todos los asientos que tiene una sala
        try {
            asientosDisponibles = asientoBean.findAsientosBySalaandProgramacion(programacionSelecionada.getIdSala(), programacionSelecionada);
            System.out.println("los asientos displonibles son " + asientosDisponibles.size());
            asientosDisponibles.forEach(System.out::println);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            System.out.println("error al cargar los asientos");
        }
    }

    //    esto es de hdzzz
    public List<String> sugerencias(String clave) {
        List<String> sugerencias = new ArrayList<>();
        if (!programaciones.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            programaciones.forEach(p -> {
                sugerencias.add(
                        p.getIdProgramacion() + "_" +
                                p.getIdPelicula().getNombre() + "," +
                                p.getIdSala().getNombre() + " (" +
                                sdf.format(p.getDesde()) + "-" + sdf.format(p.getHasta()) + ")");
            });
            return sugerencias.stream()
                    .filter(option -> option.toLowerCase().contains(clave.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return List.of();
    }


    public void onItemSelect(AjaxBehaviorEvent event) {
        // Obtener el valor del input automáticamente
        String selectedValue = (String) ((UIInput) event.getSource()).getValue();
        System.out.println("Seleccionaste: " + selectedValue);
    }

    //    esto es de hdz
    public void onProgramacionChange(SelectEvent event) {
        String idString = (String) event.getObject();
        Long id = Long.parseLong(idString.split("_")[0]);
        programacionSelecionada = programaciones.stream()
                .filter(p -> p.getIdProgramacion().equals(id))
                .findFirst() .orElse(null);
        if (programacionSelecionada != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            fechaProgramacion = "hora (" + sdf.format(programacionSelecionada.getDesde()) + "-" + sdf.format(programacionSelecionada.getHasta()) + ")"; }
    }


    public void agregarAsiento() {
        try {
            Asiento asientoSelecionado = asientosDisponibles.stream().
                    filter(a -> a.getIdAsiento().toString().equals(idAsientoSelecionado)).findFirst().orElse(null);
            if (asientosSelecionados == null) {
                asientosSelecionados = new ArrayList<>();
            }
            if (asientoSelecionado != null) {
                asientosDisponibles.remove(asientoSelecionado);
                asientosSelecionados.add(asientoSelecionado);
            }
            idAsientoSelecionado = "";
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void eliminarAsiento() {
        try {
            Asiento asientoEliminado = asientosSelecionados.stream().
                    filter(a -> a.getIdAsiento().toString().equals(idAsientoELiminado)).findFirst().orElse(null);

            if (asientoEliminado != null) {
                asientosSelecionados.remove(asientoEliminado);
                asientosDisponibles.add(asientoEliminado);
                asientosDisponibles.sort(Comparator.comparingLong(Asiento::getIdAsiento));
            }
            idAsientoSelecionado = "";
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public String formateoFechaReserva() {
        if (programacionSelecionada != null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        try {
            String formaro = "hora " + sdf.format(programacionSelecionada.getDesde()) + "-" + sdf.format(programacionSelecionada.getHasta()) + ")";
            System.out.println("prueba");
            System.out.println(sdf.format(programacionSelecionada.getDesde()));
            System.out.println(sdf.format(programacionSelecionada.getHasta()));
            System.out.println(formaro);
            return formaro;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return "problemas";
        }
    }

    public void crearReserva() {
        if (programacionSelecionada != null && tipoReservaSelecionada != null && fechaReservaSelecionada != null) {
            Reserva reserva = new Reserva();
            try {
                reserva.setIdProgramacion(programacionSelecionada);
                reserva.setIdTipoReserva(tipoReservaSelecionada);
                reserva.setFechaReserva(fechaReservaSelecionada);
                reserva.setEstado("CREADO");
                reservaBean.create(reserva);

                ReservaDetalle reservaDetalle = new ReservaDetalle();
                reservaDetalle.setIdReserva(reserva);
                try {
                    asientosSelecionados.forEach(a -> {
                        reservaDetalle.setIdAsiento(a);
                        reservaDetalle.setEstado("Creado");
                        reservaDetalleBean.create(reservaDetalle);
                    });
                } catch (Exception e) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                    reservaBean.delete(reserva);
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "error al asignar el asiento", "falso"));

                }
                nextTab();
                fc.addMessage(null, new FacesMessage("se ha credao la reserva"));
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "se ha credao la reserva", "falso"));

            }
        }
    }

    //getters y setter

    public Date getFechaActual() {
        // Crea una nueva instancia de Date y setea solo la fecha (sin hora)
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime(); // Devuelve solo la fecha sin la hora
    }

    public int getIndiceTab() {
        return indiceTab;
    }

    public void setIndiceTab(int indiceTab) {
        this.indiceTab = indiceTab;
    }

    public TipoReservaBean getTipoReservaBean() {
        return tipoReservaBean;
    }

    public void setTipoReservaBean(TipoReservaBean tipoReservaBean) {
        this.tipoReservaBean = tipoReservaBean;
    }

    public TipoReserva getTipoReservaSelecionada() {
        return tipoReservaSelecionada;
    }

    public void setTipoReservaSelecionada(TipoReserva tipoReservaSelecionada) {
        this.tipoReservaSelecionada = tipoReservaSelecionada;
    }

    public List<TipoReserva> getTipoReservasDisponibles() {
        return tipoReservasDisponibles;
    }

    public void setTipoReservasDisponibles(List<TipoReserva> reservasDisponibles) {
        this.tipoReservasDisponibles = reservasDisponibles;
    }

    public ReservaBean getReservaBean() {
        return reservaBean;
    }

    public void setReservaBean(ReservaBean reservaBean) {
        this.reservaBean = reservaBean;
    }


    public Date getFechaReservaSelecionada() {
        return fechaReservaSelecionada;
    }

    public void setFechaReservaSelecionada(Date fechaReservaSelecionada) {
        this.fechaReservaSelecionada = fechaReservaSelecionada;
    }

    public int getIdTipoReservaSelecionada() {
        return idTipoReservaSelecionada;
    }

    public void setIdTipoReservaSelecionada(int idTipoReservaSelecionada) {
        this.idTipoReservaSelecionada = idTipoReservaSelecionada;
    }

    public Programacion getProgramacionSelecionada() {
        return programacionSelecionada;
    }

    public void setProgramacionSelecionada(Programacion programacionSelecionada) {
        this.programacionSelecionada = programacionSelecionada;
    }

    public String getIdProgramacion() {
        return idProgramacion;
    }

    public void setIdProgramacion(String idProgramacion) {
        this.idProgramacion = idProgramacion;
    }

    public String getFechaProgramacion() {
        return fechaProgramacion;
    }

    public void setFechaProgramacion(String fechaProgramacion) {
        this.fechaProgramacion = fechaProgramacion;
    }

    public List<Asiento> getAsientosSelecionados() {
        return asientosSelecionados;
    }

    public void setAsientosSelecionados(List<Asiento> asientosSelecionados) {
        this.asientosSelecionados = asientosSelecionados;
    }

    public List<Asiento> getAsientosDisponibles() {
        return asientosDisponibles;
    }

    public void setAsientosDisponibles(List<Asiento> asientosDisponibles) {
        this.asientosDisponibles = asientosDisponibles;
    }

    public String getIdAsientoSelecionado() {
        return idAsientoSelecionado;
    }

    public void setIdAsientoSelecionado(String idAsientoSelecionado) {
        this.idAsientoSelecionado = idAsientoSelecionado;
    }

    public String getIdAsientoELiminado() {
        return idAsientoELiminado;
    }

    public void setIdAsientoELiminado(String idAsientoELiminado) {
        this.idAsientoELiminado = idAsientoELiminado;
    }

}