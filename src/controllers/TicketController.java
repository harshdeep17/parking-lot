package controllers;

import dto.IssueTicketRequestDTO;
import dto.IssueTicketResponseDTO;
import dto.ResponseType;
import models.Ticket;
import service.TicketService;

public class TicketController {
    private TicketService ticketService;
    public TicketController(TicketService ticketService){
        this.ticketService=ticketService;
    }
    public IssueTicketResponseDTO issueTicket(IssueTicketRequestDTO issueTicketRequestDTO){
        IssueTicketResponseDTO response=new IssueTicketResponseDTO();
        try{
            Ticket ticket=ticketService.issueTicket(issueTicketRequestDTO.getVehicleNumber(), issueTicketRequestDTO.getVehicleOwnerName(), issueTicketRequestDTO.getVehicleType(),issueTicketRequestDTO.getGateId());
            response.setTicket(ticket);
            response.setResponseStatus(ResponseType.SUCCESS);
        }catch(Exception e){
            response.setResponseStatus(ResponseType.FAILURE);
            response.setFailureMessage(e.getMessage());
        }

        return response;
    }
}
