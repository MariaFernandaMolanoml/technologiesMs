package com.example.technologies.domain.spi;

import com.example.technologies.domain.model.Technology;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface ITechnologyPersistencePort {
    Mono<Technology> save(Technology technology);
    Mono<Boolean> existsByName(String name);
    Flux<Technology> findByIds(List<UUID> ids);
    Mono<Technology> findById(UUID id);
}
