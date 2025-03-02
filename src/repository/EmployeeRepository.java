package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import model.Person.Employee;


import java.util.List;

public class EmployeeRepository implements IEmployeeRepository {
    private static final String EMPLOYEE_FILE_PATH = "data/employee.csv";
    List<Employee> employees = new ArrayList<>();

   @Override
    public void writeFile(ArrayList<Employee> employees) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(EMPLOYEE_FILE_PATH))) {
            for (Employee employee : employees) {
                bw.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%.2f",
                    employee.getId().trim(), employee.getName().trim(), employee.getDateOfBirth().trim(),
                    employee.getGender().trim(), employee.getIdNumber().trim(), employee.getPhoneNumber().trim(),
                    employee.getEmail().trim(), employee.getLevel().trim(), employee.getPosition().trim(),
                    employee.getSalary()
                ));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public ArrayList<Employee> readFile() {
        employees.clear();

        java.io.File file = new java.io.File(EMPLOYEE_FILE_PATH);
        if (!file.exists()) {
            System.out.println("File not found: " + EMPLOYEE_FILE_PATH);
            return new ArrayList<>();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(EMPLOYEE_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("\"", "").trim(); 
                String[] data = line.split("\\s*,\\s*"); 

                employees.add(new Employee(
                    data[0], data[1], data[2], data[3], data[4], 
                    data[5], data[6], data[7], data[8], 
                    Double.parseDouble(data[9])
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (ArrayList<Employee>) employees;
    }


    
}