package duoc.vehiculo.config;

import duoc.vehiculo.model.Vehiculo;
import duoc.vehiculo.repository.VehiculoRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner cargarDatos(VehiculoRepository vehiculoRepository) {
        return args -> {
            if (vehiculoRepository.count() == 0) {
                vehiculoRepository.save(Vehiculo.builder()
                        .marcaVehiculo("Daewoo")
                        .modeloVehiculo("Racer")
                        .annioVehiculo(2000)
                        .tipoVehiculo("Sedán")
                        .precioVehiculo(100000)
                        .kilometrajeVehiculo(1000000)
                        .patenteVehiculo("HTTP01")
                        .estadoVehiculo("Usado")
                        .build()
                );
                Faker faker = new Faker();
                for (int i = 0; i < 10; i++) {
                    vehiculoRepository.save(Vehiculo.builder()
                            .marcaVehiculo(faker.vehicle().manufacturer())
                            .modeloVehiculo(faker.vehicle().model())
                            .annioVehiculo(faker.number().numberBetween(1980,2027))
                            .tipoVehiculo(faker.vehicle().carType())
                            .precioVehiculo(faker.number().numberBetween(100000,20000000))
                            .kilometrajeVehiculo(faker.number().numberBetween(0,250000))
                            .patenteVehiculo(faker.vehicle().licensePlate())
                            .estadoVehiculo(faker.options().option("New", "Used", "Refurbished", "Like new"))
                            .build());

                }
            }
        };
    }
}
