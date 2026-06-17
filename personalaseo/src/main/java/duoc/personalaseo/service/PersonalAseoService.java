package duoc.personalaseo.service;

import duoc.personalaseo.dto.PersonalAseoRequest;
import duoc.personalaseo.exception.RutDuplicadoException;
import duoc.personalaseo.exception.PersonalAseoNoEncontradoException;
import duoc.personalaseo.model.PersonalAseo;
import duoc.personalaseo.repository.PersonalAseoRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;

import java.util.List;


@Service
@Transactional
public class PersonalAseoService {
    private static final Logger log =
            LoggerFactory.getLogger(PersonalAseoService.class);

    @Autowired
    private PersonalAseoRepository personalAseoRepository;

    //listar
    public List<PersonalAseo> listarTodos(){
        log.info("Listando a todo el personal de aseo");
        return personalAseoRepository.findAll();
    }

    //buscar
    public PersonalAseo buscarPorId(Integer idPersonalAseo){
        log.info("Buscando a personal de aseo con id: {}", idPersonalAseo);
        return personalAseoRepository.findById(idPersonalAseo)
                .orElseThrow(() -> new PersonalAseoNoEncontradoException("No se ha encontrado al personal de aseo con id: "+ idPersonalAseo));
    }

    //Guardar
    public PersonalAseo guardarPersonalAseo(PersonalAseoRequest request){
        if (personalAseoRepository.existsByRutPersonalAseo(request.getRutPersonalAseo())){
            throw new RutDuplicadoException("No se puede registrar el rut: " + request.getRutPersonalAseo() +
                    " porque esta duplicado");
        }

        PersonalAseo personalAseo = crearxRequest(request);
        return personalAseoRepository.save(personalAseo);
    }

    //Request
    public PersonalAseo crearxRequest(PersonalAseoRequest request){
        log.info("Creando Personal de aseo con Run:{}", request.getRutPersonalAseo());

        PersonalAseo personalAseo = new PersonalAseo();
        personalAseo.setRutPersonalAseo(request.getRutPersonalAseo());
        personalAseo.setNombrePersonalAseo(request.getNombrePersonalAseo());
        personalAseo.setApellidoPersonalAseo(request.getApellidoPersonalAseo());
        personalAseo.setSueldoPersonalAseo(request.getSueldoPersonalAseo());

        return personalAseo;
    }

    //Actualizar
    public PersonalAseo actualizar(Integer idPersonalAseo, PersonalAseoRequest request){
        log.info("Actualizando personal de aseo con id: {}", idPersonalAseo);
        PersonalAseo personalAseo = buscarPorId(idPersonalAseo);

        if(personalAseoRepository.existsByRutPersonalAseoAndIdPersonalAseo(request.getRutPersonalAseo(), idPersonalAseo)){
            throw new RutDuplicadoException("El rut ya se encuentra registrado por otro personal de aseo");
        }

        personalAseo.setRutPersonalAseo(request.getRutPersonalAseo());
        personalAseo.setNombrePersonalAseo(request.getNombrePersonalAseo());
        personalAseo.setApellidoPersonalAseo(request.getApellidoPersonalAseo());
        personalAseo.setSueldoPersonalAseo(request.getSueldoPersonalAseo());

        return personalAseoRepository.save(personalAseo);
    }

    //Eliminar
    public void eliminar(Integer idPersonalAseo){
        log.info("Eliminando personal de aseo con id: {}", idPersonalAseo);

        PersonalAseo personalAseo = buscarPorId(idPersonalAseo);
        personalAseoRepository.delete(personalAseo);
    }

    //Error simulado
    public void simularError(){
        log.error("Se ejecuto el metodo para simular un error interno");
        throw new RuntimeException("Error simulando para pruebas");
    }
}
