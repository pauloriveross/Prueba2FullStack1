package duoc.ventas.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

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
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Vendedor no encontrado no se puede generar venta")))
                .bodyToMono(Map.class)
                .block();
    }
}
