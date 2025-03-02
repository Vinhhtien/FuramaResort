package model.Person;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class Employee extends Person {
    private String level;
    private String position;
    private double salary;

    public Employee(String id, String name, String dateOfBirth, String gender, 
                    String idNumber, String phoneNumber, String email, 
                    String level, String position, double salary) {
        super(id, name, dateOfBirth, gender, idNumber, phoneNumber, email);
        this.level = level;
        this.position = position;
        this.salary = salary;
    }





    private void validateIDCard(String idNumber) {
        if (!Pattern.matches("^\\d{9}|\\d{12}$", idNumber)) {
            throw new IllegalArgumentException("ID card must be 9 or 12 digits");
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (!Pattern.matches("^0\\d{9}$", phoneNumber)) {
            throw new IllegalArgumentException("Phone number must start with 0 and be 10 digits");
        }
    }

    private void validateSalary(double salary) {
        if (salary <= 0) {
            throw new IllegalArgumentException("Salary must be greater than 0");
        }
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        validateSalary(salary);
        this.salary = salary;
    }

    @Override
    public String toString() {
        return String.format(
            "%s | Level: %s | Position: %s | Salary: %.2f",
            super.toString(), level, position, salary
        );
    }
}
