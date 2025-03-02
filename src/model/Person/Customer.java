package model.Person;

public class Customer extends Person {
    private String customerType;
    private String address;

    public Customer(String id, String name, String dateOfBirth, String gender, String idNumber, String phoneNumber, String email, String customerType, String address) {
        super(id, name, dateOfBirth, gender, idNumber, phoneNumber, email);
        this.customerType = customerType;
        this.address = address;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return String.format(
            "%s | Customer Type: %-10s | Address: %-20s",
            super.toString(), customerType, address
        );
    }

}