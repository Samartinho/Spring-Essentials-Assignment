package springEssentialsAssignment.main.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import springEssentialsAssignment.main.model.User;
import springEssentialsAssignment.main.model.UserPrincipal;
import springEssentialsAssignment.main.repository.UserRepository;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;


@ExtendWith(MockitoExtension.class)
class UserUnitServiceTest {

    @Mock private UserRepository repository;
    private UserService test;

    @BeforeEach
    void setUp(){
        test = new UserService(repository);
    }

    @Test
    void itShouldGetAllUsers() {
        //when
        test.getAllUsers();
        //then
        Mockito.verify(repository).findAll();
    }

    @Test
    void itShouldCreateUser() {
        //given
        User user = new User((long) 1, "Gabriel", "Olivera", "gabriel@gmail.com", "asd", new Date(Date.UTC(2001,12,24,0,0,0)));

        //when
        test.createUser(user);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(repository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser, user);
    }

    @Test
    void itShouldAttUserWithPassword() {
        //given
        User user = new User((long) 1, "Mateus", "Olivera", "gabriel@gmail.com", "asd", new Date(Date.UTC(2001,12,24,0,0,0)));
        UserPrincipal userPrincipal = new UserPrincipal(user);

        //when
        test.attUser(user, userPrincipal);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(repository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser, user);
    }

    @Test
    void itShouldAttUserWithoutPassword() {
        //given
        User user = new User((long) 1, "Mateus", "Olivera", "gabriel@gmail.com", "", new Date(Date.UTC(2001,12,24,0,0,0)));
        UserPrincipal userPrincipal = new UserPrincipal(user);

        //when
        test.attUser(user, userPrincipal);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(repository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser, user);
    }

    @Test
    void deleteUser() {
        //given
        User user = new User((long) 1, "Gabriel", "Olivera", "gabriel@gmail.com", "asd", new Date(Date.UTC(2001,12,24,0,0,0)));

        //when
        test.deleteUser(user.getId());

        //then
        ArgumentCaptor<Long> userArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(repository).deleteById(userArgumentCaptor.capture());
        long capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser, user.getId());
    }

    @Test
    void findUserByEmail() {
        //given
        User user = new User((long) 1, "Gabriel", "Olivera", "gabriel@gmail.com", "asd", new Date(Date.UTC(2001,12,24,0,0,0)));

        //when
        test.findUserByEmail(user.getEmail());

        //then
        ArgumentCaptor<String> userArgumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(repository).findByEmail(userArgumentCaptor.capture());
        String capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser, user.getEmail());
    }
}