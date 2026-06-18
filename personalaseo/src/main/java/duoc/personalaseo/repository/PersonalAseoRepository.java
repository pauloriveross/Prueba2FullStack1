package duoc.personalaseo.repository;

import duoc.personalaseo.model.PersonalAseo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalAseoRepository extends JpaRepository <PersonalAseo, Integer>{

    boolean existsByRutPersonalAseo(String rutPersonalAseo);
    boolean existsByRutPersonalAseoAndIdPersonalAseoNot(String rut, Integer idPersonalAseo);

}
