package com.example.loandecision.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private final String entity;
    private final String field;
    private final String value;

    public EntityNotFoundException(String entity, String field, String value) {
        super(String.format("Could not find entity %s where %s=%s", entity, field, value));
        this.entity = entity;
        this.field = field;
        this.value = value;
    }
}
