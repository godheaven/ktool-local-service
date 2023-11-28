package cl.kanopus;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SpringBootApplication
@EnableCaching
public class MyApplication {

    public static final Marker FATAL = MarkerFactory.getMarker("FATAL");
    public static final String TAG_PRINTER = "printer";

    /*
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }*/
    @Bean
    public OpenAPI openAPI() {
        String securitySchemeName = "Bearer Authentication";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(AUTHORIZATION)
                                                .type(SecurityScheme.Type.APIKEY)
                                                .in(SecurityScheme.In.HEADER)
                                                .bearerFormat("JWT")
                                )
                )
                .addTagsItem(new Tag().name(TAG_PRINTER).description("Set of operations related to communication with printers."))
                .info(new Info().title("Kanopus Local Services API")
                        .description("API that allows to integrate a web application with local services of a computer")
                        .contact(new Contact().name("Pablo Diaz Saavedra").url("www.kanopus.cl").email("pabloandres.diazsaavedra@gmail.com"))
                        .version("1.0.0"));
    }

}
