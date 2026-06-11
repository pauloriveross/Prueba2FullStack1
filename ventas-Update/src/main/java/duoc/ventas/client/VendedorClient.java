package duoc.ventas.client;

import java.util.Map;

import duoc.ventas.exception.IdVendedorNoEncontradoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class VendedorClient {

    private final WebClient webClient;

    public VendedorClient(@Value("${vendedor-service.url}") String vendedorServidor){
        this.webClient = WebClient.builder().baseUrl(vendedorServidor).build();
    }

    public Map<String, Object> obtenerVendedorId(Integer id,String token){
        return this.webClient.get()
                .uri("/{id}", id)
                .header("Authorization", token)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response ->
                        Mono.error(new IdVendedorNoEncontradoException("Vendedor con ID " + id + " no existe")))
                .onStatus(status -> status.is5xxServerError(), response ->
                        Mono.error(new RuntimeException("Error de comunicación con el servicio de Vendedores")))
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }
}
