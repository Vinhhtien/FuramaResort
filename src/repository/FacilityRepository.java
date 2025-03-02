package repository;

import model.Facility.*;
import java.io.*;
import java.util.*;

public class FacilityRepository implements IFacilityRepository {
    private static final String FACILITY_FILE_PATH = "data/facility.csv";
    private static LinkedHashMap<Facility, Integer> facilityUsage = new LinkedHashMap<>();

    static {
        loadFacilitiesFromFile();
    }

    private static void loadFacilitiesFromFile() {
        File file = new File(FACILITY_FILE_PATH);
        if (!file.exists()) {
            System.out.println("File not found: " + FACILITY_FILE_PATH);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FACILITY_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 2) continue; // Bỏ qua dòng không hợp lệ

                Facility facility = null;
                int usageCount = Integer.parseInt(data[data.length - 1]);

                switch (data[0]) {
                    case "Villa":
                        if (data.length < 10) continue; // Kiểm tra lỗi
                        facility = new Villa(data[1], data[2], Double.parseDouble(data[3]),
                                Double.parseDouble(data[4]), Integer.parseInt(data[5]),
                                data[6], data[7], Double.parseDouble(data[8]), Integer.parseInt(data[9]));
                        break;
                    case "House":
                        if (data.length < 9) continue;
                        facility = new House(data[1], data[2], Double.parseDouble(data[3]),
                                Double.parseDouble(data[4]), Integer.parseInt(data[5]),
                                data[6], data[7], Integer.parseInt(data[8]));
                        break;
                    case "Room":
                        if (data.length < 8) continue;
                        facility = new Room(data[1], data[2], Double.parseDouble(data[3]),
                                Double.parseDouble(data[4]), Integer.parseInt(data[5]),
                                data[6], data[7]);
                        break;
                }

                if (facility != null) {
                    facilityUsage.put(facility, usageCount);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void writeFile(Object entities) {
        if (entities instanceof Map) {
            writeFile((Map<Facility, Integer>) entities);
        } else {
            throw new IllegalArgumentException("Invalid data type for writeFile");
        }
    }
    
    public void writeFile(Map<Facility, Integer> facilities) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FACILITY_FILE_PATH))) {
            for (Map.Entry<Facility, Integer> entry : facilities.entrySet()) {
                Facility f = entry.getKey();
                int usage = entry.getValue();

                String line = "";
                if (f instanceof Villa) {
                    Villa v = (Villa) f;
                    line = String.format("Villa,%s,%s,%.2f,%.2f,%d,%s,%s,%.2f,%d,%d",
                            v.getServiceCode(), v.getServiceName(), v.getUsableArea(),
                            v.getRentalCost(), v.getMaxPeople(), v.getRentalType(),
                            v.getRoomStandard(), v.getPoolArea(), v.getNumberOfFloors(), usage);
                } else if (f instanceof House) {
                    House h = (House) f;
                    line = String.format("House,%s,%s,%.2f,%.2f,%d,%s,%s,%d,%d",
                            h.getServiceCode(), h.getServiceName(), h.getUsableArea(),
                            h.getRentalCost(), h.getMaxPeople(), h.getRentalType(),
                            h.getRoomStandard(), h.getNumberOfFloors(), usage);
                } else if (f instanceof Room) {
                    Room r = (Room) f;
                    line = String.format("Room,%s,%s,%.2f,%.2f,%d,%s,%s,%d",
                            r.getServiceCode(), r.getServiceName(), r.getUsableArea(),
                            r.getRentalCost(), r.getMaxPeople(), r.getRentalType(),
                            r.getFreeService(), usage);
                }

                bw.write(line);
                bw.newLine(); // Xuống dòng
            }
            bw.flush(); // Đảm bảo ghi dữ liệu ngay lập tức
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<Facility, Integer> readFile() {
        return facilityUsage;
    }

    @Override
    public void addFacility(Facility facility) {
        facilityUsage.put(facility, 0);
        writeFile(facilityUsage);
    }

    public void displayFacilities() {
        facilityUsage.forEach((facility, usage) -> System.out.println(facility + ", Usage: " + usage));
    }

    public void displayFacilitiesForMaintenance() {
        boolean found = false;
        for (Map.Entry<Facility, Integer> entry : facilityUsage.entrySet()) {
            if (entry.getValue() >= 5) { 
                System.out.println(entry.getKey() + " - Needs Maintenance!");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No facilities need maintenance.");
        }
    }
    
    @Override
    public Map<Facility, Integer> getFacilityList() {
        return facilityUsage; // Trả về danh sách facility
    }


}
