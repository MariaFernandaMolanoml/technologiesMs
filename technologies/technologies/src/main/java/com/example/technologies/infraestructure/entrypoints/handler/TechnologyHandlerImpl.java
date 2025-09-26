package com.example.technologies.infraestructure.entrypoints.handler;

import com.example.technologies.domain.api.ITechnologyServicePort;
import com.example.technologies.domain.enums.Message;
import com.example.technologies.domain.exceptions.DomainException;
import com.example.technologies.infraestructure.entrypoints.dto.TechnologyDTO;
import com.example.technologies.infraestructure.entrypoints.mapper.TechnologyMapper;
import com.example.technologies.infraestructure.entrypoints.util.ApiResponse;
import com.example.technologies.infraestructure.entrypoints.util.ErrorDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.example.technologies.infraestructure.entrypoints.util.Constants.TECHNOLOGY_ERROR;

@Component
@RequiredArgsConstructor
@Slf4j
public class TechnologyHandlerImpl {

    private final ITechnologyServicePort servicePort;
    private final TechnologyMapper mapper;

    public Mono<ServerResponse> createTechnology(ServerRequest request) {
        return request.bodyToMono(TechnologyDTO.class)
                .flatMap(dto -> servicePort.registerTechnology(mapper.dtoToModel(dto)))
                .flatMap(saved -> ServerResponse.status(HttpStatus.CREATED)
                        .bodyValue(mapper.modelToDto(saved)))
                .doOnSuccess(success -> log.info("Technology created successfully"))
                .doOnError(ex -> log.error(TECHNOLOGY_ERROR, ex))
                .onErrorResume(DomainException.class, ex -> buildErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        ex.getMessage(),
                        List.of(ErrorDTO.builder()
                                .code(ex.getCode())
                                .message(ex.getMessage())
                                .param(ex.getParam())
                                .build())))
                .onErrorResume(ex -> buildErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        Message.INTERNAL_ERROR.getMessage(),
                        List.of(ErrorDTO.builder()
                                .code(Message.INTERNAL_ERROR.getCode())
                                .message(Message.INTERNAL_ERROR.getMessage())
                                .build())));
    }

    public Mono<ServerResponse> validateTechnologies(ServerRequest request) {
        return request.bodyToFlux(UUID.class)
                .collectList()
                .flatMapMany(servicePort::findByIds)
                .collectList()
                .flatMap(techs -> ServerResponse.ok().bodyValue(techs))
                .doOnSuccess(success -> log.info("Validated technologies successfully"))
                .doOnError(ex -> log.error(TECHNOLOGY_ERROR, ex))
                .onErrorResume(ex -> buildErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        Message.INTERNAL_ERROR.getMessage(),
                        List.of(ErrorDTO.builder()
                                .code(Message.INTERNAL_ERROR.getCode())
                                .message(Message.INTERNAL_ERROR.getMessage())
                                .build())));
    }
    private Mono<ServerResponse> buildErrorResponse(HttpStatus status, String message, List<ErrorDTO> errors) {
        ApiResponse response = ApiResponse.builder()
                .code(String.valueOf(status.value()))
                .message(message)
                .date(Instant.now().toString())
                .errors(errors)
                .build();
        return ServerResponse.status(status).bodyValue(response);
    }
    public Mono<ServerResponse> getTechnologyById(ServerRequest request) {
        UUID id = UUID.fromString(request.pathVariable("id"));
        return servicePort.findById(id)
                .flatMap(tech -> ServerResponse.ok().bodyValue(tech))
                .switchIfEmpty(buildErrorResponse(
                        HttpStatus.NOT_FOUND,
                        "Tecnología no encontrada con id " + id,
                        List.of(ErrorDTO.builder()
                                .code("NOT_FOUND")
                                .message("La tecnología con el id especificado no existe")
                                .param(id.toString())
                                .build())
                ))
                .doOnSuccess(success -> log.info("Fetched technology with id {}", id))
                .doOnError(ex -> log.error(TECHNOLOGY_ERROR, ex))
                .onErrorResume(ex -> buildErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        Message.INTERNAL_ERROR.getMessage(),
                        List.of(ErrorDTO.builder()
                                .code(Message.INTERNAL_ERROR.getCode())
                                .message(Message.INTERNAL_ERROR.getMessage())
                                .build())));
    }

}
