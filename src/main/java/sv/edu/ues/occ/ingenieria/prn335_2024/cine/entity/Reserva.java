/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
@Table(name = "reserva")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Reserva.findAll", query = "SELECT r FROM Reserva r"),
        @NamedQuery(name = "Reserva.findByIdReserva", query = "SELECT r FROM Reserva r WHERE r.idReserva = :idReserva"),
        @NamedQuery(name = "Reserva.findByFechaReserva", query = "SELECT r FROM Reserva r WHERE r.fechaReserva = :fechaReserva"),
        @NamedQuery(name = "Reserva.findByEstado", query = "SELECT r FROM Reserva r WHERE r.estado = :estado"),
        @NamedQuery(name = "Reserva.findByObservaciones", query = "SELECT r FROM Reserva r WHERE r.observaciones = :observaciones")})
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_reserva")
    private Long idReserva;
    @Column(name = "fecha_reserva")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaReserva;
    @Size(max = 155)
    @Column(name = "estado")
    private String estado;
    @Size(max = 2147483647)
    @Column(name = "observaciones")
    private String observaciones;
    @OneToMany(mappedBy = "idReserva", fetch = FetchType.LAZY)
    private List<ReservaDetalle> reservaDetalleList;
    @JoinColumn(name = "id_programacion", referencedColumnName = "id_programacion")
    @ManyToOne(fetch = FetchType.LAZY)
    private Programacion idProgramacion;
    @JoinColumn(name = "id_tipo_reserva", referencedColumnName = "id_tipo_reserva")
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoReserva idTipoReserva;

    public Reserva() {
    }

    public Reserva(Long idReserva) {
        this.idReserva = idReserva;
    }

    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @XmlTransient
    public List<ReservaDetalle> getReservaDetalleList() {
        return reservaDetalleList;
    }

    public void setReservaDetalleList(List<ReservaDetalle> reservaDetalleList) {
        this.reservaDetalleList = reservaDetalleList;
    }

    public Programacion getIdProgramacion() {
        return idProgramacion;
    }

    public void setIdProgramacion(Programacion idProgramacion) {
        this.idProgramacion = idProgramacion;
    }

    public TipoReserva getIdTipoReserva() {
        return idTipoReserva;
    }

    public void setIdTipoReserva(TipoReserva idTipoReserva) {
        this.idTipoReserva = idTipoReserva;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReserva != null ? idReserva.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reserva)) {
            return false;
        }
        Reserva other = (Reserva) object;
        if ((this.idReserva == null && other.idReserva != null) || (this.idReserva != null && !this.idReserva.equals(other.idReserva))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Reserva[ idReserva=" + idReserva + " ]";
    }

}
