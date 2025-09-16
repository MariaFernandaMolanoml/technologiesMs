package com.example.technologies.application;

import com.example.technologies.domain.api.ITechnologyServicePort;
import com.example.technologies.domain.spi.ITechnologyPersistencePort;
import com.example.technologies.domain.usecase.TechnologyUseCase;
import com.example.technologies.infraestructure.adapters.persistenceadapter.TechnologyPersistenceAdapter;
import com.example.technologies.infraestructure.adapters.persistenceadapter.mapper.ITechnologyEntityMapper;
import com.example.technologies.infraestructure.adapters.persistenceadapter.repository.TechnologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UseCasesConfig {

    private final TechnologyRepository technologyRepository;
    private final ITechnologyEntityMapper technologyEntityMapper;

    @Bean
    public ITechnologyPersistencePort technologyPersistencePort() {
        return new TechnologyPersistenceAdapter(technologyRepository, technologyEntityMapper);
    }

    @Bean
    public ITechnologyServicePort technologyServicePort(ITechnologyPersistencePort technologyPersistencePort) {
        return new TechnologyUseCase(technologyPersistencePort);
    }
}
