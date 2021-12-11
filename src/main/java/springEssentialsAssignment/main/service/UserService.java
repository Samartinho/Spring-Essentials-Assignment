package springEssentialsAssignment.main.service;


import org.springframework.stereotype.Service;
import springEssentialsAssignment.main.model.User;
import springEssentialsAssignment.main.model.UserPrincipal;
import springEssentialsAssignment.main.repository.UserRepository;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository){ this.repository = repository; }

    public List<User> getAllUsers() {
        List<User> result = (List<User>) repository.findAll();

        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<>();
        }
    }

    public User createUser(User al) {
        al = repository.save(al);
        return al;
    }

    public void attUser(User user, UserPrincipal us){
        if(!user.getPassword().equals("")) {
            us.getUser().setName(user.getName());
            us.getUser().setSecondName(user.getSecondName());
            us.getUser().setEmail(user.getEmail());
            us.getUser().setPassword(user.getPassword());
            us.getUser().setBirth(user.getBirth());
            repository.save(us.getUser());
        }
        else
        {
            us.getUser().setName(user.getName());
            us.getUser().setSecondName(user.getSecondName());
            us.getUser().setEmail(user.getEmail());
            us.getUser().setBirth(user.getBirth());
            repository.save(us.getUser());
        }
    }

    public void deleteUser(long Id){
        repository.deleteById(Id);
    }

    @Transactional
    public Optional<User> findUserByEmail(String email){
        return repository.findByEmail(email);
    }

}
