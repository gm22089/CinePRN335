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
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author mj99lopez
 */
@Entity
@Table(name = "reserva_detalle")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "ReservaDetalle.findAll", query = "SELECT r FROM ReservaDetalle r"),
        @NamedQuery(name = "ReservaDetalle.findByIdReservaDetalle", query = "SELECT r FROM ReservaDetalle r WHERE r.idReservaDetalle = :idReservaDetalle"),
        @NamedQuery(name = "ReservaDetalle.findByEstado", query = "SELECT r FROM ReservaDetalle r WHERE r.estado = :estado")})
public class ReservaDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_reserva_detalle")
    private Long idReservaDetalle;
    @Size(max = 155)
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "id_asiento", referencedColumnName = "id_asiento")
    @ManyToOne(fetch = FetchType.LAZY)
    private Asiento idAsiento;
    @JoinColumn(name = "id_reserva", referencedColumnName = "id_reserva")
    @ManyToOne(fetch = FetchType.LAZY)
    private Reserva idReserva;
    @OneToMany(mappedBy = "idReservaDetalle", fetch = FetchType.LAZY)
    private List<FacturaDetalleSala> facturaDetalleSalaList;

    public ReservaDetalle() {
    }

    public ReservaDetalle(Long idReservaDetalle) {
        this.idReservaDetalle = idReservaDetalle;
    }

    public Long getIdReservaDetalle() {
        return idReservaDetalle;
    }

    public void setIdReservaDetalle(Long idReservaDetalle) {
        this.idReservaDetalle = idReservaDetalle;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Asiento getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(Asiento idAsiento) {
        this.idAsiento = idAsiento;
    }

    public Reserva getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Reserva idReserva) {
        this.idReserva = idReserva;
    }

    @XmlTransient
    public List<FacturaDetalleSala> getFacturaDetalleSalaList() {
        return facturaDetalleSalaList;
    }

    public void setFacturaDetalleSalaList(List<FacturaDetalleSala> facturaDetalleSalaList) {
        this.facturaDetalleSalaList = facturaDetalleSalaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReservaDetalle != null ? idReservaDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReservaDetalle)) {
            return false;
        }
        ReservaDetalle other = (ReservaDetalle) object;
        if ((this.idReservaDetalle == null && other.idReservaDetalle != null) || (this.idReservaDetalle != null && !this.idReservaDetalle.equals(other.idReservaDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.ReservaDetalle[ idReservaDetalle=" + idReservaDetalle + " ]";
    }

}
