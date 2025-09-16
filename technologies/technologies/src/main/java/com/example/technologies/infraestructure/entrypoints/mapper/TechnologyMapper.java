package com.example.technologies.infraestructure.entrypoints.mapper;

import com.example.technologies.domain.model.Technology;
import com.example.technologies.infraestructure.entrypoints.dto.TechnologyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TechnologyMapper {
    Technology dtoToModel(TechnologyDTO dto);
    TechnologyDTO modelToDto(Technology model);
}



