package com.example.technologies.domain.api;

import com.example.technologies.domain.model.Technology;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface ITechnologyServicePort {
    Mono<Technology> registerTechnology(Technology technology);
    Flux<Technology> findByIds(List<UUID> ids);
    Mono<Technology> findById(UUID id);
}