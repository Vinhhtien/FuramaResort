package view;

import model.Person.Employee;


public interface IEmployeeView extends View<Employee> {
    String getEmployeeID();  
}
