package duoc.ventas.client;

import java.util.Map;

import duoc.ventas.exception.IdClienteNoEncontradoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ClienteClient {

    private final WebClient webClient;

    public ClienteClient(@Value("${clientes-service.url}") String clientesServidor){
        this.webClient = WebClient.builder().baseUrl(clientesServidor).build();
    }

    public Map<String, Object> obtenerClienteId(Integer id,String token){
        return this.webClient.get()
                .uri("/{id}", id)
                .header("Authorization", token)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response ->
                        Mono.error(new IdClienteNoEncontradoException("Cliente con ID " + id + " no existe")))
                .onStatus(status -> status.is5xxServerError(), response ->
                        Mono.error(new RuntimeException("Error de comunicación con el servicio de Clientes")))
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }
}
