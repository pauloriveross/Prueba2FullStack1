package duoc.mecanicos.service;

import duoc.mecanicos.dto.MecanicoRequest;
import duoc.mecanicos.exception.MecanicoNoEncontradoException;
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

    public List<Mecanico> listarTodos(){
        log.info("Listando todos los clientes");
        return mecanicoRepository.findAll();
    }

    public Mecanico buscarPorId(Integer idMecanico){
        log.info("Buscando cliente con id: {}", idMecanico);
        return mecanicoRepository.findById(idMecanico)
                .orElseThrow(() -> new MecanicoNoEncontradoException("No se encontro el mecanico con id: " + idMecanico));
    }

    public @Transactional Mecanico crearDesdeRequest(MecanicoRequest request){
        log.info("Creando cliente con rut: {}", request.getRutMecanico());

        if (mecanicoRepository.existsByRutMecanico(request.getRutMecanico())) {
            log.warn("Intento de registro duplicado para el RUT: {}", request.getRutMecanico());
            // You could throw a custom exception here that your Handler catches
            throw new EntityExistsException("El cliente con este RUT ya existe.");
        }
        Mecanico mecanico = new Mecanico();
        mecanico.setRutMecanico(request.getRutMecanico());
        mecanico.setNombreMecanico(request.getNombreMecanico());
        mecanico.setApellidoMecanico(request.getApellidoMecanico());

        return mecanicoRepository.save(mecanico);
    }

    public Mecanico actualizar(Integer idMecanico, MecanicoRequest request){
        log.info("Actualizando Mecanico con id: {}", idMecanico);
        Mecanico mecanico = buscarPorId(idMecanico);
        mecanico.setRutMecanico(request.getRutMecanico());
        mecanico.setNombreMecanico(request.getNombreMecanico());
        mecanico.setApellidoMecanico(request.getApellidoMecanico());

        return mecanicoRepository.save(mecanico);
    }

    public void eliminar(Integer idMecanico){
        log.info("Eliminando Mecanico con id: {}", idMecanico);
        Mecanico mecanico = buscarPorId(idMecanico);
        mecanicoRepository.delete(mecanico);
    }

    public void simularError(){
        log.error("Se ejecuto el metodo para simular un error interno");
        throw new RuntimeException("Error simulando para pruebas");
    }
}



