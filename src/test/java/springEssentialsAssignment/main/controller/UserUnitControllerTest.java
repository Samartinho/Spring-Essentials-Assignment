package springEssentialsAssignment.main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import springEssentialsAssignment.main.model.User;
import springEssentialsAssignment.main.model.UserPrincipal;
import springEssentialsAssignment.main.repository.UserRepository;
import springEssentialsAssignment.main.service.UserService;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;



@WebMvcTest(UserController.class)
class UserUnitControllerTest {

    @Autowired private MockMvc mvc;
    @Autowired private UserController test;
    @MockBean private UserService service;
    @MockBean private UserRepository repository;
    @Autowired private WebApplicationContext wac;
    @Autowired private ObjectMapper om;
    private User user;
    private Optional<User> us;

    @BeforeEach
    void setUp(){
        RestAssuredMockMvc.standaloneSetup(this.test);
        User user = new User((long) 1, "Gabriel", "Olivera", "gabriel@gmail.com", "asd", new Date(Date.UTC(2001,12,24,0,0,0)));
        this.user = user;
        test.setUserPrincipal(new UserPrincipal(user));
        us = Optional.of(user);
        mvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    void AddUserGet() throws Exception {
        Mockito.when(service.createUser(user)).thenReturn(new User((long) 1, "Gabriel", "Olivera", "gabriel@gmail.com", "asd", new Date(Date.UTC(2001, 12, 24, 0, 0, 0))));
        mvc.perform(get("/add-User"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void PrintUsersGet() throws Exception {
        Mockito.when(service.getAllUsers()).thenReturn(new ArrayList<>());
        mvc.perform(get("/print-Users").with(SecurityMockMvcRequestPostProcessors.user("Gabriel").roles("USER")))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void handleErrorGet() throws Exception {
        mvc.perform(get("/error"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void homeUserGet() throws Exception {
        mvc.perform(get("/home").with(SecurityMockMvcRequestPostProcessors.user("Gabriel").roles("USER")))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void logInGet() throws Exception{
        mvc.perform(get("/logIn"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void editUserGet() throws Exception {
        mvc.perform(get("/edit-User").with(SecurityMockMvcRequestPostProcessors.user("Gabriel").roles("USER")))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteUserGet() throws Exception {
        mvc.perform(get("/delete-User").with(SecurityMockMvcRequestPostProcessors.user("Gabriel").roles("USER")))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isFound());
    }

    @Test
    void createUser() throws Exception {
        Mockito.when(service.findUserByEmail(user.getEmail())).thenReturn(Optional.empty());
        mvc.perform(post("/add-User"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isFound());

    }

    @Test
    void edit() throws Exception {
        mvc.perform(post("/edit").with(SecurityMockMvcRequestPostProcessors.user("Gabriel").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(user)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void verifyEmailFalse() {
        Mockito.when(service.findUserByEmail(user.getEmail())).thenReturn(us);
        assertFalse(test.verifyEmail(user.getEmail()));
    }

    @Test
    void verifyEmailTrue() {
        test.verifyEmail(user.getEmail());
        Mockito.when(service.findUserByEmail(user.getEmail())).thenReturn(Optional.empty());
        assertFalse(test.verifyEmail(user.getEmail()));
    }
}