package duoc.mantenciones.client;
import java.util.Map;

import duoc.mantenciones.exception.IdMecanicoNoEncontradoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
                .onStatus(status -> status.is4xxClientError(), response ->
                        Mono.error(new IdMecanicoNoEncontradoException("Mecánico con ID " + id + " no existe")))
                .onStatus(status -> status.is5xxServerError(), response ->
                        Mono.error(new RuntimeException("Error de comunicación con el servicio de Mecánicos")))
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }
}