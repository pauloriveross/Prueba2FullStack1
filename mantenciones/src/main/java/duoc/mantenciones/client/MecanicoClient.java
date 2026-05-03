package duoc.mantenciones.client;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class MecanicoClient {

    private final WebClient webClient;

    public MecanicoClient(@Value("${mecanico-service.url}") String mecanicosServidor){
        this.webClient = WebClient.builder().baseUrl(mecanicosServidor).build();
    }

    public Map<String, Object> obtenerMecanicoId(Integer id,String token){
        return this.webClient.get()
                .uri("/{id}", id)
                .header("Authorization", token)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Mecanico no encontrado no se puede realizar mantencion")))
                .bodyToMono(Map.class)
                .block();
    }
}