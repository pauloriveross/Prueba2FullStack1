package cl.duoc.authservice.config;


import cl.duoc.authservice.model.Usuario;
import cl.duoc.authservice.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {
    @Bean
    public CommandLineRunner cargarDatos(UsuarioRepository repository){

        return args -> {
            if(repository.count()==0){
                Usuario admin = new Usuario(
                        null,"admin","admin1234","admin"
                );
                repository.save(admin);
            }





        };
    };
}

