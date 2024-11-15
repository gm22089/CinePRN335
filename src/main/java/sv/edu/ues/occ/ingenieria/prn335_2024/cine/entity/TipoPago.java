package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "tipo_pago")
@NamedQueries({
        @NamedQuery(name = "TipoPago.findAll", query = "SELECT t FROM TipoPago t"),
        @NamedQuery(name = "TipoPago.findByIdTipoPago", query = "SELECT t FROM TipoPago t WHERE t.idTipoPago = :idTipoPago"),
        @NamedQuery(name = "TipoPago.findByNombre", query = "SELECT t FROM TipoPago t WHERE t.nombre = :nombre"),
        @NamedQuery(name = "TipoPago.findByActivo", query = "SELECT t FROM TipoPago t WHERE t.activo = :activo")
})
public class TipoPago {

    @Id
    @Column(name = "id_tipo_pago", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática de ID
    private Integer idTipoPago;

    @Size(max = 155)
    @Column(name = "nombre", length = 155)
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "idTipoPago")
    private List<Pago> pagoList;  // Renombrado para seguir convenciones

    public TipoPago() {}

    public TipoPago(Integer idTipoPago) {
        this.idTipoPago = idTipoPago;
    }

    public Integer getIdTipoPago() {
        return idTipoPago;
    }

    public void setIdTipoPago(Integer idTipoPago) {
        this.idTipoPago = idTipoPago;
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

    public List<Pago> getPagoList() {
        return pagoList;
    }

    public void setPagoList(List<Pago> pagoList) {
        this.pagoList = pagoList;
    }
}
