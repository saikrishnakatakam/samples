package com.saikatak.model;

public record ReportingLineAnalysis(
    Employee employee,
    int lineLength,
    int excess
) {
    @Override
    public String toString() {
        return "%s has %d managers (exceeds maximum by %d)".formatted(
            employee, lineLength, excess
        );
    }
}
