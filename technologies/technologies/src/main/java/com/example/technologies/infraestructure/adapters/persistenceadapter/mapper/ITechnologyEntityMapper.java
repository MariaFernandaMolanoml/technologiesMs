package com.example.technologies.infraestructure.adapters.persistenceadapter.mapper;

import com.example.technologies.domain.model.Technology;
import com.example.technologies.infraestructure.adapters.persistenceadapter.entity.TechnologyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ITechnologyEntityMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    Technology toModel(TechnologyEntity entity);
    TechnologyEntity toEntity(Technology technology);
}
