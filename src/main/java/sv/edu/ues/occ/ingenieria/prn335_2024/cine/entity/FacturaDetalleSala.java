package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "factura_detalle_sala")
public class FacturaDetalleSala {
    @Id
    @Column(name = "id_factura_detalle_sala", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_factura")
    private Factura idFactura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reserva_detalle")
    private ReservaDetalle idReservaDetalle;

    @Column(name = "monto", precision = 10, scale = 2)
    private BigDecimal monto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Factura getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Factura idFactura) {
        this.idFactura = idFactura;
    }

    public ReservaDetalle getIdReservaDetalle() {
        return idReservaDetalle;
    }

    public void setIdReservaDetalle(ReservaDetalle idReservaDetalle) {
        this.idReservaDetalle = idReservaDetalle;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

}