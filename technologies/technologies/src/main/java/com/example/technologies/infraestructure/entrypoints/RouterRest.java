package com.example.technologies.infraestructure.entrypoints;

import com.example.technologies.infraestructure.entrypoints.handler.TechnologyHandlerImpl;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class RouterRest {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/technology",
                    beanClass = TechnologyHandlerImpl.class,
                    beanMethod = "createTechnology"
            )
    })
    public RouterFunction<ServerResponse> technologyRoutes(TechnologyHandlerImpl handler) {
        return RouterFunctions.route(POST("/technology"), handler::createTechnology)
                .andRoute(POST("/technologies/validate"), handler::validateTechnologies)
                .andRoute(GET("/technologies/{id}"), handler::getTechnologyById);
    }
}
