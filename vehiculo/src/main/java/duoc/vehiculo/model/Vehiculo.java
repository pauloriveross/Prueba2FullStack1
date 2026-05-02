package duoc.vehiculo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVehiculo;

    @Column(nullable = false)
    private String marcaVehiculo;

    @Column(nullable = false)
    private String modeloVehiculo;

    @Column(nullable = false)
    private Integer annioVehiculo;

    @Column(nullable = true)
    private String tipoVehiculo;

    @Column(nullable = false)
    private Integer precioVehiculo;

    @Column(nullable = true)
    private Integer kilometrajeVehiculo;

    @Column(unique = true, nullable = false)
    private String patenteVehiculo;

    @Column(nullable = false)
    private String estadoVehiculo;

}
