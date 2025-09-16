package com.example.technologies.domain.spi;

import com.example.technologies.domain.model.Technology;
import reactor.core.publisher.Mono;

public interface ITechnologyPersistencePort {
    Mono<Technology> save(Technology technology);
    Mono<Boolean> existsByName(String name);
}
