package repository;

import model.Person.Customer;
import repository.ICustomerRepository;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerRepository implements ICustomerRepository {
    private static final String CUSTOMER_FILE_PATH = "data/customer.csv";
    List<Customer> customers = new ArrayList<>();

    @Override
    public void displayCustomer() {
        customers = readFile();  
        if (customers.isEmpty()) {
            System.out.println("No customers available.");
        } else {
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        }
    }


    @Override
    public void addCustomer() {
    }

    @Override
    public void updateCustomer() {
    }

    
  @Override
    public void writeFile(Object entities) {
        if (entities instanceof List<?>) {
            List<?> list = (List<?>) entities;
            if (!list.isEmpty() && list.get(0) instanceof Customer) {
                writeFile((List<Customer>) list);
            }
        }
    }

    // 
    private void writeFile(List<Customer> customers) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CUSTOMER_FILE_PATH))) {
            for (Customer customer : customers) {
                
                String csvLine = String.join(",", customer.getId(), customer.getName(), 
                                              customer.getDateOfBirth(), customer.getGender(), 
                                              customer.getIdNumber(), customer.getPhoneNumber(), 
                                              customer.getEmail(), customer.getAddress(), 
                                              customer.getCustomerType());
                bw.write(csvLine);
                bw.newLine(); 
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    
    
    
    
    @Override
    public List<Customer> readFile() {
        List<Customer> customers = new ArrayList<>();
        File file = new File(CUSTOMER_FILE_PATH);

        if (!file.exists()) {
            System.out.println("File not found: " + CUSTOMER_FILE_PATH);
            return customers;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length != 9) { 
                    System.out.println("Invalid data format: " + line);
                    continue; 
                }
                
                data[0] = data[0].replace("\"", "").trim();
                
                SimpleDateFormat inputFormat1 = new SimpleDateFormat("d/M/yyyy");
                SimpleDateFormat inputFormat2 = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date date = inputFormat1.parse(data[2]);
                    data[2] = outputFormat.format(date);
                } catch (ParseException e) {
                    try {
                        Date date = inputFormat2.parse(data[2]);
                        data[2] = outputFormat.format(date);
                    } catch (ParseException ex) {
                        System.out.println("Invalid date format: " + data[2]);
                    }
                }

                customers.add(new Customer(data[0], data[1], data[2], data[3], data[4], 
                                           data[5], data[6], data[7], data[8]));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return customers;
    }

   public List<Customer> getAllCustomers() {
        if (customers.isEmpty()) {
            customers = readFile();  
        }
        return customers; 
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);  
        writeFile(customers);  // Ghi lại danh sách mới vào file
    }

   

}
