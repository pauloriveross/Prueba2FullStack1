package duoc.service;

import duoc.dto.ClienteResponse;
import duoc.dto.TestDriveRequest;
import duoc.dto.VehiculoResponse;
import duoc.dto.VendedorResponse;
import duoc.exception.*;
import duoc.model.TestDrive;
import duoc.repository.TestDriveRepository;
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
public class TestDriveService {

    private final TestDriveRepository testDriveRepository;
    private final WebClient.Builder webClientBuilder;

    @Value("${services.cliente.url:http://CLIENTE}")
    private String clienteServiceUrl;
    @Value("${services.vehiculo.url:http://VEHICULO}")
    private String vehiculoServiceUrl;
    @Value("${services.vendedor.url:http://VENDEDOR}")
    private String vendedorServiceUrl;

    //Obtener TestDrive mediante Id
    public TestDrive buscarTestDrivePorId(Integer idTestDrive){
        log.info("Buscando test drive por ID {}",idTestDrive);
        return testDriveRepository.findById(idTestDrive).orElseThrow(()->
                new TestDriveNoEncotradoException("No se encontró un test drive con el id" + idTestDrive));
    }

    //Listar todos los TestDrives
    public List<TestDrive> listarTestDrive(){return testDriveRepository.findAll();}

    //Guardar TestDrive
    public TestDrive guardarTestDrive(TestDriveRequest request ,String token ) {


        if(testDriveRepository.existsByIdVehiculoAndFechaTestDrive(request.getIdVehiculo(), request.getFechaTestDrive())){
            throw new IdVehiculoDuplicadoException("El vehiculo ya esta asignado a otro test drive en esa fecha");
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
        VendedorResponse vendedor = webClientBuilder.build().get()
                .uri(vendedorServiceUrl + "/api/v1/vendedores/{id}", request.getIdVendedor())
                .header("Authorization",token)
                .retrieve().bodyToMono(VendedorResponse.class)
                .onErrorResume(e -> Mono.empty())
                .block();


        //Validar que no sea nula la comunicación
        if (cliente == null) {
            throw new IdClienteNoEncontradoException("El cliente con el id " + request.getIdCliente() + " no existe");
        }

        if (vehiculo == null) {
            throw new IdVehiculoNoEncontradoException("El vehiculo con el id " + request.getIdVehiculo() + "no existe");
        }

        if (vendedor == null) {
            throw new IdVendedorNoEncontradoException("El vendedor con el id " + request.getIdVendedor() + " no existe");
        }


        TestDrive testDrive = TestDrive.builder()
                .fechaTestDrive(request.getFechaTestDrive())
                .idCliente(cliente.idCliente())
                .idVehiculo(vehiculo.idVehiculo())
                .idVendedor(vendedor.idVendedor())
                .build();
        log.info("Test Drive registrado");
        return testDriveRepository.save(testDrive);
    }

    //Actualizar TestDrive
    public TestDrive actualizarTestDrive(Integer idTestDrive, TestDriveRequest request , String token) {
        log.info("Actualizando Test Drive con id {}", idTestDrive);
        TestDrive testDrive = buscarTestDrivePorId(idTestDrive);

        if(testDriveRepository.existsByIdVehiculoAndFechaTestDriveAndIdTestDriveNot(request.getIdVehiculo(), request.getFechaTestDrive(),idTestDrive)){
            throw new IdVehiculoDuplicadoException("El vehiculo ya esta asignado en otro test drive con esa fecha");
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
        VendedorResponse vendedor = webClientBuilder.build().get()
                .uri(vendedorServiceUrl + "/api/v1/vendedores/{id}", request.getIdVendedor())
                .header("Authorization",token)
                .retrieve().bodyToMono(VendedorResponse.class)
                .onErrorResume(e -> Mono.empty())
                .block();


        // Validar que no sea nula la comunicación
        if (cliente == null) {
            throw new IdClienteNoEncontradoException("El cliente con el id " + request.getIdCliente() + " no existe");
        }

        if (vehiculo == null) {
            throw new IdVehiculoNoEncontradoException("El vehiculo con el id " + request.getIdVehiculo() + "no existe");
        }

        if (vendedor == null) {
            throw new IdVendedorNoEncontradoException("El vendedor con el id " + request.getIdVendedor() + " no existe");
        }

        TestDrive testDriveActualizado = TestDrive.builder()
                .idTestDrive(testDrive.getIdTestDrive())
                .fechaTestDrive(request.getFechaTestDrive())
                .idCliente(cliente.idCliente())
                .idVehiculo(vehiculo.idVehiculo())
                .idVendedor(vendedor.idVendedor())
                .build();
        return testDriveRepository.save(testDriveActualizado);
    }

    //Eliminar Test Drive
    public void eliminarTestDrive(Integer idTestDrive){
        log.info("Eliminando Test Drive con id {}",idTestDrive);
        TestDrive testDrive = buscarTestDrivePorId(idTestDrive);
        testDriveRepository.delete(testDrive);
    }
}
