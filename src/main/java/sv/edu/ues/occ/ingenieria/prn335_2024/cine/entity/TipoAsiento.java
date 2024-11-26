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
@Table(name = "tipo_asiento")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "TipoAsiento.findAll", query = "SELECT t FROM TipoAsiento t"),
        @NamedQuery(name = "TipoAsiento.findByIdTipoAsiento", query = "SELECT t FROM TipoAsiento t WHERE t.idTipoAsiento = :idTipoAsiento"),
        @NamedQuery(name = "TipoAsiento.findByAsientoCaracteristica", query = "SELECT t FROM TipoAsiento t WHERE t.asientoCaracteristicaList = :asientoCaracteristica"),
        @NamedQuery(name = "TipoAsiento.findByNombre", query = "SELECT t FROM TipoAsiento t WHERE t.nombre = :nombre"),
        @NamedQuery(name = "TipoAsiento.findByActivo", query = "SELECT t FROM TipoAsiento t WHERE t.activo = :activo"),
        @NamedQuery(name = "TipoAsiento.findByComentarios", query = "SELECT t FROM TipoAsiento t WHERE t.comentarios = :comentarios"),
        @NamedQuery(name = "TipoAsiento.findByExpresionRegular", query = "SELECT t FROM TipoAsiento t WHERE t.expresionRegular = :expresionRegular")})
public class TipoAsiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_asiento")
    private Integer idTipoAsiento;
    @Size(max = 155)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "activo")
    private Boolean activo;
    @Size(max = 2147483647)
    @Column(name = "comentarios")
    private String comentarios;
    @Size(max = 2147483647)
    @Column(name = "expresion_regular")
    private String expresionRegular;
    @OneToMany(mappedBy = "idTipoAsiento", fetch = FetchType.LAZY)
    private List<AsientoCaracteristica> asientoCaracteristicaList;

    public TipoAsiento() {
    }

    public TipoAsiento(Integer idTipoAsiento) {
        this.idTipoAsiento = idTipoAsiento;
    }

    public Integer getIdTipoAsiento() {
        return idTipoAsiento;
    }

    public void setIdTipoAsiento(Integer idTipoAsiento) {
        this.idTipoAsiento = idTipoAsiento;
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

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getExpresionRegular() {
        return expresionRegular;
    }

    public void setExpresionRegular(String expresionRegular) {
        this.expresionRegular = expresionRegular;
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
        hash += (idTipoAsiento != null ? idTipoAsiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoAsiento)) {
            return false;
        }
        TipoAsiento other = (TipoAsiento) object;
        if ((this.idTipoAsiento == null && other.idTipoAsiento != null) || (this.idTipoAsiento != null && !this.idTipoAsiento.equals(other.idTipoAsiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoAsiento[ idTipoAsiento=" + idTipoAsiento + " ]";
    }

}
