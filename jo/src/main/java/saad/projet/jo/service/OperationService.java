package saad.projet.jo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saad.projet.jo.constants.State;
import saad.projet.jo.model.Operation;
import saad.projet.jo.model.User;
import saad.projet.jo.repository.OperationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OperationService {

    private final OperationRepository repository;
    private final UserService userService;

    @Autowired
    public OperationService(OperationRepository repository, UserService userService)
    {
        this.repository = repository;
        this.userService = userService;
    }

    public List<Operation> findAllOperation () {
        System.out.println("Toutes les evenements");
        return repository.findAll();
    }

    public Operation findOperatonById(String uuid) {
        return repository.findOneByUuid(uuid).orElse(null);
    }

    public Operation createOperation (Operation op){
        System.out.println("Operation créer");
        return repository.save(op);
    }

    @Transactional
    public Boolean deleteOperation (String id){
        System.out.println("operation supprimée");
        Operation op = findOperatonById(id);
        if(op != null) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
        public boolean updateOperation (String id, Operation op){
        System.out.println("op up");

        Operation opModification = findOperatonById(id);
        if(opModification != null) {
            opModification.setDate(op.getDate());
            opModification.setType(op.getType());
            repository.save(opModification);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean recordAction (String actionType, LocalDateTime date, String email){
        User user = userService.findByEmail(email);
        if(user != null) {
            Operation op = new Operation(actionType, date, user);
            repository.save(op);
            return true;
        }
        return false;
        }



}
