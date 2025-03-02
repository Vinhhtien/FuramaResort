package repository;

import model.Booking;
import java.util.Set;

public interface IBookingRepository extends Repository<Booking, Set<Booking>> {
    void addBooking(Booking booking);
    void displayBookings();
}
