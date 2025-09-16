package com.example.technologies.infraestructure.adapters.persistenceadapter;

import com.example.technologies.domain.model.Technology;
import com.example.technologies.domain.spi.ITechnologyPersistencePort;
import com.example.technologies.infraestructure.adapters.persistenceadapter.mapper.ITechnologyEntityMapper;
import com.example.technologies.infraestructure.adapters.persistenceadapter.repository.TechnologyRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

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

}
