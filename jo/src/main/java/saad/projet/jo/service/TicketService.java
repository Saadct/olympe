package saad.projet.jo.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saad.projet.jo.constants.State;
import saad.projet.jo.dto.ticket.CreateTicket;
import saad.projet.jo.dto.ticket.GetTicketDto;
import saad.projet.jo.model.Event;
import saad.projet.jo.model.Operation;
import saad.projet.jo.model.Ticket;
import saad.projet.jo.model.User;
import saad.projet.jo.repository.TicketRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository repository;
    private final EventService evenementService;
    private final OperationService operationService;
    private final UserService userService;

    @Autowired
    public TicketService(TicketRepository repository,
                         EventService evenementService,
                         OperationService operationService,
                         UserService userService){
        this.repository = repository;
        this.evenementService = evenementService;
        this.operationService = operationService;
        this.userService = userService;
    }

    public List<Ticket> findAllTicket () {
        System.out.println("Toutes les tickets");
        return repository.findAll();
    }

    public Ticket findTicketById(String uuid) {

        return repository.findOneByUuid(uuid).orElse(null);
    }

    public Ticket createTicket (Ticket ticket){
        System.out.println("Ticket créer");
        return repository.save(ticket);
    }

    public List<GetTicketDto> getTicketByUserId (String uuid){
        List<Ticket> tickets = repository.findAllByUserId(uuid);
        List<GetTicketDto> getTicketsDto = new ArrayList<>();
        for (Ticket ticket : tickets) {
            GetTicketDto ticketDto = new GetTicketDto();
            ticketDto.setEvenement(ticket.getEvenement());
            ticketDto.setFirstname(ticket.getFirstname());
            ticketDto.setName(ticket.getName());
            getTicketsDto.add(ticketDto);

        }
        return getTicketsDto;
    }

    public List<GetTicketDto> getTicketByUserEmail (String email){
        User user = userService.findByEmail(email);
        List<Ticket> tickets = repository.findAllByUserId(user.getId());
        List<GetTicketDto> getTicketsDto = new ArrayList<>();
        for (Ticket ticket : tickets) {
            GetTicketDto ticketDto = new GetTicketDto();
            ticketDto.setEvenement(ticket.getEvenement());
            ticketDto.setFirstname(ticket.getFirstname());
            ticketDto.setName(ticket.getName());
            ticketDto.setUuid(ticket.getUuid());
            getTicketsDto.add(ticketDto);

        }
        return getTicketsDto;
    }


    public Page<GetTicketDto> getTicketByUserEmailPaginated (String email, int size, int page){
        Pageable paging = PageRequest.of(page, size);
        User user = userService.findByEmail(email);
        Page<Ticket> tickets = repository.findAllByUserPaginated(user, paging);
        return mappingToPageDto(tickets, paging);
    }





    public Page<GetTicketDto> getTicketByUserIdPaginated (String uuid, int size, int page){
        Pageable paging = PageRequest.of(page, size);
        User user = userService.findById(uuid);
        Page<Ticket> tickets = repository.findAllByUserPaginated(user, paging);
        return mappingToPageDto(tickets, paging);
    }

    public Page<GetTicketDto> getTicketByUserEmailPaginatedAvailable (String email, int size, int page){
        Pageable paging = PageRequest.of(page, size);
        User user = userService.findByEmail(email);
        Page<Ticket> tickets = repository.findAllByUserPaginatedAvailable(user, paging);
        return mappingToPageDto(tickets, paging);
    }

    public Page<GetTicketDto> getTicketByUserEmailPaginatedNotAvailable (String email, int size, int page){
        Pageable paging = PageRequest.of(page, size);
        User user = userService.findByEmail(email);
        Page<Ticket> tickets = repository.findAllByUserPaginatedNotAvailable(user, paging);
        return mappingToPageDto(tickets, paging);
    }

    public Page<GetTicketDto> findByEvenement(String evenementId, int size, int page){
        Pageable paging = PageRequest.of(page, size);
        Page<Ticket> tickets = repository.findAllByEvenementPaginated(evenementService.findEvenementById(evenementId), paging);
        return mappingToPageDto(tickets, paging);
    }

    public Page<GetTicketDto> mappingToPageDto (Page<Ticket> tickets, Pageable paging){
        List<GetTicketDto> getTicketsDto = new ArrayList<>();
        for (Ticket ticket : tickets) {
            GetTicketDto ticketDto = new GetTicketDto();
            ticketDto.setEvenement(ticket.getEvenement());
            ticketDto.setFirstname(ticket.getFirstname());
            ticketDto.setName(ticket.getName());
            ticketDto.setUuid(ticket.getUuid());
            getTicketsDto.add(ticketDto);
        }
        Page<GetTicketDto> ticketsPage = new PageImpl<>(getTicketsDto, paging, 10);
        return ticketsPage;
    }




    public int getTotalPage(String email, int size) {
        long totalItems = repository.countByDateAfter(userService.findByEmail(email));
        return (int) Math.ceil((double) totalItems / size);
    }

    public int getTotalPageById(String uuid, int size) {
        long totalItems = repository.countByDateAfter(userService.findById(uuid));
        return (int) Math.ceil((double) totalItems / size);
    }

    public int getTotalPageByEvenement(String uuid, int size) {
        long totalItems = repository.countByEvenement(evenementService.findEvenementById(uuid));
        return (int) Math.ceil((double) totalItems / size);
    }

    public int getTotalPageAvailable(String email, int size) {
        long totalItems = repository.countByDateAfterAvailable(userService.findByEmail(email));
        return (int) Math.ceil((double) totalItems / size);
    }

    public int getTotalPageNotAvailable(String email, int size) {
        long totalItems = repository.countByDateAfterNotAvailable(userService.findByEmail(email));
        return (int) Math.ceil((double) totalItems / size);
    }

    public Boolean buyTicket(String eventId, @Valid CreateTicket t, String email){
        Event event = evenementService.findEvenementById(eventId);
        User user = userService.findByEmail(email);
        if(!(event.getAvailableSeats() == 0)) {
        LocalDateTime date = LocalDateTime.now();
        Ticket ticket = new Ticket(
                    t.getName(),
                    t.getFirstName(),
                    event,
                    user,
                    event.getDateEvent()
            );
            Ticket boughtTicket = repository.save(ticket);
            Operation op = new Operation(State.Achat_Unique_Ticket.toString(), date, user);
            operationService.createOperation(op);
            evenementService.updateSeatsAvailable(eventId);
            return true;
        }
        return false;
    }

    public Boolean checkRegistration(String email, String uuid){
        try {
            User user = userService.findByEmail(email);
            Event evenement = evenementService.findEvenementById(uuid);
            List<Ticket> tickets = repository.findAllByUserAndEvenement(user, evenement);
            if(tickets.size() > 0){
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }



    public Boolean createTicket( String email, String eventId){
        if(!checkRegistration(email, eventId)) {
            Event event = evenementService.findEvenementById(eventId);
            User user = userService.findByEmail(email);
            if (!(event.getAvailableSeats() == 0)) {
                Ticket ticket = new Ticket(
                        user.getName(),
                        user.getFirstName(),
                        event,
                        user,
                        event.getDateEvent()
                );
                repository.save(ticket);
                evenementService.updateSeatsAvailable(eventId);
                return true;
            }
        }
        return false;
    }


    @Transactional
    public Boolean CancelTicketByToken (String email, String id){
        User user = userService.findByEmail(email);
        Ticket ticket = findTicketById(id);
        LocalDate date = LocalDate.now();
        if(ticket != null && date.isBefore(ticket.getDateEvent()) && ticket.getUser() == user) {
            repository.deleteById(id);
            Event evenement = ticket.getEvenement();
            evenementService.updateSeatsAvailable(evenement.getUuid());
            return true;
        }
        return false;
    }


    public Boolean CancelTicketById (String id){
        Ticket ticket = findTicketById(id);
        LocalDate date = LocalDate.now();
        if(ticket != null && date.isBefore(ticket.getDateEvent())) {
            Event evenement = ticket.getEvenement();
            repository.deleteById(id);
            evenementService.updateSeatsAvailable(evenement.getUuid());
            return true;
        }
        return false;
    }


    @Transactional
    public Boolean deleteTicket (String id){
        System.out.println("ticket supprimée");
        Ticket ticket = findTicketById(id);
        if(ticket != null) {
            repository.deleteById(id);
            Event evenement = ticket.getEvenement();
            evenementService.updateSeatsAvailable(evenement.getUuid());
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updateTicket (String id, Ticket t){
        Ticket ticket = findTicketById(id);
        if(ticket != null) {
            ticket.setPrice(t.getPrice());
            ticket.setState(t.getState());
            repository.save(ticket);
            return true;
        }
        return false;
    }




}
