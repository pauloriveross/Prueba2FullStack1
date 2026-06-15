package duoc.mantenciones.service;

import duoc.mantenciones.client.ClienteClient;
import duoc.mantenciones.client.MecanicoClient;
import duoc.mantenciones.client.VehiculoClient;
import duoc.mantenciones.dto.ClienteResponse;
import duoc.mantenciones.dto.MantencionRequest;
import duoc.mantenciones.dto.MecanicoResponse;
import duoc.mantenciones.dto.VehiculoResponse;
import duoc.mantenciones.exception.*;
import duoc.mantenciones.model.Mantencion;
import duoc.mantenciones.repository.MantencionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class MantencionService {

    private final MantencionRepository mantencionRepository;
    private final WebClient.Builder webClientBuilder;

    @Value("${services.cliente.url:http://CLIENTE}")
    private String clienteServiceUrl;
    @Value("${services.vehiculo.url:http://VEHICULO}")
    private String vehiculoServiceUrl;
    @Value("${services.mecanico.url:http://MECANICO}")
    private String mecanicoServiceUrl;


    public List<Mantencion> listarTodos(){return mantencionRepository.findAll();}

    public Mantencion buscarPorId(Integer idMantencion){
        log.info("Buscando mantención con id: {}", idMantencion);
        return mantencionRepository.findById(idMantencion)
                .orElseThrow(() -> new IdMantencionNoEncontradaException("No se ha encontrado la mantención con id: "+ idMantencion));
    }

    public Mantencion guardarMantencion(MantencionRequest request, String token){
        ClienteResponse cliente = webClientBuilder.build().get()
                .uri(clienteServiceUrl + "/api/v1/clientes/{idCliente}", request.getIdCliente())
                .header("Authorization",token)
                .retrieve().bodyToMono(ClienteResponse.class).block();

        VehiculoResponse vehiculo = webClientBuilder.build().get()
                .uri(vehiculoServiceUrl + "/api/v1/vehiculos/{idVehiculo}", request.getIdVehiculo())
                .header("Authorization",token)
                .retrieve().bodyToMono(VehiculoResponse.class).block();

        MecanicoResponse mecanico = webClientBuilder.build().get()
                .uri(mecanicoServiceUrl + "/api/v1/mecanicos/{idMecanico}",request.getIdMecanico())
                .header("Authorization",token)
                .retrieve().bodyToMono(MecanicoResponse.class).block();

        if(cliente == null){
            throw new IllegalArgumentException("El cliente con el id " + request.getIdCliente() + " no existe");
        }

        if (vehiculo == null){
            throw new IdVehiculoNoEncontradoException("El vehiculo con el id " + request.getIdVehiculo() + " no existe");
        }

        if (mecanico == null){
            throw new IdMantencionNoEncontradaException("El mecanico con el id " + request.getIdMecanico() + " no existe")
        }

        Mantencion nuevaMantencion = new Mantencion();

        nuevaMantencion.setIdCliente(request.getIdCliente());
        nuevaMantencion.setIdVehiculo(request.getIdVehiculo());
        nuevaMantencion.setIdMecanico(request.getIdMecanico());


        return mantencionRepository.save(nuevaMantencion);
    }

    public void eliminarMantencion(Integer idMantencion){
        log.info("Eliminando mantención con id {}",idMantencion);
        Mantencion mantencion = buscarPorId(idMantencion);
        mantencionRepository.delete(mantencion);
    }

    public Mantencion actualizarMantencion(Integer idMantencion, MantencionRequest request, String token) {
        log.info("Actualizando Mantención con id {}",idMantencion);
        Mantencion mantencion = buscarPorId(idMantencion);

        if(mantencionRepository.existsByIdVehiculoAndIdMantencionNot(request.getIdVehiculo(), idMantencion)){

            throw new IdVehiculoDuplicadoException("El vehiculo ya se encuentra registrado en otra mantención");
        }

        ClienteResponse cliente = webClientBuilder.build().get()
                .uri(clienteServiceUrl + "/api/v1/clientes/{idCliente}",request.getIdCliente())
                .header("Authorization",token)
                .retrieve().bodyToMono(ClienteResponse.class).block();

        VehiculoResponse vehiculo = webClientBuilder.build().get()
                .uri(vehiculoServiceUrl + "/api/v1/vehiculos/{idVehiculo}",request.getIdVehiculo())
                .header("Authorization",token)
                .retrieve().bodyToMono(VehiculoResponse.class).block();

        MecanicoResponse mecanico = webClientBuilder.build().get()
                .uri(mecanicoServiceUrl + "/api/v1/mecanicos/{idMecanico}",request.getIdMecanico())
                .header("Authorization",token)
                .retrieve().bodyToMono(MecanicoResponse.class).block();

        mantencion.setIdCliente(request.getIdCliente());
        mantencion.setIdVehiculo(request.getIdVehiculo());
        mantencion.setIdMantencion(request.getIdMecanico());

        return  mantencionRepository.save(mantencion);
    }

    public Mantencion crearDesdeRequest(MantencionRequest mantencionRequest){
        log.info("Creando mantención con idVehiculo: {}", mantencionRequest.getIdVehiculo());
        Mantencion mantencion = new Mantencion();
        mantencion.setFechaMantencion(mantencionRequest.getFechaMantencion());
        mantencion.setPrecioMantencion(mantencionRequest.getPrecioMantencion());
        mantencion.setTipoMantencion(mantencionRequest.getTipoMantencion());
        mantencion.setIdVehiculo(mantencionRequest.getIdVehiculo());
        mantencion.setIdMecanico(mantencionRequest.getIdMecanico());
        mantencion.setIdCliente(mantencionRequest.getIdCliente());
        return mantencion;
    }

}