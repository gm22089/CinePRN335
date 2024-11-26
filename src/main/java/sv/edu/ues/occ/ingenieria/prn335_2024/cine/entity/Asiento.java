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
@Table(name = "asiento")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Asiento.findAll", query = "SELECT a FROM Asiento a"),
        @NamedQuery(name = "Asiento.findAllAsientoCaracteristica", query = "SELECT ac FROM AsientoCaracteristica ac"),
        @NamedQuery(name = "Asiento.findByIdAsiento", query = "SELECT a FROM Asiento a WHERE a.idAsiento = :idAsiento"),
        @NamedQuery(name = "Asiento.findByNombre", query = "SELECT a FROM Asiento a WHERE a.nombre = :nombre"),
        @NamedQuery(name = "Asiento.MatchCaracterAndTipoWithSala", query = "SELECT ac.valor, ta.nombre FROM AsientoCaracteristica ac JOIN ac.idTipoAsiento ta WHERE ac.idAsiento.idAsiento = :asiento"),
        @NamedQuery(name = "Asiento.findIdAsientoBySala", query = "SELECT a FROM Asiento a WHERE a.idSala.idSala = :idSala"),
        @NamedQuery(name = "Asiento.countByIdAsiento", query = "SELECT COUNT (s) FROM Asiento s WHERE s.idSala.idSala = :idSala"),
        @NamedQuery(name = "Asiento.findAsientosBySalaandProgramacion",
                query = "SELECT a FROM Asiento a WHERE a.idSala = :idSala AND a.idAsiento NOT IN (SELECT rd.idAsiento.idAsiento FROM ReservaDetalle rd JOIN rd.idReserva r JOIN r.idProgramacion p WHERE p = :idProgramacion )"),
        @NamedQuery(name = "Asiento.findByActivo", query = "SELECT a FROM Asiento a WHERE a.activo = :activo")})
public class Asiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_asiento")
    private Long idAsiento;
    @Size(max = 155)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "activo")
    private Boolean activo;
    @OneToMany(mappedBy = "idAsiento", fetch = FetchType.LAZY)
    private List<ReservaDetalle> reservaDetalleList;
    @JoinColumn(name = "id_sala", referencedColumnName = "id_sala")
    @ManyToOne(fetch = FetchType.LAZY)
    private Sala idSala;
    @OneToMany(mappedBy = "idAsiento", fetch = FetchType.LAZY)
    private List<AsientoCaracteristica> asientoCaracteristicaList;


    public Asiento() {
    }

    public Asiento(Long idAsiento) {
        this.idAsiento = idAsiento;
    }

    public Long getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(Long idAsiento) {
        this.idAsiento = idAsiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @XmlTransient
    public List<ReservaDetalle> getReservaDetalleList() {
        return reservaDetalleList;
    }

    public void setReservaDetalleList(List<ReservaDetalle> reservaDetalleList) {
        this.reservaDetalleList = reservaDetalleList;
    }

    public Sala getIdSala() {
        return idSala;
    }

    public void setIdSala(Sala idSala) {
        this.idSala = idSala;
    }

    @XmlTransient
    public List<AsientoCaracteristica> getAsientoCaracteristicaList() {
        return asientoCaracteristicaList;
    }

    public void setAsientoCaracteristicaList(List<AsientoCaracteristica> asientoCaracteristicaList) {
        this.asientoCaracteristicaList = asientoCaracteristicaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAsiento != null ? idAsiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asiento)) {
            return false;
        }
        Asiento other = (Asiento) object;
        if ((this.idAsiento == null && other.idAsiento != null) || (this.idAsiento != null && !this.idAsiento.equals(other.idAsiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Asiento[ idAsiento=" + idAsiento + " ]";
    }

}
