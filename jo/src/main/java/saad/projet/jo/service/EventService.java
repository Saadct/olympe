package saad.projet.jo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saad.projet.jo.dto.event.CreateEventDto;
import saad.projet.jo.dto.event.UpdateEventDto;
import saad.projet.jo.model.Event;
import saad.projet.jo.model.Ticket;
import saad.projet.jo.repository.EventRepository;
import saad.projet.jo.repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final EventRepository repository;
    private final OperationService operationService;
    private final CategoryService categoryService;
    private final TicketRepository ticketRepository;

    @Autowired
    public EventService(EventRepository repository,
                            OperationService operationService,
                            CategoryService categoryService,
                            TicketRepository ticketRepository

    ) {
        this.operationService = operationService;
        this.repository = repository;
        this.categoryService = categoryService;
        this.ticketRepository = ticketRepository;
    }

    public List<Event> findAllEvenement() {
        System.out.println("Toutes les evenements");
       // Pageable paging = PageRequest.of(0, 10); // Always return the first 10 elements
        return repository.findAll();
    }


    public Page<Event> findPaginatedEvenements(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return repository.findByDateAfter(paging);
    }

    public Page<Event> findByCategoryPaginatedEvenements(String id, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return repository.findByCategory(categoryService.findCategoryById(id), paging);
    }

    public int getTotalPage(int size) {
    long totalItems = repository.countByDateAfter();
    return (int) Math.ceil((double) totalItems / size);
    }

    public int getTotalPageByCategory(String uuid, int size) {
        long totalItems = repository.countByDateAfterByCategory(categoryService.findCategoryById(uuid));
        return (int) Math.ceil((double) totalItems / size);
    }


    public Event findEvenementById(String uuid) {
        return repository.findOneByUuid(uuid).orElse(null);
    }


    public Boolean createEvenement(CreateEventDto createEvent, String email) {
        System.out.println("Evenement créer");
        LocalDateTime date = LocalDateTime.now();
        try {
            Event evenement = new Event();
            evenement.setName(createEvent.getName());
            evenement.setTotalSeats(createEvent.getTotalSeats());
            evenement.setAvailableSeats(createEvent.getTotalSeats());
            evenement.setDateEvent(createEvent.getDateEvent());
            evenement.setHourBegin(createEvent.getHourBegin());
            evenement.setHourEnding(createEvent.getHourEnding());
            evenement.setCreatedAt(date);
            evenement.setLongDescription(createEvent.getLongDescription());
            evenement.setShortDescription(createEvent.getShortDescription());
            try {
                evenement.setCategory(categoryService.findCategoryById(createEvent.getCategoryId()));
            }catch (Exception e){
                System.err.println("Erreur lors de l'ajout de la category à l'evenemment: " + e.getMessage());
            }

            repository.save(evenement);
            operationService.recordAction("creation d'evenement", date, email);
            return true;
        } catch (Exception e) {
            System.err.println("Erreur lors de la création de l'événement: " + e.getMessage());
            return false;
        }
    }


    public Boolean checkAvailableEvent(String uuid){
        Event evenement = findEvenementById(uuid);
        if(evenement.getAvailableSeats() == 0){
            return false;
        }
        return true;
    }

    public Boolean checkIfCategoryHasNoEvent(String uuid){
        List<Event> events = repository.findAllByCategory(categoryService.findCategoryById(uuid));
        if (events.size() == 0) {
            return true;
        }
        return false;
    }

    public Boolean deleteEvenement(String id) {
        Event evenementASupr = findEvenementById(id);
        if (evenementASupr != null) {
           List<Ticket> tickets = ticketRepository.findAllByEvenement(evenementASupr);
           for (Ticket ticket : tickets) {
                ticketRepository.delete(ticket);
            }
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public Boolean updateEvenement(String id, UpdateEventDto updateEvent, String email) {
        Event evenementAModifier = findEvenementById(id);
        LocalDateTime date = LocalDateTime.now();
        long coutInscription = ticketRepository.countByEvenement(evenementAModifier);
        int totalseat = 0;

        if(updateEvent.getTotalSeats() < coutInscription){
            return false;
        }
        try {
            evenementAModifier.setName(updateEvent.getName());
            evenementAModifier.setDateEvent(updateEvent.getDateEvent());
            evenementAModifier.setHourBegin(updateEvent.getHourBegin());
            evenementAModifier.setHourEnding(updateEvent.getHourEnding());
            evenementAModifier.setUpdateAt(date);
            evenementAModifier.setShortDescription(updateEvent.getShortDescription());
            evenementAModifier.setLongDescription(updateEvent.getLongDescription());


            evenementAModifier.setTotalSeats(updateEvent.getTotalSeats());
            evenementAModifier.setAvailableSeats(updateEvent.getTotalSeats() - (int)coutInscription );


            try {
                evenementAModifier.setCategory(categoryService.findCategoryById(updateEvent.getCategoryId()));
            } catch (Exception e) {
                System.err.println("Erreur lors de la mdoficiation de la category à l'evenemment: " + e.getMessage());
            }

            repository.save(evenementAModifier);
            operationService.recordAction("Modification d'évenement", date, email);
            return true;
        }
            catch (Exception e) {
                System.err.println("Erreur lors de la modification de l'événement: " + e.getMessage());
                return false;
            }
    }


    public void updateSeatsAvailable(String uuid) {
        Event evenement = findEvenementById(uuid);
        long coutInscription = ticketRepository.countByEvenement(evenement);
        Integer availableSeat = evenement.getTotalSeats() - (int)coutInscription;
        evenement.setAvailableSeats(availableSeat);
        repository.save(evenement);
    }





}
