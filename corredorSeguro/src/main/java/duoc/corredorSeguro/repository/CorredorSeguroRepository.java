package duoc.corredorSeguro.repository;


import duoc.corredorSeguro.model.CorredorSeguro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorredorSeguroRepository  extends JpaRepository<CorredorSeguro,Integer> {

    boolean existsByRutCorredor(String rutCorredor);


    boolean existsByRutCorredorAndIdCorredorNot(String rut , Integer idCorredor);



}
