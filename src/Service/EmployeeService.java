package Service;

import model.Person.Employee;
import repository.IEmployeeRepository;
import repository.EmployeeRepository;
import view.EmployeeView;
import Utils.Validation;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.reflect.Field;

public class EmployeeService implements IEmployeeService {
    private IEmployeeRepository employeeRepository = new EmployeeRepository();
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Employee> employees;
    private EmployeeView employeeView;
    
    public EmployeeService() {
        this.employeeView = employeeView;
        employees = employeeRepository.readFile();
        if (employees == null) {
            employees = new ArrayList<>();
        }
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }
    
    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = (employees != null) ? employees : new ArrayList<>();
    }

    @Override
    public Employee findbyId(String id) {
        if (id == null || id.isEmpty()) return null;
        return employees.stream()
                        .filter(e -> e.getId().equals(id))
                        .findFirst()
                        .orElse(null);
    }

    @Override
    public void display() {
        employeeView.display(employees);
    }

    @Override
    public void add(Employee entity) {
        if (entity instanceof Employee employee) {
            if (findbyId(employee.getId()) != null) {
                System.out.println("Employee ID already exists. Cannot add duplicate.");
                return;
            }
            employees.add(employee);
            save();
            System.out.println("Employee added successfully!");
        } else {
            System.out.println("Invalid entity type!");
        }
    }

    @Override
    public void save() {
        employeeRepository.writeFile(employees);
        System.out.println("Employee data saved successfully.");
    }

    public void updateEmployee() {
        String id = Utils.Validation.checkString("Enter Id Name: ", "Invalid!");
        Employee employee = findbyId(id);
        if (employee == null) {
            System.out.println("Employee not found!");
            return;
        }

        System.out.println("Current Employee details: \n" + employee);
        Field[] fields = employee.getClass().getDeclaredFields();

        boolean updating = true;
        while (updating) {
            System.out.println("Select a field to update:");
            for (int i = 0; i < fields.length; i++) {
                System.out.println((i + 1) + ". " + fields[i].getName());
            }
            System.out.println("0. Exit update");

            int choice;
            try {
                choice = Integer.parseInt(Validation.getValue("Enter your choice: "));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            if (choice == 0) {
                System.out.println("Exiting update mode.");
                break;
            }

            if (choice < 1 || choice > fields.length) {
                System.out.println("Invalid choice. Please select from the list.");
                continue;
            }

            Field fieldToUpdate = fields[choice - 1];
            fieldToUpdate.setAccessible(true);
            String newValue = Validation.getValue("Enter new value for " + fieldToUpdate.getName() + ": ");

            try {
                switch (fieldToUpdate.getName()) {
                    case "id":
                        if (Validation.validateEmployeeId(newValue) && findbyId(newValue) == null) {
                            fieldToUpdate.set(employee, newValue);
                        } else {
                            System.out.println("Invalid or duplicate Employee ID.");
                        }
                        break;
                    case "name":
                        if (Validation.validateName(newValue)) {
                            fieldToUpdate.set(employee, newValue);
                        } else {
                            System.out.println("Invalid name format.");
                        }
                        break;
                    case "dateOfBirth":
                        if (Validation.validateAge(newValue)) {
                            fieldToUpdate.set(employee, newValue);
                        } else {
                            System.out.println("Invalid date of birth. Must be 18 or older.");
                        }
                        break;
                    case "idNumber":
                        if (Validation.validateIdNumber(newValue)) {
                            fieldToUpdate.set(employee, newValue);
                        } else {
                            System.out.println("Invalid ID number.");
                        }
                        break;
                    case "phoneNumber":
                        if (Validation.validatePhoneNumber(newValue)) {
                            fieldToUpdate.set(employee, newValue);
                        } else {
                            System.out.println("Invalid phone number.");
                        }
                        break;
                    case "salary":
                        try {
                            double newSalary = Double.parseDouble(newValue);
                            if (Validation.validateSalary(newSalary)) {
                                fieldToUpdate.set(employee, newSalary);
                            } else {
                                System.out.println("Invalid salary amount.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid salary format.");
                        }
                        break;
                    default:
                        fieldToUpdate.set(employee, newValue);
                        break;
                }
                System.out.println("Updated " + fieldToUpdate.getName() + " successfully.");
            } catch (Exception e) {
                System.out.println("Failed to update " + fieldToUpdate.getName() + ": " + e.getMessage());
            }

            String continueUpdate;
            while (true) {
                continueUpdate = Validation.getValue("Do you want to update another field? (yes/no): ");
                if (continueUpdate.equalsIgnoreCase("yes") || continueUpdate.equalsIgnoreCase("no")) {
                    break;
                } else {
                    System.out.println("Invalid input! Please enter 'yes' or 'no'.");
                }
            }
            if (continueUpdate.equalsIgnoreCase("no")) {
                updating = false;
            }
        }
        save();
    }

}
