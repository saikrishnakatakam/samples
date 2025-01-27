# Employee Organization Analyzer

A Java application that analyzes organizational structure to identify salary discrepancies and reporting line issues within a company.

## Overview

This application processes employee data from a CSV file to identify:
- Managers who earn less than 20% of their subordinates' average salary
- Managers who earn more than 150% of their subordinates' average salary
- Employees who have more than 4 managers in their reporting line

## Requirements

- Java 17 or higher
- Maven 3.6 or higher

## Input File Format

The application expects a CSV file with the following structure:

```csv
Id,firstName,lastName,salary,managerId
123,Joe,Doe,60000,
124,Martin,Chekov,45000,123
125,Bob,Ronstad,47000,123
```
 - First line must be the header
 - Fields are comma-separated
 - managerId is empty for CEO
 - All other fields are required

## Building and Running
### Build the project:
```bash
mvn clean package
```

### Run the application:
```bash
java -jar target/employee-analyzer-1.0-SNAPSHOT.jar path/to/employees.csv
```

## Example Output
```text
Salary Analysis:
---------------
Steve (ID: 124) earns 5000.00 less than minimum required
Brady (ID: 125) earns 2000.00 more than maximum allowed

Reporting Line Analysis:
----------------------
Alice (ID: 456) has 5 managers (exceeds maximum by 1)
```
## Testing
Run the tests using:
```bash
mvn test
```

