package duoc.cliente;

import duoc.cliente.dto.ClienteRequest;
import duoc.cliente.model.Clientes;
import duoc.cliente.repository.ClientesRepository;
import duoc.cliente.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(properties = {
        "spring.cloud.discovery.enabled=false",
        "eureka.client.enabled=false"
})
@ActiveProfiles("test")
public class ClienteServiceTest {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClientesRepository clientesRepository;

    @Test
    void cuandoGuardarCliente_entoncesRetornaClienteConId() {
        ClienteRequest request = new ClienteRequest();

        Clientes guardado = clienteService.guardarCliente(request);

        assertNotNull(guardado);
        assertNotNull(guardado.getIdCliente());
    }

    @Test
    void cuandoBuscarPorIdExistente_entoncesRetornaCliente() {
        ClienteRequest request = new ClienteRequest();
        Clientes guardado = clienteService.guardarCliente(request);

        Clientes encontrado = clienteService.buscarPorId(guardado.getIdCliente());

        assertNotNull(encontrado);
        assertEquals(guardado.getIdCliente(), encontrado.getIdCliente());
    }

    @Test
    void cuandoListarTodos_entoncesRetornaListaDeClientes() {
        List<Clientes> lista = clienteService.listarTodos();

        assertNotNull(lista);
        assertTrue(lista.size() >= 0);
    }

    @Test
    void cuandoEliminarCliente_entoncesMetodoEjecutaCorrectamente() {
        ClienteRequest request = new ClienteRequest();
        Clientes guardado = clienteService.guardarCliente(request);
        Integer id = guardado.getIdCliente();

        assertDoesNotThrow(() -> clienteService.eliminarCliente(id));
    }

    @Test
    void cuandoSimularError_entoncesLanzaExcepcion() {
        assertThrows(RuntimeException.class, () -> {
            clienteService.simularError();
        });
    }
}