package Service;

import model.Booking;
import model.Person.Customer;
import repository.BookingRepository;
import repository.ContractRepository;
import repository.CustomerRepository;
import repository.FacilityRepository;
import view.BookingView;
import model.Contract;
import Utils.Validation;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.*;
import model.BookingComparator;

public class BookingService implements IBookingService {
    private final BookingRepository bookingRepository = new BookingRepository();
    private final ContractRepository contractRepository = new ContractRepository();
    private final CustomerRepository customerRepository = new CustomerRepository();
    private final FacilityRepository facilityRepository = new FacilityRepository();
    private final BookingView bookingView = new BookingView();
    private final Scanner scanner = new Scanner(System.in);
    private ArrayList<Customer> customers;

    public BookingService() {
        this.customers = (ArrayList<Customer>) customerRepository.getAllCustomers();
    }

    @Override
    public void save() {
        try (FileWriter fw = new FileWriter("booking.csv"); PrintWriter writer = new PrintWriter(fw)) {
            Set<Booking> bookings = bookingRepository.readFile();
            for (Booking booking : bookings) {
                writer.printf("%s,%s,%s,%s,%s\n", booking.getId(), booking.getCustomerName(), booking.getServiceId(),
                        booking.getStartDate(), booking.getEndDate());
            }
        } catch (IOException e) {
            System.out.println("Error saving bookings: " + e.getMessage());
        }
    }

    private boolean checkBookingOverlap(Booking newBooking) {
        Set<Booking> existingBookings = bookingRepository.readFile();
        for (Booking existingBooking : existingBookings) {
            if (existingBooking.getCustomerName().equals(newBooking.getCustomerName())) {
                LocalDate existingStart = existingBooking.getStartDate();
                LocalDate existingEnd = existingBooking.getEndDate();
                LocalDate newStart = newBooking.getStartDate();
                LocalDate newEnd = newBooking.getEndDate();

                if (!(newEnd.isBefore(existingStart) || newStart.isAfter(existingEnd))) {
                    System.out.println("Sorry: Service ID " + newBooking.getServiceId() +
                        " already booked from " + existingStart + " to " + existingEnd);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkServiceType(String serviceId) {
        return serviceId.startsWith("SVVL") || serviceId.startsWith("SVHO") || serviceId.startsWith("SVRO");
    }

    @Override
    public void display() {
        Set<Booking> bookings = bookingRepository.readFile();
        bookingView.display(new ArrayList<>(bookings));
    }

    @Override
    public void add(Booking entity) {
        System.out.println("--------- Add New Booking ---------");
        Booking bookingDetails = bookingView.getADetail();

        if (bookingDetails == null) {
            System.out.println("Invalid booking details. Booking not added.");
            return;
        }

        if (checkBookingOverlap(bookingDetails)) {
            System.out.println("Booking is already in use!");
            return;
        }

        bookingRepository.addBooking(bookingDetails);
        System.out.println("Booking added successfully with ID: " + bookingDetails.getId());

        save();
    }

    
    public void createContract() {
        System.out.print("Enter Booking ID to create a contract: ");
        String bookingId = scanner.nextLine();
        Booking booking = bookingRepository.findById(bookingId);

        if (booking == null) {
            System.out.println("Error: Booking not found!");
            return;
        }

        double deposit = booking.getDeposit();
        double totalPayment = booking.getTotalPayment();

        String contractNumber = "CO-" + (contractRepository.getAllContracts().size() + 1);

        Contract newContract = new Contract(contractNumber, bookingId, deposit, totalPayment);
        contractRepository.addContract(newContract);

        System.out.println("Contract created successfully!");
        System.out.println("Contract Number: " + contractNumber);
        System.out.println("Deposit: " + deposit);
        System.out.println("Total Payment: " + totalPayment);

        displayContract(); 
    }

    public void editContract() {
        System.out.print("Enter contract number to edit: ");
        String contractNumber = scanner.nextLine();
        Contract contract = contractRepository.findById(contractNumber);

        if (contract == null) {
            System.out.println("Contract not found!");
            return;
        }

        Booking booking = bookingRepository.findById(contract.getBookingCode());

        if (booking == null) {
            System.out.println("Error: Booking not found for this contract!");
            return;
        }

        double totalPayment = booking.getTotalPayment();

        double newDeposit;
        while (true) {
            newDeposit = Validation.checkDouble("Enter new deposit amount: ",
                    "Invalid deposit amount. Please enter a valid number.", 0);

            if (newDeposit < totalPayment * 0.2) {
                System.out.println("Deposit must be at least 20% of total payment (" + (totalPayment * 0.2) + "). Please enter a valid deposit.");
            } else if (newDeposit > totalPayment) {
                System.out.println("Deposit cannot be greater than total payment (" + totalPayment + "). Please enter a valid deposit.");
            } else {
                break;
            }
        }

        contract.setDeposit(newDeposit);
        contractRepository.updateContract(contract);

        System.out.println("Contract updated successfully!");
        displayContract();
    }

    private void updateBookingQueue() {
        bookingQueue.clear();
        Set<Booking> bookings = bookingRepository.readFile();
        if (bookings.isEmpty()) {
            System.out.println("No bookings found in the repository.");
        } else {
            bookingQueue.addAll(bookings);
        }
    }

    public void displayContract() {
        System.out.println("=== CONTRACT LIST ===");
        System.out.println("| %-15s | %-25s | %-15s | %-15s |".formatted("Contract Number", "Booking Code", "Deposit", "Total Payment"));
        System.out.println("-------------------------------------------------------------");

        for (Contract contract : contractRepository.getAllContracts()) {
            System.out.println(contract);
        }
    }

    private Queue<Booking> bookingQueue = new LinkedList<>();

    @Override
    public Booking findbyId(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
