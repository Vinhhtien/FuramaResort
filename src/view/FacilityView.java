package view;

import model.Facility.Facility;
import model.Facility.House;
import model.Facility.Room;
import model.Facility.Villa;
import Utils.Validation;
import java.util.ArrayList;
import java.util.Map;
import repository.FacilityRepository;

public class FacilityView implements IFacilityView {
    FacilityRepository facilityRepository = new FacilityRepository();
    @Override
    public void display(ArrayList<Facility> entities) {
        if (entities == null || entities.isEmpty()) {
            System.out.println("No facilities available.");
            return;
        }

        System.out.println("========== Facility List ==========");
        System.out.printf("%-10s %-15s %-10s %-10s %-10s %-15s %-25s %-10s\n", 
                "ID", "Name", "Area", "Cost", "People", "Rental Type", "Extra Info", "Usage");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");

        // Hiển thị danh sách facility
        for (Facility facility : entities) {
            String extraInfo = "";
            if (facility instanceof Villa villa) {
                extraInfo = String.format("Pool: %.1f m^2, Floors: %d", villa.getPoolArea(), villa.getNumberOfFloors());
            } else if (facility instanceof House house) {
                extraInfo = String.format("Floors: %d", house.getNumberOfFloors());
            } else if (facility instanceof Room room) {
                extraInfo = "Free Service: " + room.getFreeService();
            }

            // Lấy số lần sử dụng từ facilityRepository
            Map<Facility, Integer> facilityList = facilityRepository.getFacilityList();
            int usage = facilityList.getOrDefault(facility, 0);  // Nếu không có, mặc định usage = 0

            System.out.printf("%-10s %-15s %-10.2f %-10.2f %-10d %-15s %-25s %-10d\n",
                    facility.getServiceCode(),
                    facility.getServiceName(),
                    facility.getUsableArea(),
                    facility.getRentalCost(),
                    facility.getMaxPeople(),
                    facility.getRentalType(),
                    extraInfo,
                    usage); // Sử dụng giá trị thực tế của Usage
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
    }


    @Override
    public Facility getADetail() {
        System.out.println("Select type of facility to add:");
        System.out.println("1. Add New Villa");
        System.out.println("2. Add New House");
        System.out.println("3. Add New Room");

        int choice = Validation.checkInteger("Select option (1-3): ", "Invalid choice, try again!", 1);

        switch (choice) {
            case 1:
                return createVilla();
            case 2:
                return createHouse();
            case 3:
                return createRoom();
            default:
                System.out.println("Invalid choice, try again.");
                return null;
        }
    }

    private Villa createVilla() {
        String serviceID = Validation.checkStringCondition("Enter service ID (SVVL-YYYY): ", "Invalid service ID! Format: SVVL-YYYY", "^SVVL-\\d{4}$");
        String serviceName = Validation.checkStringCondition("Enter service name: ", "Invalid service name! Must start with uppercase.", "^[A-Z][a-z]*(\\s[A-Z][a-z]*)*$");
        double usableArea = Validation.checkDouble("Enter usable area (>30m^2): ", "Invalid area! Must be >30.", 30.1);
        double rentalCost = Validation.checkDouble("Enter rental cost (>0): ", "Invalid cost! Must be >0.", 0.1);
        int maxPeople = Validation.checkIntergerMinMax("Enter max people (1-19): ", "Invalid number! Must be between 1 and 19.", 1, 19);
        String rentalType = Validation.checkStringCondition("Enter rental type: ", "Invalid rental type! Must start with uppercase.", "^[A-Z][a-z]*(\\s[A-Z][a-z]*)*$");
        String roomStandard = Validation.checkStringCondition("Enter room standard: ", "Invalid room standard! Must start with uppercase.", "^[A-Z][a-z]*(\\s[A-Z][a-z]*)*$");
        double poolArea = Validation.checkDouble("Enter pool area (>30m^2): ", "Invalid area! Must be >30.", 30.1);
        int floors = Validation.checkInteger("Enter number of floors (>0): ", "Invalid number! Must be a positive integer.", 1);

        return new Villa(serviceID, serviceName, usableArea, rentalCost, maxPeople, rentalType, roomStandard, poolArea, floors);
    }

    private House createHouse() {
        String serviceID = Validation.checkStringCondition("Enter service ID (SVHO-YYYY): ", "Invalid service ID! Format: SVHO-YYYY", "^SVHO-\\d{4}$");
        String serviceName = Validation.checkStringCondition("Enter service name: ", "Invalid service name!", "^[A-Z][a-z]*(\\s[A-Z][a-z]*)*$");
        double usableArea = Validation.checkDouble("Enter usable area (>30m^2): ", "Invalid area!", 30.1);
        double rentalCost = Validation.checkDouble("Enter rental cost (>0): ", "Invalid cost!", 0.1);
        int maxPeople = Validation.checkIntergerMinMax("Enter max people (1-19): ", "Invalid number!", 1, 19);
        String rentalType = Validation.checkStringCondition("Enter rental type: ", "Invalid rental type!", "^[A-Z][a-z]*(\\s[A-Z][a-z]*)*$");
        String roomStandard = Validation.checkStringCondition("Enter room standard: ", "Invalid room standard!", "^[A-Z][a-z]*(\\s[A-Z][a-z]*)*$");
        int floors = Validation.checkInteger("Enter number of floors (>0): ", "Invalid number!", 1);

        return new House(serviceID, serviceName, usableArea, rentalCost, maxPeople, rentalType, roomStandard, floors);
    }

    private Room createRoom() {
        String serviceID = Validation.checkStringCondition("Enter service ID (SVRO-YYYY): ", "Invalid service ID! Format: SVRO-YYYY", "^SVRO-\\d{4}$");
        String serviceName = Validation.checkStringCondition("Enter service name: ", "Invalid service name!", "^[A-Z][a-z]*(\\s[A-Z][a-z]*)*$");
        double usableArea = Validation.checkDouble("Enter usable area (>30m^2): ", "Invalid area!", 30.1);
        double rentalCost = Validation.checkDouble("Enter rental cost (>0): ", "Invalid cost!", 0.1);
        int maxPeople = Validation.checkIntergerMinMax("Enter max people (1-19): ", "Invalid number!", 1, 19);
        String rentalType = Validation.checkStringCondition("Enter rental type: ", "Invalid rental type!", "^[A-Z][a-z]*(\\s[A-Z][a-z]*)*$");
        String freeService = Validation.checkStringCondition("Enter free service included: ", "Invalid input!", "^[A-Z][a-z]*(\\s[A-Z][a-z]*)*$");

        return new Room(serviceID, serviceName, usableArea, rentalCost, maxPeople, rentalType, freeService);
    }
}
