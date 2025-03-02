package repository;

import model.Booking;
import java.util.*;

public class PromotionRepository {
    private static final TreeSet<String> customerUsedService = new TreeSet<>();
    private static final Stack<String> voucherStack = new Stack<>();
    
    static {
        loadCustomersFromBookings();
    }

    public List<String> getCustomersUsedService(int year) {
        List<String> customers = new ArrayList<>();
        for (Booking booking : BookingRepository.getAllBookings()) {
            if (booking.getBookingYear() == year) {
                customers.add(booking.getCustomerName());
            }
        }
        return customers;
    }

    public void distributeVouchers(int num10, int num20, int num50) {
        voucherStack.clear();
        List<Booking> currentMonthBookings = getBookingsInCurrentMonth();
        currentMonthBookings.sort(Comparator.comparing(Booking::getStartDate).reversed());

        for (Booking booking : currentMonthBookings) {
            voucherStack.push(booking.getCustomerName());
        }

        int totalVouchers = num10 + num20 + num50;
        int totalCustomers = currentMonthBookings.size();
        
        
        if (totalVouchers != totalCustomers) {
            System.out.println("Invalid! voucher must be equal or lower than total Customers.");
            return;
        }

        distributeVoucherTypes(num10, num20, num50);
    }

    private List<Booking> getBookingsInCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH) + 1;
        int currentYear = cal.get(Calendar.YEAR);
        List<Booking> currentMonthBookings = new ArrayList<>();
        
        for (Booking booking : BookingRepository.getAllBookings()) {
            if (booking.getBookingMonth() == currentMonth && booking.getBookingYear() == currentYear) {
                currentMonthBookings.add(booking);
            }
        }
        return currentMonthBookings;
    }

    private void distributeVoucherTypes(int num10, int num20, int num50) {
        //Map theo voucher 
        Map<Integer, Integer> voucherMap = new LinkedHashMap<>();
        voucherMap.put(10, num10);
        voucherMap.put(20, num20);
        voucherMap.put(50, num50);
        //Map khách hàng
        Map<Integer, List<String>> customerVoucherMap = new LinkedHashMap<>();
        customerVoucherMap.put(10, new ArrayList<>());
        customerVoucherMap.put(20, new ArrayList<>());
        customerVoucherMap.put(50, new ArrayList<>());
        // phát voucher
        while (!voucherStack.isEmpty()) {
            for (Map.Entry<Integer, Integer> entry : voucherMap.entrySet()) {
                if (entry.getValue() > 0) {
                    String customerName = voucherStack.pop();
                    customerVoucherMap.get(entry.getKey()).add(customerName);
                    voucherMap.put(entry.getKey(), entry.getValue() - 1);
                    break;
                }
            }
        }
        System.out.println();
        for (Map.Entry<Integer, List<String>> entry : customerVoucherMap.entrySet()) {
            int percent = entry.getKey();
            List<String> customers = entry.getValue();
            if (!customers.isEmpty()) {
                System.out.println(percent + "% Voucher:");
                for (String customerName : customers) {
                    Booking customerBooking = findBookingForCustomer(customerName);
                    if (customerBooking != null) {
                        System.out.println(customerBooking.getId() + "| Customer ID : " + customerBooking.getCustomerName() + "| Service ID: " + customerBooking.getServiceId() + "| Booking Start: " + customerBooking.getStartDate() + "| Booking End: " + customerBooking.getEndDate());
                    }
                    System.out.println(customerName);  
                }
            }
        }
    }
    
    private Booking findBookingForCustomer(String customerName) {
        for (Booking booking : BookingRepository.getAllBookings()) {
            if (booking.getCustomerName().equals(customerName)) {
                return booking;  
            }
        }
        return null;  
    }
    
    private static void loadCustomersFromBookings() {
        for (Booking booking : BookingRepository.getAllBookings()) {
            customerUsedService.add(booking.getCustomerName());
        }
    }
    
    public List<Booking> getBookingsByYear(int year) {
        List<Booking> bookings = new ArrayList<>();
        for (Booking booking : BookingRepository.getAllBookings()) {
            if (booking.getBookingYear() == year) {
                bookings.add(booking);
            }
        }
        return bookings;
    }
}
