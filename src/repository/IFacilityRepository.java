package repository;

import java.util.Map;
import model.Facility.Facility;

public interface IFacilityRepository extends Repository {

     void addFacility(Facility facility);

     Map<Facility, Integer> getFacilityList();

    
   
}