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
@Table(name = "pelicula_caracteristica")
@XmlRootElement
@NamedQueries({

        @NamedQuery(name = "Caracteristica.findByPelicula", query = "SELECT c FROM PeliculaCaracteristica c WHERE c.idPeliculaCaracteristica = :peliculaId"),

        @NamedQuery(name = "PeliculaCaracteristica.findAll", query = "SELECT p FROM TipoPelicula p"),
        @NamedQuery(name = "PeliculaCaracteristica.findByIdPelicula", query = "SELECT pc FROM PeliculaCaracteristica pc WHERE pc.idPelicula.idPelicula = :idPelicula order by pc.idPeliculaCaracteristica asc "),
        @NamedQuery(name = "PeliculaCaracteristica.countByIdPelicula", query = "SELECT COUNT (pc.idPeliculaCaracteristica) FROM PeliculaCaracteristica pc WHERE pc.idPelicula.idPelicula = :idPelicula "),
        @NamedQuery(name = "PeliculaCaracteristica.findByValor", query = "SELECT p FROM PeliculaCaracteristica p WHERE p.valor = :valor")})

public class PeliculaCaracteristica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pelicula_caracteristica")
    private Long idPeliculaCaracteristica;
    @Size(max = 2147483647)
    @Column(name = "valor")
    private String valor;
    @JoinColumn(name = "id_pelicula", referencedColumnName = "id_pelicula")
    @ManyToOne(fetch = FetchType.LAZY)
    private Pelicula idPelicula;
    @JoinColumn(name = "id_tipo_pelicula", referencedColumnName = "id_tipo_pelicula")
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoPelicula idTipoPelicula;

    public PeliculaCaracteristica() {
    }

    public PeliculaCaracteristica(Long idPeliculaCaracteristica) {
        this.idPeliculaCaracteristica = idPeliculaCaracteristica;
    }

    public Long getIdPeliculaCaracteristica() {
        return idPeliculaCaracteristica;
    }

    public void setIdPeliculaCaracteristica(Long idPeliculaCaracteristica) {
        this.idPeliculaCaracteristica = idPeliculaCaracteristica;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Pelicula getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Pelicula idPelicula) {
        this.idPelicula = idPelicula;
    }

    public TipoPelicula getIdTipoPelicula() {
        return idTipoPelicula;
    }

    public void setIdTipoPelicula(TipoPelicula idTipoPelicula) {
        this.idTipoPelicula = idTipoPelicula;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPeliculaCaracteristica != null ? idPeliculaCaracteristica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PeliculaCaracteristica)) {
            return false;
        }
        PeliculaCaracteristica other = (PeliculaCaracteristica) object;
        if ((this.idPeliculaCaracteristica == null && other.idPeliculaCaracteristica != null) || (this.idPeliculaCaracteristica != null && !this.idPeliculaCaracteristica.equals(other.idPeliculaCaracteristica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.PeliculaCaracteristica[ idPeliculaCaracteristica=" + idPeliculaCaracteristica + " ]";
    }


}
