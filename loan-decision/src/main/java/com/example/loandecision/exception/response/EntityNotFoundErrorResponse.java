package com.example.loandecision.exception.response;

import com.example.loandecision.exception.EntityNotFoundException;
import lombok.Getter;

@Getter
public class EntityNotFoundErrorResponse {
    private final String entity;
    private final String field;
    private final String value;

    public EntityNotFoundErrorResponse(EntityNotFoundException e) {
        this.entity = e.getEntity();
        this.field = e.getField();
        this.value = e.getValue();
    }
}
