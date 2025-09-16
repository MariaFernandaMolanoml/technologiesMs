package com.example.technologies.infraestructure.adapters.persistenceadapter.repository;

import com.example.technologies.infraestructure.adapters.persistenceadapter.entity.TechnologyEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TechnologyRepository extends ReactiveCrudRepository<TechnologyEntity, UUID> {
    Mono<TechnologyEntity> findByName(String name);
}
