package br.com.atlas.atlas_logistics.adapters.web.OpenApi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.webmvc.ui.SwaggerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    OpenAPI customConfiguration(){
        return new OpenAPI().info(new Info().title("Atlas Logistics API").version("v1").description("Otimizando o fluxo global de mercadorias com inteligência e precisão."+
                "A Atlas Logistics API é uma interface de programação de alto desempenho projetada para conectar ecossistemas de e-commerce, armazéns e transportadoras. Nossa solução centraliza a complexidade da cadeia de suprimentos em um único ponto de integração, permitindo que empresas foquem no crescimento enquanto cuidamos da movimentação.")
                .termsOfService("https://github.com/GabrielFavaroo"));
    }


}
