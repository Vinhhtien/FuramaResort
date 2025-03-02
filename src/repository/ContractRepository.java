package repository;

import model.Contract;
import java.io.*;
import java.util.*;

public class ContractRepository {
    private static final String FILE_PATH = "data/contract.csv";
    private static final List<Contract> contracts = new ArrayList<>();

    static {
            
        loadContractsFromFile();
    }

    private static void loadContractsFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    // Sửa lại cách xử lý giá trị string và parse double
                    String contractNumber = data[0].replace("\"", "").trim();
                    String bookingCode = data[1].replace("\"", "").trim();
                    double deposit = Double.parseDouble(data[2].replace("\"", "").trim());
                    double totalPayment = Double.parseDouble(data[3].replace("\"", "").trim());
                    contracts.add(new Contract(contractNumber, bookingCode, deposit, totalPayment));
                }
            }
        } catch (IOException e) {
            System.err.println("⚠ Lỗi khi đọc file hợp đồng: " + e.getMessage());
        }
    }

    public void saveContractsToFile() {
        
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Contract contract : contracts) {
                writer.write(String.join(",", 
                   "\"" + contract.getContractNumber() + "\"", 
                   "\"" + contract.getBookingCode() + "\"", 
                   "\"" + contract.getDeposit() + "\"", 
                   "\"" + contract.getTotalPayment() + "\""));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("⚠ Lỗi khi ghi file hợp đồng: " + e.getMessage());
        }
    }

    public void addContract(Contract contract) {
        contracts.add(contract);
        saveContractsToFile();
    }

    public List<Contract> getAllContracts() {
        return new ArrayList<>(contracts);
    }

    public Contract findById(String contractNumber) {
        return contracts.stream()
                .filter(contract -> contract.getContractNumber().equals(contractNumber))
                .findFirst()
                .orElse(null);
    }

    public void updateContract(Contract contract) {
        for (int i = 0; i < contracts.size(); i++) {
            if (contracts.get(i).getContractNumber().equals(contract.getContractNumber())) {
                contracts.set(i, contract);
                saveContractsToFile();
                break;
            }
        }
    }

    public boolean existsByContractNumber(String contractNumber) {
    return contracts.stream()
                    .anyMatch(contract -> contract.getContractNumber().equals(contractNumber));
}

    public boolean existsByBookingId(String bookingId) {
    return contracts.stream()
            .anyMatch(contract -> contract.getBookingCode().equals(bookingId));
}

public Contract findByBookingId(String bookingId) {
    return contracts.stream()
                    .filter(contract -> contract.getBookingCode().equals(bookingId))
                    .findFirst()
                    .orElse(null);
}
public Contract findContractByBookingId(String bookingId) {
        for (Contract contract : contracts) {
            if (contract.getBookingCode().equals(bookingId)) {
                return contract;  // Nếu tìm thấy hợp đồng với Booking ID trùng khớp
            }
        }
        return null;  // Nếu không tìm thấy hợp đồng nào
    }

}
