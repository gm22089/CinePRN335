package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tipo_sala", schema = "public")
@NamedQueries({
        @NamedQuery(name = "TipoSala.findAll", query = "SELECT t FROM TipoSala t"),
        @NamedQuery(name = "TipoSala.findByName", query = "SELECT t FROM TipoSala t WHERE t.nombre LIKE :nombre"),
        @NamedQuery(name = "TipoSala.countActive", query = "SELECT COUNT(t) FROM TipoSala t WHERE t.activo = TRUE"),
        @NamedQuery(name = "TipoSala.findByExpresionRegular", query = "SELECT t FROM TipoSala t WHERE t.expresionRegular = :expresionRegular")
})
public class TipoSala implements Serializable {

    @Id
    @Column(name = "id_tipo_sala", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoSala;

    @NotBlank(message = "El nombre no es válido")
    @Size(max = 155, min = 3, message = "Debe agregar un nombre entre 3 y 155 caracteres")
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "idTipoSala")
    private List<SalaCaracteristica> salaCaracteristicaList;

    public TipoSala() {
    }

    public TipoSala(Integer idTipoSala) {
        this.idTipoSala = idTipoSala;
    }

    public Integer getIdTipoSala() {
        return idTipoSala;
    }

    public void setIdTipoSala(Integer idTipoSala) {
        this.idTipoSala = idTipoSala;
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

    public List<SalaCaracteristica> getSalaCaracteristicaList() {
        return salaCaracteristicaList;
    }

    public void setSalaCaracteristicaList(List<SalaCaracteristica> salaCaracteristicaList) {
        this.salaCaracteristicaList = salaCaracteristicaList;
    }
}
