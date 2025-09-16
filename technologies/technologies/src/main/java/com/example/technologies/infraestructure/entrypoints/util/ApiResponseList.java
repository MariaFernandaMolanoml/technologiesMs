package com.example.technologies.infraestructure.entrypoints.util;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ApiResponseList<T> {
    private String code;
    private String message;
    private String date;
    private List<T> data;
}
