
package view;

import Service.CustomerService;
import Utils.Validation;
import java.util.ArrayList;
import model.Person.Customer;
import repository.CustomerRepository;

public class CustomerView implements ICustomerView {

    CustomerRepository cusRepo;
    CustomerService cusSer;

    public CustomerView(CustomerRepository cusRepo, CustomerService cusSer) {
        this.cusRepo = cusRepo;
        this.cusSer = cusSer;
    }

    @Override
    public void display(ArrayList<Customer> customers) {
        if (customers.isEmpty()) {
           System.out.println("List is empty.");
           return;
       }
       for (Customer c : customers) {
           System.out.println(c);
       }
    }

    @Override
    public Customer getADetail() {
        String id;
        while (true) {
            id = Validation.checkStringCondition("Enter Customer ID (KH-YYYY): ", 
                                                 "Invalid format! ID must be in format KH-YYYY (YYYY must be numbers).", 
                                                 "KH-\\d{4}$");
            if (cusSer.findbyId(id) == null) break;
            System.out.println("Customer ID already exists! Please enter a different ID.");
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

        String customerType = Validation.checkString("Enter Customer Type: ", "Customer Type cannot be empty!");
        String address = Validation.checkString("Enter Address: ", "Address cannot be empty!");

        Customer newCustomer = new Customer(id, name, dateOfBirth, gender, idNumber, phoneNumber, email, customerType, address);
        return newCustomer;
    }
    
    @Override
    public String getEmployeeId() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
