package repository;

import model.Booking;
import java.io.*;
import java.util.*;
import model.BookingComparator;

public class BookingRepository implements IBookingRepository {
    private static final String FILE_PATH = "data/booking.csv"; 
    private static final Set<Booking> bookings;

    static {
        bookings = new TreeSet<>(new BookingComparator());  
        loadBookingsFromFile();  
    }

    private static void loadBookingsFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Booking booking = Booking.fromCSV(line);  
                if (booking != null) {
                    bookings.add(booking);  
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    @Override
    public void writeFile(Set<Booking> bookings) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Booking booking : bookings) {
                writer.write(booking.toCSV());  
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    @Override
    public Set<Booking> readFile() {
        return new TreeSet<>(bookings);  
    }

    @Override
    public void addBooking(Booking booking) {
        if (bookings.add(booking)) {  
            writeFile(bookings);  
        } else {
            System.out.println("Booking already exists: " + booking);  
        }
    }

    @Override
    public void displayBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings available.");
        } else {
            bookings.forEach(System.out::println);  
        }
    }

    // 
    public Queue<Booking> getBookingsForContract() {
        return new PriorityQueue<>(bookings);  
    }

    public static Set<Booking> getAllBookings() {
        return new TreeSet<>(bookings);  
    }
    //
    public Booking findById(String bookingId) {
        for (Booking booking : bookings) {
            if (booking.getId().equalsIgnoreCase(bookingId)) {
                return booking;
            }
        }
        return null; 
    }

}


