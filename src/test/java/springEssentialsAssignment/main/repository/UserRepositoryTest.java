package springEssentialsAssignment.main.repository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import springEssentialsAssignment.main.model.User;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired private UserRepository test;

    @AfterEach
    void tearDown(){
        test.deleteAll();
    }

    @Test
    void itShouldfindUserByEmailTest() {
        //given
        User user = new User((long) 1, "Gabriel", "Olivera", "gabriel@gmail.com", "asd", new Date(Date.UTC(2001,12,24,0,0,0)));
        test.save(user);

        //when
        Optional<User> foundedUser = test.findByEmail("gabriel@gmail.com");

        //then
        assertEquals(foundedUser.get().getEmail(), "gabriel@gmail.com");
    }

    @Test
    void itShouldNotFindUserByEmail(){
        //given
        String email = "gabriel@gmail.com";

        //when
        Optional<User> user = test.findByEmail(email);

        //then
        assertEquals(user, Optional.empty());
    }
}