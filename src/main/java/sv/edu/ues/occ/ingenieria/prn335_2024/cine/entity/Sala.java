package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sala", schema = "public")
@NamedQueries({
        @NamedQuery(name = "Sala.findAll", query = "SELECT s FROM Sala s"),
        @NamedQuery(name = "Sala.findByName", query = "SELECT s FROM Sala s WHERE LOWER(s.nombre) LIKE LOWER(:nombre) ORDER BY s.nombre ASC"),
        @NamedQuery(name = "Sala.countActive", query = "SELECT COUNT(s) FROM Sala s WHERE s.activo = true"),
        @NamedQuery(name = "Sala.findByIdTipoSala", query = "SELECT s FROM SalaCaracteristica sc JOIN sc.idSala s WHERE sc.idTipoSala.idTipoSala = :idTipoSala GROUP BY s.idSala ORDER BY s.nombre ASC")
})
public class Sala implements Serializable {
    @Id
    @Column(name = "id_sala", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSala;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sucursal")
    private Sucursal idSucursal;

    @Size(max = 155)
    @Column(name = "nombre", length = 155)
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    @Lob
    @Column(name = "observaciones")
    private String observaciones;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "idSala")
    private List<Asiento> asientoList = new ArrayList<>();  // Inicialización de la lista

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "idSala")
    private List<SalaCaracteristica> salaCaracteristicaList = new ArrayList<>();  // Corregido el nombre de la lista

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "idSala")
    private List<Programacion> programacionList = new ArrayList<>();  // Inicialización de la lista

    public Sala() {
    }

    public Sala(Integer idSala) {
        this.idSala = idSala;
    }

    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer id) {
        this.idSala = id;
    }

    public Sucursal getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Sucursal idSucursal) {
        this.idSucursal = idSucursal;
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<Asiento> getAsientoList() {
        return asientoList;
    }

    public void setAsientoList(List<Asiento> asientoList) {
        this.asientoList = asientoList;
    }

    public List<SalaCaracteristica> getSalaCaracteristicaList() {
        return salaCaracteristicaList;
    }

    public void setSalaCaracteristicaList(List<SalaCaracteristica> salaCaracteristicaList) {
        this.salaCaracteristicaList = salaCaracteristicaList;
    }

    public List<Programacion> getProgramacionList() {
        return programacionList;
    }

    public void setProgramacionList(List<Programacion> programacionList) {
        this.programacionList = programacionList;
    }
}
