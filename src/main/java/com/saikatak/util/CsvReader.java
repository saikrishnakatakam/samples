package com.saikatak.util;

import com.saikatak.model.Employee;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class CsvReader {
    public static List<Employee> readEmployees(String filePath) throws IOException {
        try (Stream<String> lines = Files.lines(Path.of(filePath))) {
            return lines
                .skip(1)
                .map(CsvReader::parseLine)
                .toList();
        }
    }
    
    private static Employee parseLine(String line) {
        var values = line.split(",");
        return new Employee(
            Integer.parseInt(values[0]),
            values[1],
            values[2],
            Double.parseDouble(values[3]),
            values[4].isEmpty() ? null : Integer.parseInt(values[4])
        );
    }
}
