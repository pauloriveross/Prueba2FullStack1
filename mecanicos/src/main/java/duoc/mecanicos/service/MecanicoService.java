package duoc.mecanicos.service;

import duoc.mecanicos.dto.MecanicoRequest;
import duoc.mecanicos.exception.MecanicoNoEncontradoException;
import duoc.mecanicos.exception.RutDuplicadoException;
import duoc.mecanicos.model.Mecanico;
import duoc.mecanicos.repository.MecanicoRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;

import java.util.List;

@Service
@Transactional
public class MecanicoService {
    private static final Logger log =
            LoggerFactory.getLogger(MecanicoService.class);

    @Autowired
    private MecanicoRepository mecanicoRepository;

    //Listar
    public List<Mecanico> listarTodos(){
        log.info("Listando todos los clientes");
        return mecanicoRepository.findAll();
    }

    //Buscar
    public Mecanico buscarPorId(Integer idMecanico){
        log.info("Buscando cliente con id: {}", idMecanico);
        return mecanicoRepository.findById(idMecanico)
                .orElseThrow(() -> new MecanicoNoEncontradoException("No se encontro el mecanico con id: " + idMecanico));
    }

    //Guardar
    public Mecanico guardarMecanico(MecanicoRequest request){
        if(mecanicoRepository.existsByRutMecanico(request.getRutMecanico())){
            throw new RutDuplicadoException("No se puede registrar el rut : " + request.getRutMecanico() +
                    " porque esta duplicado.");
        }
        Mecanico mecanico = crearxRequest(request);
        return mecanicoRepository.save(mecanico);
    }


    //Request
    public Mecanico crearxRequest(MecanicoRequest request){
        log.info("Creando Mecanico Con Run :{}",request.getRutMecanico());
        Mecanico mecanico = new Mecanico();
        mecanico.setRutMecanico(request.getRutMecanico());
        mecanico.setNombreMecanico(request.getNombreMecanico());
        mecanico.setApellidoMecanico(request.getApellidoMecanico());
        return mecanico;
    }


    //Actualizar
    public Mecanico actualizar(Integer idMecanico, MecanicoRequest request){
        log.info("Actualizando Mecánico con id: {}", idMecanico);
        Mecanico mecanico = buscarPorId(idMecanico);
        if (mecanicoRepository.existsByRutMecanicoAndIdMecanicoNot(request.getRutMecanico(),idMecanico)) {
            throw new RutDuplicadoException("El rut ya se encuentra registrado por otro mecánico");
        }
        mecanico.setRutMecanico(request.getRutMecanico());
        mecanico.setNombreMecanico(request.getNombreMecanico());
        mecanico.setApellidoMecanico(request.getApellidoMecanico());

        return mecanicoRepository.save(mecanico);
    }

    //Eliminar
    public void eliminar(Integer idMecanico){
        log.info("Eliminando Mecánico con id: {}", idMecanico);
        Mecanico mecanico = buscarPorId(idMecanico);
        mecanicoRepository.delete(mecanico);
    }

    //Error simulado
    public void simularError(){
        log.error("Se ejecuto el metodo para simular un error interno");
        throw new RuntimeException("Error simulando para pruebas");
    }
}



