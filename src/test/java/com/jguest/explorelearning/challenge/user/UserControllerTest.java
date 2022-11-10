package com.jguest.explorelearning.challenge.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jguest.explorelearning.challenge.security.SecurityConfiguration;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SecurityConfiguration.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testGetUsersSuccess() throws Exception {
        Mockito.when(userRepository.findAll(any(Sort.class))).thenReturn(Arrays.asList(user1(), user2()));
        mockMvc.perform(get("/v1/users").header("X-API-KEY", "113"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    public void testGetUsersFailure() throws Exception {
        Mockito.when(userRepository.findAll(any(Sort.class))).thenAnswer(invocationOnMock -> {
            throw new Exception();
        });
        mockMvc.perform(get("/v1/users").header("X-API-KEY", "113"))
            .andExpect(status().is(500));
    }

    @Test
    public void testGetUserExists() throws Exception {
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user1()));
        mockMvc.perform(get("/v1/users/1").header("X-API-KEY", "113"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", Matchers.is(1)))
            .andExpect(jsonPath("$.firstName", Matchers.is("John")));
    }

    @Test
    public void testGetUserNotExists() throws Exception {
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.empty());
        mockMvc.perform(get("/v1/users/1").header("X-API-KEY", "113"))
                .andExpect(status().is(404));
    }

    @Test
    public void testGetUserFailure() throws Exception {
        Mockito.when(userRepository.findById(1)).thenAnswer(invocationOnMock -> {
            throw new Exception();
        });
        mockMvc.perform(get("/v1/users/1").header("X-API-KEY", "113"))
            .andExpect(status().is(500));
    }

    @Test
    public void testCreateUserSuccess() throws Exception {
        User user = user1();
        String userJson = new ObjectMapper().writeValueAsString(user);

        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        mockMvc.perform(post("/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson)
            .header("X-API-KEY", "113"))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.firstName", Matchers.is(user.getFirstName())));
    }

    @Test
    public void testCreateUserFailure() throws Exception {
        User user = user1();
        String userJson = new ObjectMapper().writeValueAsString(user);

        Mockito.when(userRepository.save(any(User.class))).thenAnswer(invocationOnMock -> {
            throw new Exception();
        });
        mockMvc.perform(post("/v1/users").header("X-API-KEY", "113")
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson))
                .andExpect(status().is(500));
    }

    @Test
    public void testDeleteUserExists() throws Exception {
        User user = user1();
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Mockito.doNothing().when(userRepository).delete(user);
        mockMvc.perform(delete("/v1/users/1").header("X-API-KEY", "113"))
            .andExpect(status().is(204));
    }

    @Test
    public void testDeleteUserNotExists() throws Exception {
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/v1/users/1").header("X-API-KEY", "113"))
            .andExpect(status().is(404));
    }

    @Test
    public void testDeleteUserFailure() throws Exception {
        Mockito.when(userRepository.findById(1)).thenAnswer(invocationOnMock -> {
            throw new Exception();
        });
        mockMvc.perform(delete("/v1/users/1").header("X-API-KEY", "113"))
            .andExpect(status().is(500));
    }

    private User user1() {
        User user = new User();
        user.setId(1);
        user.setFirstName("John");
        user.setLastName("Guest");
        return user;
    }

    private User user2() {
        User user = new User();
        user.setId(1);
        user.setFirstName("Amber");
        user.setLastName("Guest");
        return user;
    }
}
