package com.saikatak.service;

import com.saikatak.model.Employee;
import com.saikatak.model.SalaryAnalysis;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrganizationAnalyzerTest {
    @Test
    void testSalaryAnalysis() {
        var employees = List.of(
            new Employee(1, "CEO", "Boss", 100000, null),
            new Employee(2, "Manager", "One", 50000, 1),
            new Employee(3, "Employee", "One", 40000, 2)
        );
        
        var analyzer = new OrganizationAnalyzer(employees);
        var results = analyzer.analyzeSalaries();
        
        assertFalse(results.isEmpty());
        assertTrue(results.stream()
            .anyMatch(analysis -> analysis.manager().id() == 1));
    }
    
    @Test
    void testReportingLineAnalysis() {
        var employees = List.of(
            new Employee(1, "CEO", "Boss", 100000, null),
            new Employee(2, "Manager1", "One", 80000, 1),
            new Employee(3, "Manager2", "Two", 70000, 2),
            new Employee(4, "Manager3", "Three", 60000, 3),
            new Employee(5, "Manager4", "Four", 50000, 4),
            new Employee(6, "Employee", "Five", 40000, 5),
            new Employee(7, "Employee2", "Six", 40000, 5)
        );
        
        var analyzer = new OrganizationAnalyzer(employees);
        var results = analyzer.analyzeLongReportingLines();
        
        assertFalse(results.isEmpty());
        assertTrue(results.stream()
            .anyMatch(analysis -> analysis.employee().id() == 7));
    }
}
