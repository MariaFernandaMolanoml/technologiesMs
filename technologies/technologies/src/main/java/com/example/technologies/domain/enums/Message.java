package com.example.technologies.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Message {

    INTERNAL_ERROR("500", "Something went wrong, please try again", ""),
    INTERNAL_ERROR_IN_ADAPTERS("PRC501", "An error occurred in the adapters, please try again", ""),
    INVALID_REQUEST("400", "Invalid request, please check your data", ""),
    INVALID_PARAMETERS(INVALID_REQUEST.getCode(), "Invalid parameters, please check your data", ""),
    INVALID_NAME("403", "Invalid name, please check", "name"),
    INVALID_DESCRIPTION("403", "Invalid description, please check", "description"),
    NAME_TOO_LONG("403", "Name cannot exceed 50 characters", "name"),
    DESCRIPTION_TOO_LONG("403", "Description cannot exceed 90 characters", "description"),
    UNSUPPORTED_OPERATION("501", "Unsupported operation, please try again", ""),
    TECHNOLOGY_CREATED("201", "Technology successfully created", ""),
    ADAPTER_RESPONSE_NOT_FOUND("404-0", "Invalid name, please check", ""),
    TECHNOLOGIES_EMPTY("200", "No technologies registered", ""),
    TECHNOLOGIES_FOUND("200", "List of technologies retrieved successfully", ""),
    TECHNOLOGY_ALREADY_EXISTS("400", "Technology is already registered", "");

    private final String code;
    private final String message;
    private final String param;
}
