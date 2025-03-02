package repository;

import model.Person.Customer;
import java.util.Set;

public interface IPromotionRepository {
    Set<Customer> getCustomersUsedService(int year);
    void distributeVouchers(int num10, int num20, int num50);
}
