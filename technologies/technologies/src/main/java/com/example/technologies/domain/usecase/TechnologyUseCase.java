package com.example.technologies.domain.usecase;

import com.example.technologies.domain.api.ITechnologyServicePort;
import com.example.technologies.domain.constants.Constants;
import com.example.technologies.domain.enums.Message;
import com.example.technologies.domain.exceptions.DomainException;
import com.example.technologies.domain.model.Technology;
import com.example.technologies.domain.spi.ITechnologyPersistencePort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public class TechnologyUseCase implements ITechnologyServicePort {

    private final ITechnologyPersistencePort persistencePort;

    public TechnologyUseCase(ITechnologyPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public Mono<Technology> registerTechnology(Technology technology) {
        String nameLower = technology.name().toLowerCase();

        Technology newTechnology = new Technology(
                null,
                nameLower,
                technology.description()
        );

        return validateTechnologyFields(newTechnology)
                .flatMap(validTech ->
                        persistencePort.existsByName(validTech.name())
                                .filter(exists -> !exists)
                                .switchIfEmpty(Mono.error(new DomainException(Message.TECHNOLOGY_ALREADY_EXISTS)))
                                .flatMap(exists -> persistencePort.save(validTech))
                );
    }

    @Override
    public Flux<Technology> findByIds(List<UUID> ids) {
        return persistencePort.findByIds(ids);
    }

    private Mono<Technology> validateTechnologyFields(Technology technology) {
        if (technology.name() == null || technology.name().isBlank()) {
            return Mono.error(new DomainException(Message.INVALID_NAME));
        }
        if (technology.name().length() > Constants.MAX_NAME_LENGTH) {
            return Mono.error(new DomainException(Message.NAME_TOO_LONG));
        }
        if (technology.description() == null || technology.description().isBlank()) {
            return Mono.error(new DomainException(Message.INVALID_DESCRIPTION));
        }
        if (technology.description().length() > Constants.MAX_DESCRIPTION_LENGTH) {
            return Mono.error(new DomainException(Message.DESCRIPTION_TOO_LONG));
        }
        return Mono.just(technology);
    }
    @Override
    public Mono<Technology> findById(UUID id) {
        return persistencePort.findById(id);
    }
}
