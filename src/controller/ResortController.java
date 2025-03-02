package controller;

import Service.EmployeeService;
import Service.*;
import repository.*;
import view.*;
import java.util.Scanner;
import service.PromotionService;

public class ResortController extends Menu<String> {
    private static final Scanner scanner = new Scanner(System.in);
 
    EmployeeRepository empRepo = new EmployeeRepository();
    EmployeeService empSer = new EmployeeService();
    EmployeeView empView = new EmployeeView(empRepo, empSer);
    
    CustomerRepository cusRepo = new CustomerRepository();
    CustomerService cusSer = new CustomerService();
    CustomerView cusView = new CustomerView(cusRepo,cusSer);
    
    BookingService bookingService = new BookingService();
    BookingRepository bookRepo = new BookingRepository();
    BookingView bookView = new BookingView();
    
    
    FacilityService facilityService = new FacilityService();
    FacilityView facilityView = new FacilityView();
    
    private final PromotionService promotionService = new PromotionService();

    public ResortController() {
        super("--- FURAMA RESORT ---", new String[]{
            "Employee Management",
            "Customer Management",
            "Facility Management",
            "Booking Management",
            "Promotion Management",
            "Exit"
        });
    }

    private void displayEmployeeMenu() {
        Menu<String> EMPMenu = new Menu<>("--- EMP ---", new String[]{
            "Display list employees",
            "Add new employee",
            "Edit employee",
        }) {
            @Override
            public void execute(int n) {
                switch (n) {
                    case 1:
                        empView.display(empSer.getEmployees());
                        break;
                    case 2:
                        empSer.add(empView.getADetail());
                        break;
                    case 3:
                        empSer.updateEmployee();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        };
        EMPMenu.run();
    }

    private void displayCustomerMenu() {
        Menu<String> CUSMenu = new Menu<>("--- CUS ---", new String[]{
            "Display list customers",
            "Add new customer",
            "Edit customer",
        }) {
            @Override
            public void execute(int n) {
                switch (n) {
                    case 1:
                        cusView.display(cusSer.getCustomers());
                        break;
                    case 2:
                        cusSer.add(cusView.getADetail());
                        break;
                    case 3:
                        cusSer.editCustomer();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        };
        CUSMenu.run();
    }

    private void displayFacilityMenu() {
        Menu<String> FACMenu = new Menu<>("--- FACILITY ---", new String[]{
            "Display list facility",
            "Add new facility",
            "Display list facility maintenance",
        }) {
            @Override
            public void execute(int n) {
                switch (n) {
                    case 1:
                        facilityService.display();
                        break;
                    case 2:
                        facilityService.add(null);
                        break;
                    case 3:
                        facilityService.displayFacilityMaintenance();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        };
        FACMenu.run();
    }

    private void displayBookingMenu() {
        Menu<String> BOOMenu = new Menu<>("--- BOOKING ---", new String[]{
            "Add new booking",
            "Display list booking",
            "Create new contracts",
            "Display list contracts",
            "Edit contracts",
        }) {
            @Override
            public void execute(int n) {
                switch (n) {
                    case 1:
                        bookingService.add(null);
                        break;
                    case 2:
                        bookingService.display();
                        break;
                    case 3:
                        bookingService.createContract();
                        break;
                    case 4:
                        bookingService.displayContract();
                        break;
                    case 5:
                        bookingService.editContract();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        };
        BOOMenu.run();
    }

    private void displayPromotionMenu() {
        Menu<String> PROMenu = new Menu<>("--- PROMOTION ---", new String[]{
            "Display list customers use service",
            "Display list customers get voucher",
        }) {
            @Override
            public void execute(int n) {
                switch (n) {
                    case 1:
                        promotionService.handleDisplayCustomersUsedService();
                        break;
                    case 2:
                        promotionService.handleDistributeVouchers();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        };
        PROMenu.run();
    }

    @Override
    public void execute(int n) {
        switch (n) {
            case 1:
                displayEmployeeMenu();
                break;
            case 2:
                displayCustomerMenu();
                break;
            case 3:
                displayFacilityMenu();
                break;
            case 4:
                displayBookingMenu();
                break;
            case 5:
                displayPromotionMenu();
                break;
            case 6:
                System.out.println("Exiting...");
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}
