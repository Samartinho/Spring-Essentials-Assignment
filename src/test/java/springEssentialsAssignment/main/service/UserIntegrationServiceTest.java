package springEssentialsAssignment.main.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springEssentialsAssignment.main.model.User;
import springEssentialsAssignment.main.model.UserPrincipal;
import springEssentialsAssignment.main.repository.UserRepository;
import springEssentialsAssignment.main.service.UserService;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
class UserIntegrationServiceTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void testCreateUser() {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setBirth(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setSecondName("Second Name");
        when(this.userRepository.save((User) any())).thenReturn(user);

        User user1 = new User();
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setBirth(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setEmail("jane.doe@example.org");
        user1.setId(123L);
        user1.setName("Name");
        user1.setPassword("iloveyou");
        user1.setSecondName("Second Name");
        assertSame(user, this.userService.createUser(user1));
        verify(this.userRepository).save((User) any());
        assertTrue(this.userService.getAllUsers().isEmpty());
    }

    @Test
    void testAttUser() {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setBirth(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setSecondName("Second Name");
        when(this.userRepository.save((User) any())).thenReturn(user);

        User user1 = new User();
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setBirth(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setEmail("jane.doe@example.org");
        user1.setId(123L);
        user1.setName("Name");
        user1.setPassword("iloveyou");
        user1.setSecondName("Second Name");
        UserPrincipal userPrincipal = new UserPrincipal(new User());
        this.userService.attUser(user1, userPrincipal);
        verify(this.userRepository).save((User) any());
        User user2 = userPrincipal.getUser();
        assertEquals(
                "User(Id=null, name=Name, secondName=Second Name, email=jane.doe@example.org, password=iloveyou, birth=Wed"
                        + " Dec 31 21:00:00 BRT 1969)",
                user2.toString());
        assertEquals("Second Name", user2.getSecondName());
        assertEquals("iloveyou", user2.getPassword());
        assertEquals("Name", user2.getName());
        assertEquals("jane.doe@example.org", user2.getEmail());
        assertTrue(this.userService.getAllUsers().isEmpty());
    }

    @Test
    void testAttUser2() {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setBirth(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setSecondName("Second Name");
        when(this.userRepository.save((User) any())).thenReturn(user);

        User user1 = new User();
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setBirth(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setEmail("jane.doe@example.org");
        user1.setId(123L);
        user1.setName("Name");
        user1.setPassword("");
        user1.setSecondName("Second Name");
        UserPrincipal userPrincipal = new UserPrincipal(new User());
        this.userService.attUser(user1, userPrincipal);
        verify(this.userRepository).save((User) any());
        User user2 = userPrincipal.getUser();
        assertEquals("User(Id=null, name=Name, secondName=Second Name, email=jane.doe@example.org, password=null, birth=Wed"
                + " Dec 31 21:00:00 BRT 1969)", user2.toString());
        assertEquals("Second Name", user2.getSecondName());
        assertEquals("Name", user2.getName());
        assertEquals("jane.doe@example.org", user2.getEmail());
        assertTrue(this.userService.getAllUsers().isEmpty());
    }

    @Test
    void testAttUser3() {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setBirth(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setSecondName("Second Name");
        when(this.userRepository.save((User) any())).thenReturn(user);

        User user1 = new User();
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setBirth(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setEmail("jane.doe@example.org");
        user1.setId(123L);
        user1.setName("Name");
        user1.setPassword("iloveyou");
        user1.setSecondName("Second Name");
        User user2 = mock(User.class);
        doNothing().when(user2).setBirth((Date) any());
        doNothing().when(user2).setPassword((String) any());
        doNothing().when(user2).setEmail((String) any());
        doNothing().when(user2).setSecondName((String) any());
        doNothing().when(user2).setName((String) any());
        UserPrincipal userPrincipal = new UserPrincipal(user2);
        this.userService.attUser(user1, userPrincipal);
        verify(this.userRepository).save((User) any());
        verify(user2).setBirth((Date) any());
        verify(user2).setEmail((String) any());
        verify(user2).setName((String) any());
        verify(user2).setPassword((String) any());
        verify(user2).setSecondName((String) any());
        assertNull(userPrincipal.getUsername());
        assertTrue(this.userService.getAllUsers().isEmpty());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(this.userRepository).deleteById((Long) any());
        this.userService.deleteUser(123L);
        verify(this.userRepository).deleteById((Long) any());
        assertTrue(this.userService.getAllUsers().isEmpty());
    }

    @Test
    void testFindUserByEmail() {
        User user = new User();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setBirth(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEmail("jane.doe@example.org");
        user.setId(123L);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setSecondName("Second Name");
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findByEmail((String) any())).thenReturn(ofResult);
        Optional<User> actualFindUserByEmailResult = this.userService.findUserByEmail("jane.doe@example.org");
        assertSame(ofResult, actualFindUserByEmailResult);
        assertTrue(actualFindUserByEmailResult.isPresent());
        verify(this.userRepository).findByEmail((String) any());
        assertTrue(this.userService.getAllUsers().isEmpty());
    }
}

