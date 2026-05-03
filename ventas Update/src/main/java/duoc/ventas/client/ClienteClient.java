package duoc.ventas.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

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
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Cliente no encontrado no se puede generar venta")))
                .bodyToMono(Map.class)
                .block();
    }
}
