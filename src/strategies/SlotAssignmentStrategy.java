package strategies;

import models.Gate;
import models.ParkingSlot;
import models.VehicleTypes;

public interface SlotAssignmentStrategy {
    public ParkingSlot getSlot(Gate gate , VehicleTypes vehicleType);
}
