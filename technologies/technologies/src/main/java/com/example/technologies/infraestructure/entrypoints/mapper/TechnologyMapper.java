package com.example.technologies.infraestructure.entrypoints.mapper;

import com.example.technologies.domain.model.Technology;
import com.example.technologies.infraestructure.entrypoints.dto.TechnologyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TechnologyMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    Technology dtoToModel(TechnologyDTO dto);

    TechnologyDTO modelToDto(Technology model);
}



