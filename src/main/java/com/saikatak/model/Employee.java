package com.saikatak.model;

public record Employee(
    int id,
    String firstName,
    String lastName,
    double salary,
    Integer managerId
) {
    @Override
    public String toString() {
        return "%s %s (ID: %d)".formatted(firstName, lastName, id);
    }
}
