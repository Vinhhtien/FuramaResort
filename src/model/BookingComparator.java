package model;

import model.Booking;
import java.util.Comparator;

public class BookingComparator implements Comparator<Booking> {
    @Override
    public int compare(Booking b1, Booking b2) {
        if (b1.getStartDate() == null || b2.getStartDate() == null) {
            throw new IllegalArgumentException("Start date cannot be null.");
        }
        
        int compareStart = b2.getStartDate().compareTo(b1.getStartDate()); // Ngày gần nhất trước
        if (compareStart != 0) return compareStart;

        // Kiểm tra và so sánh ngày kết thúc nếu ngày bắt đầu giống nhau
        if (b1.getEndDate() == null || b2.getEndDate() == null) {
            throw new IllegalArgumentException("End date cannot be null.");
        }
        return b2.getEndDate().compareTo(b1.getEndDate()); // Ngày kết thúc gần nhất trước
    }
}
