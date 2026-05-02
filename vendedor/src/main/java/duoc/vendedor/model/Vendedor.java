package duoc.vendedor.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vendedores")
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVendedor;

    @Column(unique = true, length = 13, nullable = false)
    private String rutVendedor;

    @Column(nullable = false)

    private String nombreVendedor;

    @Column(nullable = false)

    private String apellidoVendedor;

    @Column(nullable = true)

    private String seccionVendedor;

    @Column(nullable = true)

    private String turnoVendedor;

    @Column(nullable = false)

    private String emailVendedor;
}
