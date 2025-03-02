package model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Booking implements Comparable<Booking> {
    private String id;
    private String customerName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String serviceId;
    private double totalPayment;
    private double deposit;

    public Booking(String id, String customerName, String serviceId, LocalDate startDate, LocalDate endDate, double totalPayment, double deposit) {
        this.id = id;
        this.customerName = customerName;
        this.serviceId = serviceId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPayment = totalPayment;
        this.deposit = deposit;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public int getBookingYear() {
        return startDate.getYear();
    }

    public int getBookingMonth() {
        return startDate.getMonthValue();
    }

    // Định dạng ngày tháng với dd-MM-yyyy
    public String toCSV() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return id + "," + customerName + "," + serviceId + "," + startDate.format(formatter) + "," + endDate.format(formatter) + "," + totalPayment + "," + deposit;
    }

    public static Booking fromCSV(String csv) {
        String[] parts = csv.split(",");
        if (parts.length < 7) {  // Chúng ta có ít nhất 7 trường dữ liệu trong tệp CSV
            System.err.println("Invalid booking data (insufficient fields): " + csv);
            return null;  // Trả về null nếu thiếu trường dữ liệu
        }

        String id = parts[0].trim();
        String customerName = parts[1].trim();
        String serviceId = parts[2].trim();

        // Định dạng ngày tháng với dd-MM-yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate = parseDate(parts[3].trim(), formatter);
        LocalDate endDate = parseDate(parts[4].trim(), formatter);

        if (startDate == null || endDate == null) {
            return null;  // Trả về null nếu ngày tháng không hợp lệ
        }

        // Thêm trường totalPayment và deposit
        double totalPayment = Double.parseDouble(parts[5].trim());
        double deposit = Double.parseDouble(parts[6].trim());

        return new Booking(id, customerName, serviceId, startDate, endDate, totalPayment, deposit);
    }

    private static LocalDate parseDate(String dateStr, DateTimeFormatter formatter) {
        try {
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format for: " + dateStr);
            return null;
        }
    }

    @Override
    public int compareTo(Booking other) {
        int result = this.startDate.compareTo(other.startDate);
        return (result == 0) ? this.endDate.compareTo(other.endDate) : result;
    }
}
