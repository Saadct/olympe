package saad.projet.jo.service;

import jakarta.persistence.GenerationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saad.projet.jo.constants.Role;
import saad.projet.jo.dto.RegisterDto;
import saad.projet.jo.dto.user.GetUserDto;
import saad.projet.jo.dto.user.UpdatePasswordDto;
import saad.projet.jo.dto.user.UpdateUserDto;
import saad.projet.jo.model.Operation;
import saad.projet.jo.model.User;
import saad.projet.jo.repository.OperationRepository;
import saad.projet.jo.repository.UserRepository;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository repository;
    private final OperationRepository operationRepository;

    @Autowired
    public UserService(UserRepository repository,
                       OperationRepository operationRepository

    ){
        this.repository = repository;
        this.operationRepository = operationRepository;
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    public User findById(String id) {
        return repository.findById(id).orElse(null);
    }


    public GetUserDto findUserById(String id) {
        User user = repository.findById(id).orElse(null);
        return new GetUserDto(user.getEmail(),user.getFullName());
    }


    public Boolean UpdateUser(UpdateUserDto newUser, String email, String id){
        User userToUpdate = findById(id);
        LocalDateTime date = LocalDateTime.now();
        if(userToUpdate != null) {
            if (newUser.getEmail() != userToUpdate.getUsername()){
                userToUpdate.setEmail(newUser.getEmail());
            }
            if(newUser.getFullName() != userToUpdate.getFullName())
            {
                userToUpdate.setFullName(newUser.getFullName());
            }
            repository.save(userToUpdate);
            return true;
        }
        return false;
    };



    public boolean UpdatePassword(String id, UpdatePasswordDto updatePassword){
        User userToUpdate = findById(id);
        if(userToUpdate != null){
            userToUpdate.setPasword(updatePassword.getPassword());
            repository.save(userToUpdate);
            return true;
        }
        return false;

    }

    public Boolean createUserAdmin(RegisterDto registerUser, String email) {
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println("User créer");

        User user = new User();
        user.setEmail(registerUser.getEmail());
        user.setPasword(registerUser.getPassword());
        try {
            repository.save(user);
            Operation op = new Operation("creation User", dateTime, findByEmail(email));
            operationRepository.save(op);
            return true;
        } catch (Exception e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        return false;
    }

    public Boolean togleRole(String Id, String email) {
        LocalDateTime dateTime = LocalDateTime.now();
        String action = "";
        try {
            User user = findById(Id);
            if(user.getRole() == Role.ADMIN){
                user.setRole(Role.USER);
                action = "changement role admin to user";
            }else if(user.getRole() == Role.USER){
                user.setRole(Role.ADMIN);
                action = "changement role user to admin";
            }
            repository.save(user);
            Operation op = new Operation(action, dateTime, findByEmail(email));
            operationRepository.save(op);
            return true;
        } catch (Exception e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        return false;
    }


    public Boolean deleteUser(String Id, String email) {
        LocalDateTime dateTime = LocalDateTime.now();
        String action = "Suppression de l'utilisateur";
        try {
            User user = findById(Id);
            repository.delete(user);
            Operation op = new Operation(action, dateTime, findByEmail(email));
            operationRepository.save(op);
            return true;
        } catch (Exception e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        return false;
    }




}
