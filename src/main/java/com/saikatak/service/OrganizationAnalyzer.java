package com.saikatak.service;

import com.saikatak.model.*;
import java.util.*;

public class OrganizationAnalyzer {
    private static final double MIN_SALARY_RATIO = 1.20;
    private static final double MAX_SALARY_RATIO = 1.50;
    private static final int MAX_REPORTING_LINE = 4;
    
    private final Map<Integer, Employee> employeeMap;
    private final Map<Integer, List<Employee>> subordinatesMap;
    
    public OrganizationAnalyzer(List<Employee> employees) {
        this.employeeMap = employees.stream()
            .collect(HashMap::new, (map, emp) -> map.put(emp.id(), emp), HashMap::putAll);
            
        this.subordinatesMap = employees.stream()
            .filter(emp -> emp.managerId() != null)
            .collect(HashMap::new,
                (map, emp) -> map.computeIfAbsent(emp.managerId(), k -> new ArrayList<>()).add(emp),
                HashMap::putAll);
    }
    
    public List<SalaryAnalysis> analyzeSalaries() {
        System.out.println("subordinatesMap: " + subordinatesMap);
        return employeeMap.values().stream()
            .filter(manager -> subordinatesMap.containsKey(manager.id()))
            .map(this::analyzeSalaryForManager)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();
    }
    
    private Optional<SalaryAnalysis> analyzeSalaryForManager(Employee manager) {
        var subordinates = subordinatesMap.get(manager.id());
        if (subordinates == null || subordinates.isEmpty()) {
            return Optional.empty();
        }
        System.out.println("subordinates: " + subordinates);
        double avgSubordinateSalary = subordinates.stream()
            .mapToDouble(Employee::salary)
            .average()
            .orElse(0.0);
        System.out.println("avgSubordinateSalary: " + avgSubordinateSalary);
        double minRequired = avgSubordinateSalary * MIN_SALARY_RATIO;
        double maxAllowed = avgSubordinateSalary * MAX_SALARY_RATIO;
        System.out.println("minRequired: " + minRequired + ", maxAllowed: " + maxAllowed + ", manager salary: "+manager.salary());
        if (manager.salary() < minRequired) {
            return Optional.of(new SalaryAnalysis(
                manager,
                minRequired - manager.salary(),
                SalaryAnalysis.SalaryIssueType.UNDERPAID
            ));
        } else if (manager.salary() > maxAllowed) {
            return Optional.of(new SalaryAnalysis(
                manager,
                manager.salary() - maxAllowed,
                SalaryAnalysis.SalaryIssueType.OVERPAID
            ));
        }
        
        return Optional.empty();
    }
    
    public List<ReportingLineAnalysis> analyzeLongReportingLines() {
        return employeeMap.values().stream()
            .map(this::analyzeReportingLine)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();
    }
    
    private Optional<ReportingLineAnalysis> analyzeReportingLine(Employee employee) {
        int lineLength = calculateReportingLineLength(employee);
        return lineLength > MAX_REPORTING_LINE
            ? Optional.of(new ReportingLineAnalysis(employee, lineLength, lineLength - MAX_REPORTING_LINE))
            : Optional.empty();
    }
    
    private int calculateReportingLineLength(Employee employee) {
        return generateReportingChain(employee).size() - 1;
    }
    
    private List<Employee> generateReportingChain(Employee employee) {
        var chain = new ArrayList<Employee>();
        var current = employee;
        
        while (current != null) {
            chain.add(current);
            current = current.managerId() != null ? employeeMap.get(current.managerId()) : null;
        }
        
        return chain;
    }
}
