package duoc.ventas.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVenta;

    @Column(nullable = false)
    private Date fechaVenta;

    @Column(nullable = false)
    private Integer precioVehiculo;

    @Column(nullable = false)
    private String tipoPago;

    @Column(nullable = false)
    private Integer idCliente;

    @Column(nullable = false)
    private Integer idVehiculo;

    @Column(nullable = false)
    private Integer idVendedor;


}
