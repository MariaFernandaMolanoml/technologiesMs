package com.example.technologies;

import com.example.technologies.domain.enums.Message;
import com.example.technologies.domain.exceptions.DomainException;
import com.example.technologies.domain.model.Technology;
import com.example.technologies.domain.spi.ITechnologyPersistencePort;
import com.example.technologies.domain.usecase.TechnologyUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TechnologyUseCaseTest {

    private ITechnologyPersistencePort persistencePort;
    private TechnologyUseCase useCase;

    @BeforeEach
    void setUp() {
        persistencePort = Mockito.mock(ITechnologyPersistencePort.class);
        useCase = new TechnologyUseCase(persistencePort);
    }

    @Test
    void registerTechnology_success() {
        Technology tech = new Technology(null, "WebFlux", "Framework reactivo");

        when(persistencePort.existsByName("webflux")).thenReturn(Mono.just(false));
        when(persistencePort.save(any(Technology.class)))
                .thenReturn(Mono.just(new Technology(UUID.randomUUID(), "webflux", "Framework reactivo")));

        StepVerifier.create(useCase.registerTechnology(tech))
                .expectNextMatches(t -> t.name().equals("webflux"))
                .verifyComplete();
    }

    @Test
    void registerTechnology_alreadyExists() {
        Technology tech = new Technology(null, "Spring Boot", "Framework Java");

        when(persistencePort.existsByName("spring boot")).thenReturn(Mono.just(true));

        StepVerifier.create(useCase.registerTechnology(tech))
                .expectErrorMatches(e -> e instanceof DomainException &&
                        ((DomainException) e).getMessage().equals(Message.TECHNOLOGY_ALREADY_EXISTS.getMessage()))
                .verify();
    }

    @Test
    void registerTechnology_invalidName() {
        Technology tech = new Technology(null, "   ", "desc");

        StepVerifier.create(useCase.registerTechnology(tech))
                .expectErrorMatches(e -> e instanceof DomainException &&
                        ((DomainException) e).getMessage().equals(Message.INVALID_NAME.getMessage()))
                .verify();
    }

    @Test
    void registerTechnology_nameTooLong() {
        String longName = "a".repeat(300);
        Technology tech = new Technology(null, longName, "desc");

        StepVerifier.create(useCase.registerTechnology(tech))
                .expectErrorMatches(e -> e instanceof DomainException &&
                        ((DomainException) e).getMessage().equals(Message.NAME_TOO_LONG.getMessage()))
                .verify();
    }

    @Test
    void registerTechnology_invalidDescription() {
        Technology tech = new Technology(null, "java", "   ");

        StepVerifier.create(useCase.registerTechnology(tech))
                .expectErrorMatches(e -> e instanceof DomainException &&
                        ((DomainException) e).getMessage().equals(Message.INVALID_DESCRIPTION.getMessage()))
                .verify();
    }

    @Test
    void registerTechnology_descriptionTooLong() {
        String longDesc = "x".repeat(600);
        Technology tech = new Technology(null, "java", longDesc);

        StepVerifier.create(useCase.registerTechnology(tech))
                .expectErrorMatches(e -> e instanceof DomainException &&
                        ((DomainException) e).getMessage().equals(Message.DESCRIPTION_TOO_LONG.getMessage()))
                .verify();
    }

    @Test
    void findById_success() {
        UUID id = UUID.randomUUID();
        Technology tech = new Technology(id, "docker", "contenedores");

        when(persistencePort.findById(id)).thenReturn(Mono.just(tech));

        StepVerifier.create(useCase.findById(id))
                .expectNext(tech)
                .verifyComplete();
    }

    @Test
    void findByIds_success() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        Technology t1 = new Technology(id1, "docker", "contenedores");
        Technology t2 = new Technology(id2, "kubernetes", "orquestador");

        when(persistencePort.findByIds(List.of(id1, id2)))
                .thenReturn(Flux.just(t1, t2));

        StepVerifier.create(useCase.findByIds(List.of(id1, id2)))
                .expectNext(t1)
                .expectNext(t2)
                .verifyComplete();
    }
}
