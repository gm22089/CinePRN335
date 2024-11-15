package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "pelicula_caracteristica", schema = "public")
public class PeliculaCaracteristica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pelicula_caracteristica", nullable = false)
    private Long idPeliculaCaracteristica;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_pelicula", nullable = false)
    private TipoPelicula tipoPelicula;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pelicula", nullable = false)
    private Pelicula pelicula;

    @Lob
    @Column(name = "valor")
    private String valor;

    // Constructor vacío
    public PeliculaCaracteristica() {}

    // Constructor con ID
    public PeliculaCaracteristica(Long idPeliculaCaracteristica) {
        this.idPeliculaCaracteristica = idPeliculaCaracteristica;
    }

    // Getters y setters
    public Long getIdPeliculaCaracteristica() {
        return idPeliculaCaracteristica;
    }

    public void setIdPeliculaCaracteristica(Long idPeliculaCaracteristica) {
        this.idPeliculaCaracteristica = idPeliculaCaracteristica;
    }

    public TipoPelicula getTipoPelicula() {
        return tipoPelicula;
    }

    public void setTipoPelicula(TipoPelicula tipoPelicula) {
        this.tipoPelicula = tipoPelicula;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
