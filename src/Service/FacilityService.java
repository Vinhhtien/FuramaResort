package Service;

import java.util.ArrayList;
import model.Facility.Facility;
import model.Facility.House;
import model.Facility.Room;
import model.Facility.Villa;
import repository.FacilityRepository;
import repository.IFacilityRepository;
import view.FacilityView;

import java.util.Map;

public class FacilityService implements IFacilityService {
    private final FacilityView facilityView = new FacilityView();
    private final IFacilityRepository facilityRepository = new FacilityRepository();

    private boolean isIdExist(String serviceId) {
        Map<Facility, Integer> facilityList = facilityRepository.getFacilityList();
        for (Facility facility : facilityList.keySet()) {
            if (facility.getServiceCode().equals(serviceId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void display() {
        Map<Facility, Integer> facilityList = facilityRepository.getFacilityList();
        if (facilityList.isEmpty()) {
            System.out.println("No facilities available.");
        } else {
            facilityView.display(new ArrayList<>(facilityList.keySet()));
        }
    }

    @Override
    public void add(Facility entity) {
        Facility facility = facilityView.getADetail();
        if (facility != null) {
            if (isIdExist(facility.getServiceCode())) {
                System.out.println("Service ID already exists! Please use a different ID.");
            } else {
                facilityRepository.addFacility(facility);
                System.out.println("Facility added successfully!");
            }
        }
    }

    @Override
    public void save() {
        facilityRepository.writeFile(facilityRepository.getFacilityList());
        System.out.println("Facilities saved successfully.");
    }

    public void displayFacilityMaintenance() {
        Map<Facility, Integer> facilityList = facilityRepository.getFacilityList();
        boolean found = false;
        for (Map.Entry<Facility, Integer> entry : facilityList.entrySet()) {
            if (entry.getValue() >= 5) {  // Nếu cơ sở vật chất đã được sử dụng từ 5 lần trở lên
                System.out.println(entry.getKey() + " - Needs Maintenance!");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No facilities need maintenance.");
        }
    }

    public void updateFacilityUsage(Facility facility) {
        Map<Facility, Integer> facilityList = facilityRepository.getFacilityList();
        if (facilityList.containsKey(facility)) {
            int usageCount = facilityList.get(facility);
            facilityList.put(facility, usageCount + 1);
            facilityRepository.writeFile(facilityList);
        }
    }

    @Override
    public Facility findbyId(String id) {
        Map<Facility, Integer> facilityList = facilityRepository.getFacilityList();
        for (Facility facility : facilityList.keySet()) {
            if (facility.getServiceCode().equals(id)) {
                return facility;
            }
        }
        return null;
    }
}
