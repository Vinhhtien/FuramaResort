package repository;

public interface ICustomerRepository extends Repository {
    void displayCustomer();
    void addCustomer();
    void updateCustomer();
}