package duoc.mantenciones.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mantenciones")
public class Mantencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMantencion")
    private Integer idMantencion;

    @Column(nullable = false,name = "fechaMantencion")
    private Date fechaMantencion;

    @Column(nullable = false,name = "precioMantencion")
    private Integer precioMantencion;

    @Column(nullable=false,name = "tipoMantencion")
    private String tipoMantencion;

    @Column(nullable = false,name = "idVehiculo")
    private Integer idVehiculo;

    @Column(nullable = false,name = "idMecanico")
    private Integer idMecanico;

    @Column(nullable = false, name = "idCliente")
    private Integer idCliente;





}
