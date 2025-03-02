package Service;

import java.util.Scanner;
import repository.CustomerRepository;
import repository.ICustomerRepository;
import Utils.Validation;
import java.util.ArrayList;
import java.util.List;
import model.Person.Customer;
import view.CustomerView;

public class CustomerService implements ICustomerService {
    private ICustomerRepository customerRepository = new CustomerRepository();
    private Scanner scanner = new Scanner(System.in);
    private List<Customer> customerList;
    private ArrayList<Customer> customers;
    static Validation val = new Validation();
    private CustomerView customerView; 
//    
    public CustomerService(){
        customers = (ArrayList<Customer>) customerRepository.readFile();
    }
// 
    public ArrayList<Customer> getCustomers() {
        return customers;
    }
//    
    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }
// 
    public void editCustomer() {
        String id = Validation.getValue("Enter Customer ID to edit: ").trim();

        Customer customer = findbyId(id);
        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }

        System.out.println("Editing Customer: " + customer);

        String newCustomerType = Validation.checkString("Enter new Customer Type (or press Enter to skip): ", "Customer Type cannot be empty!");
        if (!newCustomerType.isEmpty()) {
            customer.setCustomerType(newCustomerType);
        }

        String newAddress = Validation.checkString("Enter new Address (or press Enter to skip): ", "Address cannot be empty!");
        if (!newAddress.isEmpty()) {
            customer.setAddress(newAddress);
        }

        System.out.println("Updated Customer: " + customer);
        System.out.println("Customer data has been updated in customer.csv");
    }
//    
    @Override
    public void save() {
        customerRepository.writeFile(customers);
    }
//    
    @Override
    public void display() {
        if (customers.isEmpty()) {
           System.out.println("List is empty.");
           return;
       }
       for (Customer c : customers) {
           System.out.println(c);
       }
    }
//
    @Override
    public void add(Customer entity) {
        if (entity instanceof Customer employee) {
            if (findbyId(employee.getId()) != null) {
                System.out.println("Employee ID already exists. Cannot add duplicate.");
                return;
            }
            customers.add(employee);
            save();
            System.out.println("Employee added successfully!");
        } else {
            System.out.println("Invalid entity type!");
        }
    }
//    
    @Override
    public Customer findbyId(String id) {
        if (customers == null || customers.isEmpty()) {  
            System.out.println("Customer list is empty!");
            return null;
        }

        for (Customer cust : customers) {
            if (cust.getId().equalsIgnoreCase(id.trim())) {
                return cust;
            }
        }
        return null;
    }

}
