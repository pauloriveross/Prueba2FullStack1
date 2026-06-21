package duoc.seguro.service;


import duoc.seguro.dto.ClienteResponse;
import duoc.seguro.dto.CorredorSeguroResponse;
import duoc.seguro.dto.SeguroRequest;
import duoc.seguro.dto.VehiculoResponse;
import duoc.seguro.exception.*;
import duoc.seguro.model.Seguro;
import duoc.seguro.repository.SeguroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

    @Value("${services.corredor-seguro.url:http://CORREDORSEGURO}")
    private String corredorSeguroServiceUrl;

    //Get todos
    public List<Seguro> listarSeguro(){return seguroRepository.findAll();}

    //Get con ID
    public Seguro buscarSeguroPorId(Integer idSeguro){
        log.info("Buscando seguro por ID {}",idSeguro);
        return seguroRepository.findById(idSeguro).orElseThrow(()->
            new SeguroNoEncontradoException("No se encontró un seguro con el id " + idSeguro));
    }

    //Post
    public Seguro guardarSeguro(SeguroRequest request, String token) {

        ClienteResponse cliente = webClientBuilder.build().get()
                .uri(clienteServiceUrl + "/api/v1/clientes/{idCliente}", request.getIdCliente())
                .header("Authorization",token)
                .retrieve().bodyToMono(ClienteResponse.class)
                .onErrorResume(e -> Mono.empty())
                .block();
        VehiculoResponse vehiculo = webClientBuilder.build().get()
                .uri(vehiculoServiceUrl + "/api/v1/vehiculos/{idVehiculo}", request.getIdVehiculo())
                .header("Authorization",token)
                .retrieve().bodyToMono(VehiculoResponse.class)
                .onErrorResume(e -> Mono.empty())
                .block();
        CorredorSeguroResponse corredorSeguro = webClientBuilder.build().get()
                .uri(corredorSeguroServiceUrl + "/api/v1/corredores/{id}", request.getIdCorredorSeguro())
                .header("Authorization",token)
                .retrieve().bodyToMono(CorredorSeguroResponse.class)
                .onErrorResume(e -> Mono.empty())
                .block();


        // Validar que no sea nula la comunicación
        if (cliente == null) {
            throw new IdClienteNoEncontradoException("El cliente con el id " + request.getIdCliente() + " no existe");
        }

        if (vehiculo == null) {
            throw new IdVehiculoNoEncontradoException("El vehiculo con el id " + request.getIdVehiculo() + "no existe");
        }

        if (corredorSeguro == null) {
            throw new IdCorredorSeguroNoEncontradoException("El corredor con el id " + request.getIdCorredorSeguro() + " no existe");
        }

        if (seguroRepository.existsByIdVehiculo(request.getIdVehiculo())) {
            throw new IdVehiculoDuplicadoException("El vehiculo ya esta registrado en otro seguro ");

        }

            Seguro seguro = Seguro.builder().
            precioSeguro(request.getPrecioSeguro()).
            tipoSeguro(request.getTipoSeguro())
            .idCliente(cliente.idCliente())
            .idVehiculo(vehiculo.idVehiculo())
            .idCorredorSeguro(corredorSeguro.idCorredor())
            .comisionSeguro(request.getComisionSeguro())
            .build();
            log.info("Registrando seguro");
            return seguroRepository.save(seguro);
    }

    //Put
    public Seguro actualizarSeguro(Integer idSeguro, SeguroRequest request,String token) {
        log.info("Actualizando seguro con id {}", idSeguro);
        Seguro seguro = buscarSeguroPorId(idSeguro);
        if (seguroRepository.existsByIdVehiculoAndIdSeguroNot(request.getIdVehiculo(), idSeguro)) {
            throw new IdVehiculoDuplicadoException("El ID del seguro se encuentra asignado a otro seguro  ");
        }
        ClienteResponse cliente = webClientBuilder.build().get()
                .uri(clienteServiceUrl + "/api/v1/clientes/{idCliente}", request.getIdCliente())
                .header("Authorization",token)
                .retrieve().bodyToMono(ClienteResponse.class)
                .onErrorResume(e -> Mono.empty())
                .block();
        VehiculoResponse vehiculo = webClientBuilder.build().get()
                .uri(vehiculoServiceUrl + "/api/v1/vehiculos/{idVehiculo}", request.getIdVehiculo())
                .header("Authorization",token)
                .retrieve().bodyToMono(VehiculoResponse.class)
                .onErrorResume(e -> Mono.empty())
                .block();
        CorredorSeguroResponse corredorSeguro = webClientBuilder.build().get()
                .uri(corredorSeguroServiceUrl + "/api/v1/corredores/{id}", request.getIdCorredorSeguro())
                .header("Authorization",token)
                .retrieve().bodyToMono(CorredorSeguroResponse.class)
                .onErrorResume(e -> Mono.empty())
                .block();


        // Validar que no sea nula la comunicación
        if (cliente == null) {
            throw new IdClienteNoEncontradoException("El cliente con el id " + request.getIdCliente() + " no existe");
        }

        if (vehiculo == null) {
            throw new IdVehiculoNoEncontradoException("El vehiculo con el id " + request.getIdVehiculo() + " no existe");
        }

        if (corredorSeguro == null) {
            throw new IdCorredorSeguroNoEncontradoException("El corredor con el id " + request.getIdCorredorSeguro() + " no existe");
        }

        Seguro seguroActualizado = Seguro.builder().idSeguro(idSeguro).
                precioSeguro(request.getPrecioSeguro()).
                tipoSeguro(request.getTipoSeguro())
                .idCliente(cliente.idCliente())
                .idVehiculo(vehiculo.idVehiculo())
                .idCorredorSeguro(corredorSeguro.idCorredor())
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