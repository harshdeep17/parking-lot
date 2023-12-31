import controllers.TicketController;
import repositories.GateRepository;
import repositories.ParkingLotRepository;
import repositories.TicketRepository;
import repositories.VehicleRepository;
import service.TicketService;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class ParkingLotApplication {
    public static void main(String[] args) {
        GateRepository gateRepository=new GateRepository();
        VehicleRepository vehicleRepository=new VehicleRepository();
        ParkingLotRepository parkingLotRepository=new ParkingLotRepository();
        TicketRepository ticketRepository=new TicketRepository();
        TicketService ticketService=new TicketService(gateRepository,
                vehicleRepository,parkingLotRepository,ticketRepository);
        TicketController ticketController=new TicketController(ticketService);
    }
}
// controller ---depends on---> services ---depends on---> repositories