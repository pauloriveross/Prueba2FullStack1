package duoc.seguro.service;


import duoc.seguro.dto.ClienteResponse;
import duoc.seguro.dto.CorredorSeguroResponse;
import duoc.seguro.dto.SeguroRequest;
import duoc.seguro.dto.VehiculoResponse;
import duoc.seguro.exception.IdCorredorSeguroNoEncontradoException;
import duoc.seguro.exception.IdVehiculoDuplicadoException;
import duoc.seguro.exception.IdVehiculoNoEncontradoException;
import duoc.seguro.exception.SeguroNoEncontradoException;
import duoc.seguro.model.Seguro;
import duoc.seguro.repository.SeguroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SeguroService {

    private final SeguroRepository seguroRepository;
    private final WebClient.Builder webClientBuilder;

    @Value("${services.cliente.url:http://CLIENTE}")
    private String clienteServiceUrl;
    @Value("${services.vehiculo.url:http://VEHICULO}")
    private String vehiculoServiceUrl;

    @Value("${services.corredor-seguro.url:http://CORREDOR-SEGURO}")
    private String corredorSeguroServiceUrl;

    //Get todos
    public List<Seguro> listarSeguro(){return seguroRepository.findAll();}

    //Get con ID
    public Seguro buscarSeguroPorId(Integer idSeguro){
        log.info("Buscando seguro por ID {}",idSeguro);
        return seguroRepository.findById(idSeguro).orElseThrow(()->
            new SeguroNoEncontradoException("No se encontro un seguro con el id" + idSeguro));
    }

    //Post
    public Seguro guardarSeguro(SeguroRequest request) {

        ClienteResponse cliente = webClientBuilder.build().get()
                .uri(clienteServiceUrl + "/api/v1/clientes/{idCliente}", request.getIdCliente())
                .retrieve().bodyToMono(ClienteResponse.class).block();
        VehiculoResponse vehiculo = webClientBuilder.build().get()
                .uri(vehiculoServiceUrl + "/api/v1/vehiculos/{idVehiculo}", request.getIdVehiculo())
                .retrieve().bodyToMono(VehiculoResponse.class).block();
        CorredorSeguroResponse corredorSeguro = webClientBuilder.build().get()
                .uri(corredorSeguroServiceUrl + "/api/v1/corredorSeguro/{id}", request.getIdCorredorSeguro())
                .retrieve().bodyToMono(CorredorSeguroResponse.class).block();


        // Validar que no sea nula la comunicación
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente con el id " + request.getIdCliente() + " no existe");
        }

        if (vehiculo == null) {
            throw new IdVehiculoNoEncontradoException("El vehiculo con el id " + request.getIdVehiculo() + "no existe");
        }

        if (corredorSeguro == null) {
            throw new IdCorredorSeguroNoEncontradoException("El corredor con el id " + request.getIdCorredorSeguro() + " no existe");
        }

        if (seguroRepository.existsByIdVehiculo(request.getIdVehiculo())) {
            throw new IdVehiculoDuplicadoException("El vehiculo ya esta registrado en otra venta");

        }

    Seguro seguro = Seguro.builder().
            precioSeguro(request.getPrecioSeguro()).
            tipoSeguro(request.getTipoSeguro())
            .idCliente(request.getIdCliente())
            .idVehiculo(request.getIdVehiculo())
            .idCorredorSeguro(request.getIdCorredorSeguro())
            .comisionSeguro(request.getComisionSeguro())
            .build();
    log.info("Seguro registrado");
    return seguroRepository.save(seguro);
    }

    //Put
    public Seguro actualizarSeguro(Integer idSeguro, SeguroRequest request) {
        log.info("Actualizando seguro con id {}", idSeguro);
        Seguro seguro = buscarSeguroPorId(idSeguro);
        if (seguroRepository.existsByIdVehiculoAndIdSeguroNot(request.getIdVehiculo(), idSeguro)) {
            throw new IdVehiculoDuplicadoException("El ID del seguro se encuentra asignado a otra venta ");
        }
        ClienteResponse cliente = webClientBuilder.build().get()
                .uri(clienteServiceUrl + "/api/v1/clientes/{idCliente}", request.getIdCliente())
                .retrieve().bodyToMono(ClienteResponse.class).block();
        VehiculoResponse vehiculo = webClientBuilder.build().get()
                .uri(vehiculoServiceUrl + "/api/v1/vehiculos/{idVehiculo}", request.getIdVehiculo())
                .retrieve().bodyToMono(VehiculoResponse.class).block();
        CorredorSeguroResponse corredorSeguro = webClientBuilder.build().get()
                .uri(corredorSeguroServiceUrl + "/api/v1/corredorSeguro/{id}", request.getIdCorredorSeguro())
                .retrieve().bodyToMono(CorredorSeguroResponse.class).block();


        // Validar que no sea nula la comunicación
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente con el id " + request.getIdCliente() + " no existe");
        }

        if (vehiculo == null) {
            throw new IdVehiculoNoEncontradoException("El vehiculo con el id " + request.getIdVehiculo() + "no existe");
        }

        if (corredorSeguro == null) {
            throw new IdCorredorSeguroNoEncontradoException("El corredor con el id " + request.getIdCorredorSeguro() + " no existe");
        }

        Seguro seguroActualizado = Seguro.builder().idSeguro(idSeguro).
                precioSeguro(request.getPrecioSeguro()).
                tipoSeguro(request.getTipoSeguro())
                .idCliente(request.getIdCliente())
                .idVehiculo(request.getIdVehiculo())
                .idCorredorSeguro(request.getIdCorredorSeguro())
                .comisionSeguro(request.getComisionSeguro())
                .build();
        return seguroRepository.save(seguroActualizado);
    }

    //Delete
    public void eliminarSeguro(Integer idSeguro){
        log.info("Eliminando seguro con id {}",idSeguro);
        Seguro seguro = buscarSeguroPorId(idSeguro);
        seguroRepository.delete(seguro);
    }

    public void simularError(){
        log.error("Se ejecuta el metodo para simular un error interno");
        throw new RuntimeException("Error simulado para pruebas ");
    }

}