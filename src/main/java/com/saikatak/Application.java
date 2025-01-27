package com.saikatak;

import com.saikatak.service.OrganizationAnalyzer;
import com.saikatak.util.CsvReader;

public class Application {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Please provide the path to the CSV file");
            System.exit(1);
        }
        
        try {
            var employees = CsvReader.readEmployees(args[0]);
            var analyzer = new OrganizationAnalyzer(employees);
            
            System.out.println("""
                
                Salary Analysis:
                ---------------""");
            analyzer.analyzeSalaries()
                .forEach(System.out::println);
            
            System.out.println("""
                
                Reporting Line Analysis:
                ----------------------""");
            analyzer.analyzeLongReportingLines()
                .forEach(System.out::println);
            
        } catch (Exception e) {
            System.err.println("Error processing file: " + e.getMessage());
            System.exit(1);
        }
    }
}
