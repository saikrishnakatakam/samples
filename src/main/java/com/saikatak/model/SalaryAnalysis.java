package com.saikatak.model;

public record SalaryAnalysis(
    Employee manager,
    double difference,
    SalaryIssueType issueType
) {
    public enum SalaryIssueType {
        UNDERPAID, OVERPAID
    }
    
    @Override
    public String toString() {
        return switch (issueType) {
            case UNDERPAID -> "%s earns %.2f less than minimum required".formatted(manager, difference);
            case OVERPAID -> "%s earns %.2f more than maximum allowed".formatted(manager, difference);
        };
    }
}
