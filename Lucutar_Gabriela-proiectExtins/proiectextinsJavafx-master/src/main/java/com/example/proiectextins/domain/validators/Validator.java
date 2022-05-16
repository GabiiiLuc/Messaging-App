package com.example.proiectextins.domain.validators;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}