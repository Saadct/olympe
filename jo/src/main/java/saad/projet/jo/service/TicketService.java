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
import saad.projet.jo.model.Evenement;
import saad.projet.jo.model.Operation;
import saad.projet.jo.model.Ticket;
import saad.projet.jo.model.User;
import saad.projet.jo.repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository repository;
    private final EvenementService evenementService;
    private final OperationService operationService;
    private final UserService userService;

    @Autowired
    public TicketService(TicketRepository repository,
                         EvenementService evenementService,
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
        Evenement event = evenementService.findEvenementById(eventId);
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
            evenementService.updateSeatsAvailable(eventId, 1);
            return true;
        }
        return false;
    }

    public Boolean checkRegistration(String email, String uuid){
        User user = userService.findByEmail(email);
        Evenement evenement = evenementService.findEvenementById(uuid);
        List<Ticket> tickets = repository.findAllByUserAndEvenement(user,evenement);
        if(tickets.size() > 0){
            return true;
        }
        return false;
    }

    public Boolean createTicket( String email, String eventId){
        if(!checkRegistration(email, eventId)) {
            Evenement event = evenementService.findEvenementById(eventId);
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
                evenementService.updateSeatsAvailable(eventId, 1);
                return true;
            }
        }
        return false;
    }

    public Boolean bookTicket(String eventId, CreateTicket t, String email) {
        Evenement event = evenementService.findEvenementById(eventId);
        User user = userService.findByEmail(email);
        double tarif = 0.0;
        if(event.getStandartPrice() != null){
            tarif = (event.getStandartPrice());
        }
        if (!(event.getAvailableSeats() == 0)) {
            String state = State.Reserver.toString();
            LocalDateTime date = LocalDateTime.now();
            Ticket ticket = new Ticket(
                    user.getName(),
                    user.getFirstName(),
                    event,
                    user,
                    event.getDateEvent()
            );
            Ticket boughtTicket = repository.save(ticket);
            Operation op = new Operation(State.Reservation_Ticket.toString(), date, user);
            operationService.createOperation(op);
            evenementService.updateSeatsAvailable(eventId, 1);
            return true;
        }
        return false;
    }

    public boolean paidBookTicket(String ticketId, String email){
        Ticket ticket = findTicketById(ticketId);
        if(ticket != null){
            String state = State.Acheter.toString();
            LocalDateTime date = LocalDateTime.now();
            ticket.setState(state);
            ticket.setDateUpdate(date);
            repository.save(ticket);
            operationService.recordAction(State.Paiement_Ticket_Reserver.toString(),date, email);
         return true;
        }
        return false;
    }


    public boolean buyTickets(String eventId, List<CreateTicket> tickets, String email){
        Evenement event = evenementService.findEvenementById(eventId);
        User user = userService.findByEmail(email);

        if(event.getAvailableSeats() > tickets.size()) {
            LocalDateTime date = LocalDateTime.now();
            List<Ticket> boughtTickets = new ArrayList<>();
            Double promotion;
            if (tickets.size() > 5) {
                promotion = 0.9; // 10% de réduction (ou 90% du prix)
            } else if (tickets.size() > 10) {
                promotion = 0.75; // 25% de réduction (ou 75% du prix)
            } else {
                promotion = (tickets.size() > 10) ? 0.50 : 1.0; // 50% de réduction pour plus de 10 billets, sinon aucun rabais
            }

            double tarif = 0.0;
             if(event.getStandartPrice() != null){
                 tarif = (event.getStandartPrice() * promotion);
             }

            for (CreateTicket createTicket : tickets) {
                Ticket ticket = new Ticket(
                        createTicket.getName(),
                        createTicket.getFirstName(),
                        event,
                        user,
                        event.getDateEvent()
                );
                boughtTickets.add(repository.save(ticket));
            }
            String type = "Achat par lot de ticket " + tickets.size() + " tickets";
            Operation op = new Operation(type, date, user);
            operationService.createOperation(op);
            evenementService.updateSeatsAvailable(eventId, tickets.size());
            return true;
        }
        return false;
    }


    @Transactional
    public Boolean CancelTicket (String id){
        System.out.println("ticket supprimée");
        Ticket ticket = findTicketById(id);
        LocalDateTime date = LocalDateTime.now();
        if(ticket != null && date.isBefore(ticket.getDate())) {
            repository.deleteById(id);
            Evenement evenement = ticket.getEvenement();
            evenementService.updateSeatsAvailable(evenement.getUuid(), 1);
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
            Evenement evenement = ticket.getEvenement();
            evenementService.updateSeatsAvailable(evenement.getUuid(), 1);
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
