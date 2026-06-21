package duoc.mantenciones.config;

import duoc.mantenciones.model.Mantencion;
import duoc.mantenciones.repository.MantencionRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;


@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner cargarDatos(MantencionRepository mantencionRepository) {
        return args -> {
            if (mantencionRepository.count() == 0) {

                mantencionRepository.save(Mantencion.builder()
                        .fechaMantencion(new Date())
                        .precioMantencion(85000)
                        .tipoMantencion("Cambio de Pastillas de Freno")
                        .idVehiculo(1)
                        .idMecanico(1)
                        .idCliente(1)
                        .build()
                );

                Faker faker = new Faker();

                for (int i = 0; i < 10; i++) {
                    long diasAtras = faker.number().numberBetween(1, 30);
                    // Resta esos días de la fecha actual
                    Date fechaAleatoria = new Date(System.currentTimeMillis() - (diasAtras * 24 * 60 * 60 * 1000));

                    mantencionRepository.save(Mantencion.builder()
                            .fechaMantencion(fechaAleatoria)
                            .precioMantencion(faker.number().numberBetween(20000, 300000))
                            .tipoMantencion(faker.options().option(
                                    "Cambio de Aceite",
                                    "Alineación y Balanceo",
                                    "Revisión de 10.000 KM",
                                    "Mantención de sistema Eléctrico"
                            ))
                            .idVehiculo(faker.number().numberBetween(1, 11))
                            .idMecanico(faker.number().numberBetween(1, 11))
                            .idCliente(faker.number().numberBetween(1, 11))
                            .build()
                    );
                }
            }
        };
    }
}
