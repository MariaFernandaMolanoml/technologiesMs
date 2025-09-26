package com.example.technologies.infraestructure.entrypoints;

import com.example.technologies.infraestructure.entrypoints.dto.TechnologyDTO;
import com.example.technologies.infraestructure.entrypoints.handler.TechnologyHandlerImpl;
import com.example.technologies.infraestructure.entrypoints.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
                    beanMethod = "createTechnology",
                    operation = @Operation(
                            operationId = "createTechnology",
                            summary = "Crear tecnología",
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "Datos de la tecnología a crear",
                                    required = true,
                                    content = @Content(
                                            schema = @Schema(implementation = TechnologyDTO.class)
                                    )
                            ),
                            responses = {
                                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                                            responseCode = "201",
                                            description = "Tecnología creada",
                                            content = @Content(schema = @Schema(implementation = TechnologyDTO.class))
                                    ),
                                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                                            responseCode = "400",
                                            description = "Error de validación",
                                            content = @Content(schema = @Schema(implementation = ApiResponse.class))
                                    ),
                                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                                            responseCode = "500",
                                            description = "Error interno",
                                            content = @Content(schema = @Schema(implementation = ApiResponse.class))
                                    )
                            }
                    )
            ),

            @RouterOperation(
                    path = "/technologies/validate",
                    beanClass = TechnologyHandlerImpl.class,
                    beanMethod = "validateTechnologies",
                    operation = @Operation(
                            operationId = "validateTechnologies",
                            summary = "Validar IDs de tecnologías",
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "Lista de IDs de tecnologías a validar",
                                    required = true,
                                    content = @Content(
                                            array = @ArraySchema(
                                                    schema = @Schema(type = "string", format = "uuid",
                                                            example = "550e8400-e29b-41d4-a716-446655440000")
                                            )
                                    )
                            ),
                            responses = {
                                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                                            responseCode = "200",
                                            description = "Validación correcta",
                                            content = @Content(array = @ArraySchema(
                                                    schema = @Schema(implementation = TechnologyDTO.class)
                                            ))
                                    ),
                                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                                            responseCode = "500",
                                            description = "Error interno",
                                            content = @Content(schema = @Schema(implementation = ApiResponse.class))
                                    )
                            }
                    )
            ),

            @RouterOperation(
                    path = "/technologies/{id}",
                    beanClass = TechnologyHandlerImpl.class,
                    beanMethod = "getTechnologyById",
                    operation = @Operation(
                            operationId = "getTechnologyById",
                            summary = "Obtener tecnología por ID",
                            parameters = {
                                    @Parameter(
                                            name = "id",
                                            description = "ID de la tecnología",
                                            required = true,
                                            example = "550e8400-e29b-41d4-a716-446655440000"
                                    )
                            },
                            responses = {
                                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                                            responseCode = "200",
                                            description = "Tecnología encontrada",
                                            content = @Content(schema = @Schema(implementation = TechnologyDTO.class))
                                    ),
                                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                                            responseCode = "404",
                                            description = "No encontrada"
                                    ),
                                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                                            responseCode = "500",
                                            description = "Error interno",
                                            content = @Content(schema = @Schema(implementation = ApiResponse.class))
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> technologyRoutes(TechnologyHandlerImpl handler) {
        return RouterFunctions.route(POST("/technology"), handler::createTechnology)
                .andRoute(POST("/technologies/validate"), handler::validateTechnologies)
                .andRoute(GET("/technologies/{id}"), handler::getTechnologyById);
    }
}
