package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;


@Table(name = "tipo_pelicula", schema = "public")
@Entity
@NamedQueries({
        @NamedQuery(name = "TipoPelicula.findAll", query = "SELECT t FROM TipoPelicula t"),
        @NamedQuery(name = "TipoPelicula.findByName", query = "SELECT t FROM TipoPelicula t WHERE t.nombre LIKE :nombre"),
        @NamedQuery(name = "TipoPelicula.countActive", query = "SELECT COUNT(t) FROM TipoPelicula t WHERE t.activo = true"),
        @NamedQuery(name = "TipoPelicula.findByExpresionRegular", query = "SELECT t FROM TipoPelicula t WHERE t.expresionRegular = :expresionRegular")
})
public class TipoPelicula implements Serializable {
    @Id
    @Column(name = "id_tipo_pelicula", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoPelicula;

    @Size(max = 155)
    @Column(name = "nombre", length = 155)
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    @Lob
    @Column(name = "comentarios")
    private String comentarios;

    @Lob
    @Column(name = "expresion_regular")
    private String expresionRegular;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "idTipoPelicula")
    public List<PeliculaCaracteristica> PeliculaCaracteristicaList;

    public TipoPelicula() {}
    public TipoPelicula(Integer idTipoPelicula) {
        this.idTipoPelicula = idTipoPelicula;
    }

    public Integer getIdTipoPelicula() {
        return idTipoPelicula;
    }

    public void setIdTipoPelicula(Integer id) {
        this.idTipoPelicula = id;
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

    public List<PeliculaCaracteristica> getPeliculaCaracteristicaList() {
        return PeliculaCaracteristicaList;
    }

    public void setPeliculaCaracteristicaList(List<PeliculaCaracteristica> peliculaCaracteristicaList) {
        PeliculaCaracteristicaList = peliculaCaracteristicaList;
    }
}