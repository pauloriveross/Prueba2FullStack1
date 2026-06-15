package duoc.cliente;

import duoc.cliente.exception.ClienteNoEncontradoException;
import duoc.cliente.model.Clientes;
import duoc.cliente.repository.ClientesRepository;
import duoc.cliente.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTestV2 {

    @Mock
    private ClientesRepository repository;

    @InjectMocks
    private ClienteService service;

    @Test
    void buscarPorId_debeRetornarRegistroCuandoExiste() {
        Clientes entidad = new Clientes();
        entidad.setIdCliente(1);
        when(repository.findById(1)).thenReturn(Optional.of(entidad));

        Clientes resultado = service.buscarPorId(1);

        assertNotNull(resultado,"EL resultado no puede ser nulo");
        assertEquals(1, resultado.getIdCliente());
        verify(repository, times(1)).findById(1);
    }

    @Test
    void buscarPorId_debeLanzarExcepcionCuandoNoExiste() {
        when(repository.findById(99)).thenReturn(Optional.empty());
        assertThrows(ClienteNoEncontradoException.class, () -> service.buscarPorId(99));
        verify(repository, times(1)).findById(99);
    }
}
