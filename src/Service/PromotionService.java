package service;

import repository.PromotionRepository;
import Utils.Validation;
import java.util.List;
import model.Booking;


public class PromotionService {
    private final PromotionRepository promotionRepository = new PromotionRepository();
    
    public void handleDisplayCustomersUsedService() {
        // Nhận đầu vào năm từ người dùng
        int year = Validation.checkInteger("Enter the year you want to check: ", "Invalid input!", 0);
        List<Booking> bookings = promotionRepository.getBookingsByYear(year);

        if (bookings.isEmpty()) {
            System.out.println("No customers used the service in the year | Year too old . " + year);
        } else {
            System.out.println("List of customers who used the service in " + year + ":");
            for (Booking booking : bookings) {
                System.out.println(booking.getId() + "| Customer ID : " + booking.getCustomerName() + "| Service ID: " + booking.getServiceId() + ", Booking Start: " + booking.getStartDate() + ", Booking End: " + booking.getEndDate());
            }
        }
    }
    
    public void handleDistributeVouchers() {
        int num10 = Validation.checkInteger("Nhap so luong voucher 10%: ","Sai format, agian!",0);
        int num20 = Validation.checkInteger("Nhap so luong voucher 20%: ","Sai format, agian!",0);
        int num50 = Validation.checkInteger("Nhap so luong voucher 50%: ","Sai format, agian!",0);
        promotionRepository.distributeVouchers(num10, num20, num50);
    }
}
