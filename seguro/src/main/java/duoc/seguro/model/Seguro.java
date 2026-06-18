package duoc.seguro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "seguro")
public class Seguro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSeguro;

    @Column(nullable = false)
    private Integer precioSeguro;

    @Column(nullable = false)
    private String tipoSeguro;

    @Column(nullable = false)
    private Integer idCliente;

    @Column(nullable = false)
    private Integer idVehiculo;

    @Column(nullable = false)
    private Integer idCorredorSeguro;

    @Column(nullable = false)
    private Integer comisionSeguro;
}