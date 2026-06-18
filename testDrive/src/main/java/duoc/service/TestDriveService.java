package duoc.service;

import duoc.dto.ClienteResponse;
import duoc.dto.TestDriveRequest;
import duoc.dto.VehiculoResponse;
import duoc.dto.VendedorResponse;
import duoc.exception.IdVehiculoDuplicadoException;
import duoc.exception.IdVehiculoNoEncontradoException;
import duoc.exception.IdVendedorNoEncontradoException;
import duoc.exception.TestDriveNoEncotradoException;
import duoc.model.TestDrive;
import duoc.repository.TestDriveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
    public TestDrive guardarTestDrive(TestDriveRequest request) {

        ClienteResponse cliente = webClientBuilder.build().get()
                .uri(clienteServiceUrl + "/api/v1/clientes/{idCliente}", request.getIdCliente())
                .retrieve().bodyToMono(ClienteResponse.class).block();
        VehiculoResponse vehiculo = webClientBuilder.build().get()
                .uri(vehiculoServiceUrl + "/api/v1/vehiculos/{idVehiculo}", request.getIdVehiculo())
                .retrieve().bodyToMono(VehiculoResponse.class).block();
        VendedorResponse vendedor = webClientBuilder.build().get()
                .uri(vendedorServiceUrl + "/api/v1/vendedores/{id}", request.getIdVendedor())
                .retrieve().bodyToMono(VendedorResponse.class).block();


        // Validación comunicación con clases adyacentes
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente con el id " + request.getIdCliente() + " no existe");
        }

        if (vehiculo == null) {
            throw new IdVehiculoNoEncontradoException("El vehiculo con el id " + request.getIdVehiculo() + "no existe");
        }

        if (vendedor == null) {
            throw new IdVendedorNoEncontradoException("El vendedor con el id " + request.getIdVendedor() + " no existe");
        }

        if(testDriveRepository.existsByIdVehiculo(request.getIdVehiculo())){
            throw new IdVehiculoDuplicadoException("El vehiculo ya esta registrado en otro Test Drive");

        }

        TestDrive testDrive = TestDrive.builder().
                fechaTestDrive(request.getFechaTestDrive())
                .idCliente(request.getIdCliente())
                .idVehiculo(request.getIdVehiculo())
                .idVendedor(request.getIdVendedor())
                .build();
        log.info("Test Drive registrado");
        return testDriveRepository.save(testDrive);
    }

    //Actualizar TestDrive
    public TestDrive actualizarTestDrive(Integer idTestDrive, @NonNull TestDriveRequest request) {
        log.info("Actualizando Test Drive con id {}", idTestDrive);
        TestDrive testDrive = buscarTestDrivePorId(idTestDrive);
        ClienteResponse cliente = webClientBuilder.build().get()
                .uri(clienteServiceUrl + "/api/v1/clientes/{idCliente}", request.getIdCliente())
                .retrieve().bodyToMono(ClienteResponse.class).block();
        VehiculoResponse vehiculo = webClientBuilder.build().get()
                .uri(vehiculoServiceUrl + "/api/v1/vehiculos/{idVehiculo}", request.getIdVehiculo())
                .retrieve().bodyToMono(VehiculoResponse.class).block();
        VendedorResponse vendedor = webClientBuilder.build().get()
                .uri(vendedorServiceUrl + "/api/v1/vendedores/{id}", request.getIdVendedor())
                .retrieve().bodyToMono(VendedorResponse.class).block();


        // Validar que no sea nula la comunicación
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente con el id " + request.getIdCliente() + " no existe");
        }

        if (vehiculo == null) {
            throw new IdVehiculoNoEncontradoException("El vehiculo con el id " + request.getIdVehiculo() + "no existe");
        }

        if (vendedor == null) {
            throw new IdVendedorNoEncontradoException("El vendedor con el id " + request.getIdVendedor() + " no existe");
        }

        TestDrive testDriveActualizado = TestDrive.builder().
                fechaTestDrive(request.getFechaTestDrive())
                .idCliente(request.getIdCliente())
                .idVehiculo(request.getIdVehiculo())
                .idVendedor(request.getIdVendedor())
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
