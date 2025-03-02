package view;

import model.Booking;
import model.Facility.Facility;
import repository.FacilityRepository;
import repository.CustomerRepository;
import model.Person.Customer;
import Utils.Validation;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import repository.BookingRepository;

public class BookingView implements IBookingView {

    private final Scanner scanner = new Scanner(System.in);
    private final CustomerRepository customerRepository = new CustomerRepository();
    private final FacilityRepository facilityRepository = new FacilityRepository();
    private static int lastBookingId = 0;

    public Booking getADetail() {
        String customerId = getCustomerIdFromUser();   
        String serviceId = getServiceIdFromUser();  
        LocalDate startDate = getDateFromUser("Enter start date (dd-MM-yyyy): ");  
        LocalDate endDate = getEndDateFromUser(startDate);  

        String bookingId = generateUniqueBookingId();
        double cost = getServiceCost(serviceId);

        if (cost == 0.0) {
            System.out.println("Invalid service ID or no cost found for the selected service.");
            return null;  
        }

        int numberOfDays = calculateNumberOfDays(startDate, endDate);
        double totalPayment = cost * numberOfDays;

        System.out.println("Total Payment: " + totalPayment);

        double deposit = Validation.checkDouble("Enter deposit amount (must be at least 20% of total payment): ", 
                "Invalid deposit amount. Please enter a valid number.", 0);

        // nhập 20% của total hoặc lớn hơn với trả trước
        while (deposit < totalPayment * 0.2) {
            System.out.println("Deposit must be at least 20% of total payment.");
            deposit = Validation.checkDouble("Enter deposit amount (must be at least 20% of total payment): ", 
                    "Invalid deposit amount. Please enter a valid number.", 0);
        }

        return new Booking(bookingId, customerId, serviceId, startDate, endDate, totalPayment, deposit);
    }

    private String generateUniqueBookingId() {
        String bookingId;
        do {
            lastBookingId++;
            bookingId = String.format("BOOK-%d", lastBookingId);
        } while (isBookingIdExist(bookingId));

        return bookingId;
    }

    private boolean isBookingIdExist(String bookingId) {
        for (Booking booking : BookingRepository.getAllBookings()) {
            if (booking.getId().equals(bookingId)) {
                return true;
            }
        }
        return false;
    }

    private double getServiceCost(String serviceId) {
        switch (serviceId) {
            case "SVVL-1010":
                return 10.0;  // Giá cho Luxury Villa
            case "SVHO-2000":
                return 5.0;  // Giá cho Sky House
            case "SVRO-1919":
                return 1.0;  // Giá cho Bar Room
            case "SVVL-1234":
                return 12.0;  // Giá cho Sakura Villa
            case "SVRO-9999":
                return 3.0;  // Giá cho Chill Room
            case "SVHO-7777":
                return 8.0;  // Giá cho Vina House
            default:
                return 0.0;  // Nếu không có dịch vụ, trả về 0
        }
    }


    private int calculateNumberOfDays(LocalDate startDate, LocalDate endDate) {
        return (int) java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
    }

    public String getCustomerIdFromUser() {
        System.out.println("=== Customer List ===");
        customerRepository.displayCustomer();

        String customerId;
        while (true) {
            customerId = Validation.checkStringCondition("Enter Customer ID: ", 
                "Invalid format! ID must be in format KH-YYYY (YYYY must be numbers).", 
                "KH-\\d{4}$");
            if (findCustomerById(customerId) != null) break;
            System.out.println("Customer ID not found! Please choose a valid Customer ID from the list.");
        }
        return customerId;
    }

    public String getServiceIdFromUser() {
        System.out.println("=== Service List ===");
        for (Facility facility : facilityRepository.getFacilityList().keySet()) {
            System.out.println(facility.getServiceCode() + " - " + facility.getServiceName()+" - Cost :"+facility.getRentalCost());
        }

        String serviceId;
        while (true) {
            serviceId = Validation.checkStringCondition("Enter Service ID (SVXX-YYYY): ", 
                "Invalid format! ID must be in format SVXX-YYYY (YYYY must be numbers).", 
                "SV(RO|VL|HO)-\\d{4}$");
            if (checkServiceType(serviceId)) break;
            System.out.println("Invalid service type! Please select from the correct service list.");
        }
        return serviceId;
    }

    public LocalDate getDateFromUser(String prompt) {
        LocalDate date;
        while (true) {
            System.out.print(prompt);
            String dateInput = scanner.nextLine();
            try {
                date = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                break;
            } catch (Exception e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }
        return date;
    }

    public LocalDate getEndDateFromUser(LocalDate startDate) {
        LocalDate endDate;
        while (true) {
            System.out.print("Enter end date (dd-MM-yyyy): ");
            String dateInput = scanner.nextLine();

            if (!Validation.checkValidImportDate(dateInput)) {
                System.out.println("Invalid date format. Please use dd-MM-yyyy.");
                continue;  
            }

            endDate = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            if (endDate.isAfter(startDate)) {
                break;  
            } else {
                System.out.println("End date must be after start date.");
            }
        }
        return endDate;
    }

    private boolean checkServiceType(String serviceId) {
        return serviceId.startsWith("SVVL") || serviceId.startsWith("SVHO") || serviceId.startsWith("SVRO");
    }

    private Customer findCustomerById(String customerId) {
        for (Customer customer : customerRepository.getAllCustomers()) {
            if (customer.getId().trim().equalsIgnoreCase(customerId.trim())) {
                return customer;
            }
        }
        return null;
    }

    @Override
    public void display(ArrayList<Booking> bookings) {
        if (bookings == null || bookings.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        System.out.println("| BOOKING ID                | CUSTOMER ID  | SERVICE ID  | START DATE | END DATE   | TOTAL PAYMENT | DEPOSIT  |");
        for (Booking booking : bookings) {
            System.out.printf("| %-25s | %-12s | %-10s | %-11s | %-10s | %-13.2f | %-8.2f |\n",
                    booking.getId(),
                    booking.getCustomerName(),
                    booking.getServiceId(),
                    booking.getStartDate(),
                    booking.getEndDate(),
                    booking.getTotalPayment(),
                    booking.getDeposit());
        }
    }
}
