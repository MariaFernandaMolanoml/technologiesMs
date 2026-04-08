package com.example.technologies.infraestructure.entrypoints.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para tecnología")
public class TechnologyDTO {
    @Schema(description = "ID de la tecnología", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;
    @Schema(description = "Nombre de la tecnología", example = "Spring Boot")
    private String name;
    @Schema(description = "Descripción de la tecnología", example = "Framework para microservicios")
    private String description;
}
