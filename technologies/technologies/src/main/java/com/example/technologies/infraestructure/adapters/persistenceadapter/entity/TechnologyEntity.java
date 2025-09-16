package com.example.technologies.infraestructure.adapters.persistenceadapter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("technologies")
public class TechnologyEntity {
    @Id
    private UUID id;

    @Column("name")
    private String name;

    @Column("description")
    private String description;
}
