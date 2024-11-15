package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pelicula", schema = "public")
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pelicula", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "nombre")
    private String nombre;

    @Lob
    @Column(name = "sinopsis")
    private String sinopsis;

    @Column(name = "activainterna")
    private Boolean activainterna;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public Boolean getActivainterna() {
        return activainterna;
    }

    public void setActivainterna(Boolean activainterna) {
        this.activainterna = activainterna;
    }

}