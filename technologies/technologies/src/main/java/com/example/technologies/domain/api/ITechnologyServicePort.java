package com.example.technologies.domain.api;

import com.example.technologies.domain.model.Technology;
import reactor.core.publisher.Mono;

public interface ITechnologyServicePort {
    Mono<Technology> registerTechnology(Technology technology);
}