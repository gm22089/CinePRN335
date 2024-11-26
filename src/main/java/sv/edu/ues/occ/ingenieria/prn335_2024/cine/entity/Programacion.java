
package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author mj99lopez
 */
@Entity
@Table(name = "programacion")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Programacion.findAll", query = "SELECT p FROM Programacion p"),
        @NamedQuery(name = "Programacion.CountAll", query = "SELECT COUNT (p) FROM Programacion p"),
        @NamedQuery(name = "Programacion.findByFecha",query = "SELECT p FROM Programacion p WHERE FUNCTION('DATE', p.desde) = :fecha AND FUNCTION('DATE', p.hasta) = :fecha"),
        @NamedQuery(name = "Programacion.findByIdProgramacion", query = "SELECT p FROM Programacion p WHERE p.idProgramacion = :idProgramacion"),
        @NamedQuery(name = "Programacion.findByDesde", query = "SELECT p FROM Programacion p WHERE p.desde = :desde"),
        @NamedQuery(name = "Programacion.findByHasta", query = "SELECT p FROM Programacion p WHERE p.hasta = :hasta"),
        @NamedQuery(name = "Programacion.findProgramacionBySala", query = "SELECT p FROM Programacion p WHERE p.idSala = :sala"),
        @NamedQuery(name = "Programacion.findProgramacionBySalaRangoTiempo", query = "SELECT p FROM Programacion p WHERE p.idSala = :sala AND p.desde BETWEEN :fechaInicio AND :fechaFin"),
        @NamedQuery(name = "Programacion.findAsientoParaReserva", query = "SELECT a FROM Sala s,Asiento a where a.asientoCaracteristicaList = a "),
        @NamedQuery(name = "Programacion.findByComentarios", query = "SELECT p FROM Programacion p WHERE p.comentarios = :comentarios")})
public class Programacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_programacion")
    private Long idProgramacion;
    @Column(name = "desde")
    @Temporal(TemporalType.TIMESTAMP)
    private Date desde;
    @Column(name = "hasta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hasta;
    @Size(max = 2147483647)
    @Column(name = "comentarios")
    private String comentarios;
    @JoinColumn(name = "id_pelicula", referencedColumnName = "id_pelicula")
    @ManyToOne(fetch = FetchType.LAZY)
    private Pelicula idPelicula;
    @JoinColumn(name = "id_sala", referencedColumnName = "id_sala")
    @ManyToOne(fetch = FetchType.LAZY)
    private Sala idSala;
    @OneToMany(mappedBy = "idProgramacion", fetch = FetchType.LAZY)
    private List<Reserva> reservaList;

    public Programacion() {
    }

    public Programacion(Long idProgramacion) {
        this.idProgramacion = idProgramacion;
    }

    public Long getIdProgramacion() {
        return idProgramacion;
    }

    public void setIdProgramacion(Long idProgramacion) {
        this.idProgramacion = idProgramacion;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Pelicula getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Pelicula idPelicula) {
        this.idPelicula = idPelicula;
    }

    public Sala getIdSala() {
        return idSala;
    }

    public void setIdSala(Sala idSala) {
        this.idSala = idSala;
    }

    @XmlTransient
    public List<Reserva> getReservaList() {
        return reservaList;
    }

    public void setReservaList(List<Reserva> reservaList) {
        this.reservaList = reservaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProgramacion != null ? idProgramacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Programacion)) {
            return false;
        }
        Programacion other = (Programacion) object;
        if ((this.idProgramacion == null && other.idProgramacion != null) || (this.idProgramacion != null && !this.idProgramacion.equals(other.idProgramacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Programacion[ idProgramacion=" + idProgramacion + " ]";
    }

}