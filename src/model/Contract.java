package model;

public class Contract {
    private String contractNumber;
    private String bookingCode;
    private double deposit;
    private double totalPayment;

    public Contract(String contractNumber, String bookingCode, double deposit, double totalPayment) {
        this.contractNumber = contractNumber;
        this.bookingCode = bookingCode;
        this.deposit = deposit;
        this.totalPayment = totalPayment;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }
    
   @Override
    public String toString() {
        return String.format("| %-15s | %-25s | %-15s | %-15s |",
                             contractNumber, bookingCode, deposit, totalPayment);
    }


    
}