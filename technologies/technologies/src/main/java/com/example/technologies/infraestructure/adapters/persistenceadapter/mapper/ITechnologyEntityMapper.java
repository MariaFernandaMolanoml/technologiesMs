package com.example.technologies.infraestructure.adapters.persistenceadapter.mapper;

import com.example.technologies.domain.model.Technology;
import com.example.technologies.infraestructure.adapters.persistenceadapter.entity.TechnologyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ITechnologyEntityMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    Technology toModel(TechnologyEntity entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    TechnologyEntity toEntity(Technology technology);
}
