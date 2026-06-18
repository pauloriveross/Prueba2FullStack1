package duoc.corredorSeguro.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Builder
@Table(name = "corredores ")
public class CorredorSeguro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCorredor;


    @Column(unique = true, nullable = false)
    private String rutCorredor;

    @Column(nullable = false)
    private String nombreCorredor;

    @Column(nullable = false)
    private String apellidoCorredor;

    @Column(nullable = false)
    private String emailCorredor;

    @Column(nullable = false)
    private Integer sueldoBaseCorredor;


}
