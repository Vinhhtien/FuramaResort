package Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Validation {
    public static boolean validateEmployeeId(String id) {
        return Pattern.matches("(?i)[a-z]+-\\d{4}", id);
    }
    
    public static boolean validateName(String name) {
        return Pattern.matches("^[A-Z][a-z]*(\\s[A-Z][a-z]*)*$", name);
    }

    public static boolean validateAge(String dateOfBirth) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate birthDate = LocalDate.parse(dateOfBirth, formatter);
            LocalDate today = LocalDate.now();
            return !birthDate.plusYears(18).isAfter(today); // đủ 18
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean validateIdNumber(String idNumber) {
        return Pattern.matches("\\d{9}|\\d{12}", idNumber);
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        return Pattern.matches("0\\d{9}", phoneNumber);
    }

    public static boolean validateSalary(double salary) {
        return salary > 0;
    }
    
    static Scanner sc = new Scanner(System.in);
    
    public static String getValue(String s ){
        System.out.print(s);
        return sc.nextLine();
    }
    
    public static boolean checkValidImportDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate.parse(date, formatter); 
            return true;
        } catch (DateTimeParseException e) {
            return false; 
        }
    }
    
    public static boolean isEmpty(){
        return false;
    }
    
    public static int checkInteger(String inputMsg, String errorMsg, int lower){
        int n;
        while(true){
            try {
                System.out.print(inputMsg);
                n = Integer.parseInt(sc.nextLine());
 
                if(n > lower){
                    return n;
                }else{
                    System.out.println("Value must be greater or equal than : "+ lower);
                }
            } catch (Exception e){
                System.out.println(errorMsg);
            }
        }
    }
    public static int checkIntergerMinMax(String inputMsg,String errorMsg, int lower, int maxer){
        int n;
        while(true){
            try{
                System.out.print(inputMsg);
                n = Integer.parseInt(sc.nextLine());
                
                if(n>lower && n<maxer){
                    return n;
                }else{
                    System.out.println("Value must be in "+lower+"and "+maxer);
                }
            } catch(Exception e){
                System.out.println(errorMsg);
            }
        }
    }
    
    public static double checkDouble(String inputMsg, String errorMsg, double lower){
        double n;
        while(true){
            try{
                System.out.print(inputMsg);
                n= Double.parseDouble(sc.nextLine());
                if (n > lower){
                    return n;
                }else {
                    System.out.println("Value must be greater or equal than : "+ lower);
                }
            } catch(Exception e){
                System.out.println(errorMsg);
            }
        }
    }
    
    public static String checkString(String inputMsg, String errorMsg){
        String n;
        while( true){
            System.out.print(inputMsg);
            n= sc.nextLine().trim().toUpperCase();
            if(n.length()== 0 || n.isEmpty()){
                System.out.println(errorMsg);
            }else {
                return n;
            }
        }
    }
    
    public static String checkStringCondition(String inputMsg, String errorMsg, String condition){
        String n;
        while(true){
            System.out.print(inputMsg);
            n = sc.nextLine().trim(); 
            if(n.length() ==0 || n.isEmpty() || !n.matches(condition)){
                System.out.println(errorMsg);
            }else{
                return n;
            }
        }
    }
    
    
  public static boolean checkEndDateIsAfterStartDate(String startDate, String endDate) {
    try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        // Kiểm tra xem ngày kết thúc có sau ngày bắt đầu không
        return end.isAfter(start);  // Nếu end sau start thì trả về true, nếu không thì false
    } catch (DateTimeParseException e) {
        System.out.println("Invalid date format. Please use dd-MM-yyyy.");
        return false; // Nếu có lỗi khi phân tích ngày, trả về false
    }
}


   
    
}