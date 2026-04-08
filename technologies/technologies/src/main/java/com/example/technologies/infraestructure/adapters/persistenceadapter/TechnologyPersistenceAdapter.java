package com.example.technologies.infraestructure.adapters.persistenceadapter;

import com.example.technologies.domain.model.Technology;
import com.example.technologies.domain.spi.ITechnologyPersistencePort;
import com.example.technologies.infraestructure.adapters.persistenceadapter.mapper.ITechnologyEntityMapper;
import com.example.technologies.infraestructure.adapters.persistenceadapter.repository.TechnologyRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class TechnologyPersistenceAdapter implements ITechnologyPersistencePort {

    private final TechnologyRepository technologyRepository;
    private final ITechnologyEntityMapper technologyEntityMapper;

    @Override
    public Mono<Technology> save(Technology technology) {
        return technologyRepository.save(technologyEntityMapper.toEntity(technology))
                .map(technologyEntityMapper::toModel);
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return technologyRepository.findByName(name)
                .map(technologyEntityMapper::toModel)
                .map(t -> true)
                .defaultIfEmpty(false);
    }
    @Override
    public Flux<Technology> findByIds(List<UUID> ids) {
        return technologyRepository.findAllById(ids)
                .map(technologyEntityMapper::toModel);
    }
    @Override
    public Mono<Technology> findById(UUID id) {
        return technologyRepository.findById(id)
                .map(technologyEntityMapper::toModel);
    }

}
