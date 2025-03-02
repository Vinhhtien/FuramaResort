package model.Facility;

public class Room extends Facility {
    private String freeService;

    public Room(String serviceCode, String serviceName, double usableArea, double rentalCost, int maxPeople, String rentalType, String freeService) {
        super(serviceCode, serviceName, usableArea, rentalCost, maxPeople, rentalType);
        this.freeService = freeService;
    }

    public String getFreeService() {
        return freeService;
    }

    public void setFreeService(String freeService) {
        this.freeService = freeService;
    }

    @Override
    public String toString() {
        return String.format(
            "%-12s | %-20s | %-12.2f | %-12.2f | %-10d | %-15s | %-20s",
            super.getServiceCode(), super.getServiceName(), super.getUsableArea(), super.getRentalCost(), 
            super.getMaxPeople(), super.getRentalType(), freeService
        );
    }


    
}