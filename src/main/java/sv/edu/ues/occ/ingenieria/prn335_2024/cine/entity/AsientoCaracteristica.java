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
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author mj99lopez
 */
@Entity
@Table(name = "asiento_caracteristica")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "AsientoCaracteristica.findAll", query = "SELECT a FROM AsientoCaracteristica a"),
        @NamedQuery(name = "AsientoCaracteristica.countAll", query = "SELECT COUNT(a) FROM AsientoCaracteristica a"),
        @NamedQuery(name = "AsientoCaracteristica.findByIdAsientoCaracteristica", query = "SELECT a FROM AsientoCaracteristica a WHERE a.idAsiento.idSala.idSala = :idAsientoCaracteristica"),
        @NamedQuery(name = "AsientoCaracteristica.findBySalaId", query = "SELECT a FROM AsientoCaracteristica a WHERE a.idAsiento.idSala = :idSala"),
        @NamedQuery(name = "AsientoCaracteristica.findCaracteristicaByIdAsiento", query = "SELECT a FROM AsientoCaracteristica a WHERE a.idAsiento.idAsiento = :idAsiento"),
        @NamedQuery(name = "AsientoCaracteristica.countCaracteristicaByIdAsiento", query = "SELECT COUNT(a.idAsientoCaracteristica) FROM AsientoCaracteristica a WHERE a.idAsiento.idAsiento = :idAsiento"),
        @NamedQuery(name = "AsientoCaracteristica.findAllTipoAsiento", query = "SELECT a FROM TipoAsiento a"),
        @NamedQuery(name = "AsientoCaracteristica.findByValor", query = "SELECT a FROM AsientoCaracteristica a WHERE a.valor = :valor")})
public class AsientoCaracteristica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_asiento_caracteristica")
    private Long idAsientoCaracteristica;
    @Size(max = 2147483647)
    @Column(name = "valor")
    private String valor;
    @JoinColumn(name = "id_asiento", referencedColumnName = "id_asiento")
    @ManyToOne(fetch = FetchType.LAZY)
    private Asiento idAsiento;
    @JoinColumn(name = "id_tipo_asiento", referencedColumnName = "id_tipo_asiento")
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoAsiento idTipoAsiento;

    public AsientoCaracteristica() {
    }

    public AsientoCaracteristica(Long idAsientoCaracteristica) {
        this.idAsientoCaracteristica = idAsientoCaracteristica;
    }

    public Long getIdAsientoCaracteristica() {
        return idAsientoCaracteristica;
    }

    public void setIdAsientoCaracteristica(Long idAsientoCaracteristica) {
        this.idAsientoCaracteristica = idAsientoCaracteristica;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Asiento getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(Asiento idAsiento) {
        this.idAsiento = idAsiento;
    }

    public TipoAsiento getIdTipoAsiento() {
        return idTipoAsiento;
    }

    public void setIdTipoAsiento(TipoAsiento idTipoAsiento) {
        this.idTipoAsiento = idTipoAsiento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAsientoCaracteristica != null ? idAsientoCaracteristica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsientoCaracteristica)) {
            return false;
        }
        AsientoCaracteristica other = (AsientoCaracteristica) object;
        if ((this.idAsientoCaracteristica == null && other.idAsientoCaracteristica != null) || (this.idAsientoCaracteristica != null && !this.idAsientoCaracteristica.equals(other.idAsientoCaracteristica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.AsientoCaracteristica[ idAsientoCaracteristica=" + idAsientoCaracteristica + " ]";
    }

}
