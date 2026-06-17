package duoc.personalaseo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "personalaseo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalAseo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPersonalAseo;

    @Column(unique = true, length = 13, nullable = false)
    private String rutPersonalAseo;

    @Column(nullable = false)
    private String nombrePersonalAseo;

    @Column(nullable = false)
    private String apellidoPersonalAseo;

    @Column(nullable = false)
    private Integer sueldoPersonalAseo;
}
