package saad.projet.jo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saad.projet.jo.constants.State;
import saad.projet.jo.dto.evenement.CreateEventDto;
import saad.projet.jo.dto.evenement.UpdateEventDto;
import saad.projet.jo.model.Evenement;
import saad.projet.jo.repository.EvenementRepository;
import saad.projet.jo.repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EvenementService {

    private final EvenementRepository repository;
    private final OperationService operationService;
    private final CategoryService categoryService;
    private final TicketRepository ticketRepository;

    @Autowired
    public EvenementService(EvenementRepository repository,
                            OperationService operationService,
                            CategoryService categoryService,
                            TicketRepository ticketRepository

    ) {
        this.operationService = operationService;
        this.repository = repository;
        this.categoryService = categoryService;
        this.ticketRepository = ticketRepository;
    }

    public List<Evenement> findAllEvenement() {
        System.out.println("Toutes les evenements");
       // Pageable paging = PageRequest.of(0, 10); // Always return the first 10 elements
        return repository.findAll();
    }


    public Page<Evenement> findPaginatedEvenements(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return repository.findByDateAfter(paging);
    }

    public Page<Evenement> findByCategoryPaginatedEvenements(String id, int page, int size) {
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

    public List<Evenement> findAllEvenementByState(String state) {
        System.out.println("Toutes les evenements");
        return repository.findByState("Cancel");
    }

    public Evenement findEvenementById(String uuid) {
        return repository.findOneByUuid(uuid).orElse(null);
    }


    /*
    public Boolean createEvenement(Evenement evenement) {
        System.out.println("Evenement créer");
         repository.save(evenement);
         return true;
    }
    */


    public Boolean createEvenement(CreateEventDto createEvent, String email) {
        System.out.println("Evenement créer");
        LocalDateTime date = LocalDateTime.now();

        try {
            Evenement evenement = new Evenement();
            evenement.setName(createEvent.getName());
            evenement.setTotalSeats(createEvent.getTotalSeats());
            evenement.setAvailableSeats(createEvent.getTotalSeats());
        //    evenement.setStandartPrice(createEvent.getStandartPrice());
            evenement.setDateEvent(createEvent.getDateEvent());
            evenement.setHourBegin(createEvent.getHourBegin());
            evenement.setHourEnding(createEvent.getHourEnding());
            evenement.setDateCreate(date);
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
        Evenement evenement = findEvenementById(uuid);
        if(evenement.getAvailableSeats() > 0){
            return true;
        }
        return false;
    }

 //   @Transactional
    public Boolean deleteEvenement(String id) {
        System.out.println("evenement supprimée");
        Evenement evenementASupr = findEvenementById(id);
        if (evenementASupr != null) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public Boolean updateEvenement(String id, UpdateEventDto updateEvent, String email) {
        Evenement evenementAModifier = findEvenementById(id);
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
            evenementAModifier.setDateLastUpdate(date);
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
        Evenement evenement = findEvenementById(uuid);
        long coutInscription = ticketRepository.countByEvenement(evenement);
        Integer availableSeat = evenement.getTotalSeats() - (int)coutInscription;
        evenement.setAvailableSeats(availableSeat);
        repository.save(evenement);
    }

    public boolean updateTotalSeats(String uuid, Integer seat, String email) {
        LocalDateTime date = LocalDateTime.now();
        try {
            Evenement evenement = findEvenementById(uuid);
          /*  long incription = ticketRepository.countByEvenement(evenement);
            Integer totalSeats = evenement.getTotalSeats();
            Integer diff = evenement.getTotalSeats() - seat;
            Integer seatNew = evenement.getTotalSeats() + seat;
           */
            evenement.setTotalSeats(evenement.getTotalSeats());
            evenement.setAvailableSeats(evenement.getAvailableSeats() );
            evenement.setDateLastUpdate(date);
            repository.save(evenement);
            return true;

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }



    public boolean cancelEvent(String Id, String mail) {
        LocalDateTime date = LocalDateTime.now();
        Evenement evenement = findEvenementById(Id);
        evenement.setState("Cancel");
        evenement.setDateLastUpdate(date);
        operationService.recordAction("Cancel evenement",date, mail);
// todo les remboursement
        return true;
    }

    public boolean openEvent(String Id, String mail) {
        LocalDateTime date = LocalDateTime.now();
        Evenement evenement = findEvenementById(Id);
        evenement.setState("Cancel");
        evenement.setDateLastUpdate(date);
        operationService.recordAction("Cancel evenement",date, mail);
// todo les remboursement
        return true;
    }

    public boolean closeEventToBooking(String Id, String mail) {
        Evenement evenement = findEvenementById(Id);
        evenement.setState("Close to booking");
        return true;
    }


}
