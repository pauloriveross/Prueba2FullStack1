package duoc.ventas.client;

import java.util.Map;

import duoc.ventas.exception.IdVehiculoNoEncontradoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class VehiculoClient {

    private final WebClient webClient;

    public VehiculoClient(@Value("${vehiculo-service.url}") String vehiculoServidor){
        this.webClient = WebClient.builder().baseUrl(vehiculoServidor).build();
    }

    public Map<String, Object> obtenerVehiculoId(Integer id, String token){
        return this.webClient.get()
                .uri("/{id}", id)
                .header("Authorization", token)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response ->
                        Mono.error(new IdVehiculoNoEncontradoException("Vehículo con ID " + id + " no existe")))
                .onStatus(status -> status.is5xxServerError(), response ->
                        Mono.error(new RuntimeException("Error de comunicación con el servicio de Vehículos")))
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }


}
