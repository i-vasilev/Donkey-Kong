package ru.rsreu.sebah.view;

import ru.rsreu.sebah.model.Entity;

public interface ObjectListener<E extends Entity> {
    void handle(E object, ObjectEventType type);
}
