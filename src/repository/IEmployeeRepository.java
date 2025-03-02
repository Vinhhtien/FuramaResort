package repository;
import java.util.ArrayList;
import model.Person.Employee;

public interface IEmployeeRepository extends Repository<Employee, ArrayList<Employee>> {
   final String employeesPath = "\\data\\employee.csv";
   
   
  
}