package service;

import Exceptions.GateNotFoundException;
import models.*;
import repositories.GateRepository;
import repositories.ParkingLotRepository;
import repositories.TicketRepository;
import repositories.VehicleRepository;
import strategies.SlotAssignmentStrategy;
import strategies.SlotAssignmentStrategyFactory;

import java.util.Date;
import java.util.Optional;

public class TicketService {
    private GateRepository gateRepository;
    private VehicleRepository vehicleRepository;
    private ParkingLotRepository parkingLotRepository;
    private TicketRepository ticketRepository;

    public TicketService(GateRepository gateRepository,
                         VehicleRepository vehicleRepository,
                         ParkingLotRepository parkingLotRepository,
                         TicketRepository ticketRepository) {
        this.gateRepository = gateRepository;
        this.vehicleRepository = vehicleRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.ticketRepository = ticketRepository;
    }
    public Ticket issueTicket( String vehicleNumber,
                               String vehicleOwnerName,
                               VehicleTypes vehicleType,
                               Long gateId) throws GateNotFoundException {
        Ticket ticket=new Ticket();
        ticket.setEntryTime(new Date());
        Optional<Gate> gateOptional=gateRepository.findGateById(gateId);
        if(gateOptional.isEmpty()){
            throw new GateNotFoundException();
        }
        Gate gate=gateOptional.get();
        ticket.setGeneratedAt(gate);
        ticket.setGeneratedBy(gate.getCurrentOperator());
        Vehicle savedVehicle = null;
        Optional<Vehicle> vehicleOptional=vehicleRepository.getVehicleByNumber(vehicleNumber);
        if(vehicleOptional.isEmpty()){
            Vehicle vehicle=new Vehicle();
            vehicle.setVehicleNumber(vehicleNumber);
            vehicle.setOwnerName(vehicleOwnerName);
            vehicle.setVehicleType(vehicleType);
            vehicle.setCreatedAt(new Date());
        }else{
            savedVehicle=vehicleOptional.get();
        }
        ticket.setVehicle(savedVehicle);
        ParkingLot parkingLot=parkingLotRepository.getParkingLotByGate(gate);
        SlotAssignmentStrategyType slotAssignmentStrategyType=parkingLot.getSlotAssignmentStrategyType();
        SlotAssignmentStrategy slotAssignmentStrategy=SlotAssignmentStrategyFactory.getSlotForType(slotAssignmentStrategyType);
        ParkingSlot parkingSlot=slotAssignmentStrategy.getSlot(gate, vehicleType);
        ticket.setParkingSlot(parkingSlot);
        ticket=ticketRepository.saveTicket(ticket);
        ticket.setNumber("TICKET - " + ticket.getId());
        return ticket;
    }
}
