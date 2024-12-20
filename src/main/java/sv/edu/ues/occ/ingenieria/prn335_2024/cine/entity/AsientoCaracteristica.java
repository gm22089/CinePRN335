package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "asiento_caracteristica")
public class AsientoCaracteristica {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "asiento_caracteristica_id_gen")
    @SequenceGenerator(name = "asiento_caracteristica_id_gen", sequenceName = "asiento_caracteristica_id_asiento_caracteristica_seq", allocationSize = 1)
    @Column(name = "id_asiento_caracteristica", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_asiento")
    private Asiento idAsiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_asiento")
    private TipoAsiento idTipoAsiento;

    @Lob
    @Column(name = "valor")
    private String valor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

}