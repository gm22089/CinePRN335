package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Asiento;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Programacion;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@LocalBean
@Stateless
public class ProgramacionBean extends AbstractDataPersist<Programacion> implements Serializable {
    @PersistenceContext(unitName = "cinePU")
    EntityManager em;
    public ProgramacionBean() {
        super(Programacion.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Programacion> getProgramacionByIdSalaRangoTiempo(Sala sala) {
        if (sala !=null){
            try {
                LocalDate fechaInicio = LocalDate.now().minusMonths(6);
                LocalDate fechaFin = LocalDate.now().plusMonths(3);

                // Convertir a Date si el campo es de tipo Date
                Date inicio = Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date fin = Date.from(fechaFin.atStartOfDay(ZoneId.systemDefault()).toInstant());

                return em.createNamedQuery("Programacion.findProgramacionBySalaRangoTiempo", Programacion.class).
                        setParameter("sala", sala)
                        .setParameter("fechaInicio",inicio)
                        .setParameter("fechaFin",fin)
                        .getResultList();
            }catch (Exception ex){
                Logger.getLogger(SalaBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


        return List.of();
    }


    // es creado por hdz
    public List<Programacion> findProgramacionesByDate(Date fechaReserva) {
        // La consulta JPQL para seleccionar solo las programaciones activas en la fecha especificada
        TypedQuery<Programacion> query = em.createQuery(
                "SELECT p FROM Programacion p WHERE FUNCTION('DATE', p.desde) = :fecha", Programacion.class);
        query.setParameter("fecha", fechaReserva);
        return query.getResultList();
    }





    public List<Asiento> findAsientoParaReserva(Date fecha) {
        try {
            return em.createNamedQuery("Programacion.findAsientoParaReserva", Asiento.class).getResultList();
        }catch (Exception ex){
            Logger.getLogger(SalaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return List.of();
    }
    public boolean verificarColision(Date desde, Date hasta, Sala sala) {
        try {
            TypedQuery<Programacion> query = em.createQuery(
                    "SELECT pr FROM Programacion pr " +
                            "WHERE ( " +
                            "    (:desde < pr.hasta AND :hasta > pr.desde) " +
                            ") " +
                            "AND pr.idSala.idSala = :idSala",
                    Programacion.class
            );

            query.setParameter("desde", desde); // Inicio del rango nuevo
            query.setParameter("hasta", hasta); // Fin del rango nuevo
            query.setParameter("idSala", sala.getIdSala()); // Sala específica

            List<Programacion> resultados = query.getResultList();

            // Si no hay colisiones, la lista estará vacía
            System.out.println("que paso");
            return resultados.isEmpty();
        } catch (Exception ex) {
            Logger.getLogger(SalaBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false; // Asumimos colisión si ocurre un error
    }


}