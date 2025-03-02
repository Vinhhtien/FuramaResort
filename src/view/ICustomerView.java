package view;

import model.Person.Customer;

public interface ICustomerView extends View<Customer> {
    String getEmployeeId();
}
