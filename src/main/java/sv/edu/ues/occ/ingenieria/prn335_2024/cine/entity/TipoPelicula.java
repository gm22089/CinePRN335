package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "tipo_pelicula", schema = "public")
public class TipoPelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_pelicula", nullable = false)
    private Integer idTipoPelicula;

    @NotNull
    @Size(max = 155)
    @Column(name = "nombre", length = 155, nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Lob
    @Column(name = "comentarios")
    private String comentarios;

    @Lob
    @Column(name = "expresion_regular")
    private String expresionRegular;

    // Relación OneToMany con PeliculaCaracteristica
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tipoPelicula")
    private List<PeliculaCaracteristica> peliculaCaracteristicaList;

    // Constructor vacío
    public TipoPelicula() {}

    // Constructor con ID
    public TipoPelicula(Integer idTipoPelicula) {
        this.idTipoPelicula = idTipoPelicula;
    }

    // Getters y setters
    public Integer getIdTipoPelicula() {
        return idTipoPelicula;
    }

    public void setIdTipoPelicula(Integer idTipoPelicula) {
        this.idTipoPelicula = idTipoPelicula;
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
        return peliculaCaracteristicaList;
    }

    public void setPeliculaCaracteristicaList(List<PeliculaCaracteristica> peliculaCaracteristicaList) {
        this.peliculaCaracteristicaList = peliculaCaracteristicaList;
    }
}
