package duoc.mecanicos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mecanicos")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Mecanico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMecanico;

    @Column(unique = true, length = 13, nullable = false)
    private String rutMecanico;

    @Column(nullable = false)
    private String nombreMecanico;

    @Column(nullable = false)
    private String apellidoMecanico;

}
