package view;

import model.Person.Employee;
import Service.EmployeeService;
import Utils.Validation;
import java.util.ArrayList;
import repository.EmployeeRepository;

public class EmployeeView implements IEmployeeView {
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepo;

    public EmployeeView(EmployeeRepository employeeRepo,EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.employeeRepo = employeeRepo;
    }

    @Override
    public void display(ArrayList<Employee> employees) {
        if (employees == null || employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        for (Employee e : employees) {
           System.out.println(e);
       }
    }

    @Override
    public String getEmployeeID() {
        
        return null;
    }

    @Override
    public Employee getADetail() {
         String id;
        while (true) {
            id = Validation.checkStringCondition("Enter Employee ID (NV-YYYY): ", 
                                                 "Invalid format! ID must be in format NV-YYYY(YYYY must be number).", 
                                                 "NV-\\d{4}$");
            if (employeeService.findbyId(id) == null) break;
            System.out.println("Employee ID already exists! Please enter a different ID.");
        }

        String name = Validation.checkStringCondition("Enter Name: ", 
                                                      "Invalid name! Name must start with uppercase and contain only letters.", 
                                                      "^[A-Z][a-z]*(\\s[A-Z][a-z]*)*$");

        String dateOfBirth;
        while (true) {
            dateOfBirth = Validation.getValue("Enter Date of Birth (dd-MM-yyyy): ");
            if (Validation.checkValidImportDate(dateOfBirth) && Validation.validateAge(dateOfBirth)) {
                break;
            }
            System.out.println("Invalid date! You must be at least 18 years old.");
        }

        String gender = Validation.checkStringCondition("Enter Gender (Male/Female): ", 
                                                        "Invalid gender! Enter 'Male' or 'Female'.", 
                                                        "Male|Female");

        String idNumber = Validation.checkStringCondition("Enter ID Number: ", 
                                                          "Invalid ID! Must be 9 or 12 digits.", 
                                                          "\\d{9}|\\d{12}");

        String phoneNumber = Validation.checkStringCondition("Enter Phone Number: ", 
                                                             "Invalid phone number! Must start with 0 and contain 10 digits.", 
                                                             "0\\d{9}");

        String email = Validation.checkStringCondition("Enter Email: ", 
                                                       "Invalid email! Example: example@domain.com", 
                                                       "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

        String level = Validation.checkString("Enter Level: ", "Level cannot be empty!");

        String position = Validation.checkString("Enter Position: ", "Position cannot be empty!");

        double salary = Validation.checkDouble("Enter Salary: ", "Invalid salary! Please enter a positive number.", 0);
        
        Employee newEmployee = new Employee(id, name, dateOfBirth, gender, idNumber, phoneNumber, email, level, position, salary);
        return newEmployee;
    }
}
