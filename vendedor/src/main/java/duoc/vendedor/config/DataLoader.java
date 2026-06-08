package duoc.vendedor.config;


import duoc.vendedor.model.Vendedor;
import duoc.vendedor.repository.VendedorRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner cargarDatos (VendedorRepository repository){
        return args -> {
            if (repository.count() == 0){
                repository.save(Vendedor.builder()
                        .rutVendedor("11.222.333-4")
                        .nombreVendedor("Kim ")
                        .apellidoVendedor("Jiwoo")
                        .seccionVendedor("Ventas")
                        .turnoVendedor("Tarde")
                        .sueldoBaseVendedor(550000)
                        .emailVendedor("jiwookim@automotora.cl")
                        .build());

                Faker faker = new Faker();
                for (int i = 0; i < 10; i++){

                    String nombre = faker.name().firstName();
                    String apellido = faker.name().lastName();
                    String correo = nombre.toLowerCase() + "."+ apellido.toLowerCase() + "@automotora.cl";
                    repository.save(Vendedor.builder()
                            .rutVendedor(faker.idNumber().valid())
                            .nombreVendedor(nombre)
                            .apellidoVendedor(apellido)
                            .seccionVendedor(faker.options().option("Ventas Vehiculos","Repuestos"))
                            .turnoVendedor(faker.options().option("Tarde","Mañana","Rotativo"))
                            .sueldoBaseVendedor(faker.number().numberBetween(520000,1200000))
                            .emailVendedor(correo)
                            .build());


                    }




            }

        };
    }
}
