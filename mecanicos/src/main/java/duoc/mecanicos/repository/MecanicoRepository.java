package duoc.mecanicos.repository;

import duoc.mecanicos.model.Mecanico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MecanicoRepository extends JpaRepository<Mecanico, Integer> {
    boolean existsByRutMecanico(String rutMecanico);
    boolean existsByRutMecanicoAndIdMecanicoNot(String rut, Integer idMecanico);
}
