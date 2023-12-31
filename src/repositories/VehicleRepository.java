package repositories;

import models.Vehicle;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class VehicleRepository {
    Map<String,Vehicle> vehicles=new HashMap<>();
    public Optional<Vehicle> getVehicleByNumber(String number){
        if(vehicles.containsKey(number)){
            return Optional.of(vehicles.get(number));
        }
        return Optional.empty();
    }
}
