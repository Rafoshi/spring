package com.example.sqlite.adapters.in.web;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.sqlite.adapters.in.web.dto.UserResponse;
import com.example.sqlite.adapters.in.web.mapper.UserDtoMapper;
import com.example.sqlite.application.portin.CreateUserUseCase;
import com.example.sqlite.application.portin.ListUsersUseCase;
import com.example.sqlite.domain.User;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ListUsersUseCase listUsersUseCase;

    @MockitoBean
    private CreateUserUseCase createUserUseCase;

    @MockitoBean
    private UserDtoMapper userDtoMapper;

    @Test
    void getAllUsers_returnsMappedList() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("Ioshi");
        user.setEmail("rafa.ioshi@gmail.com");
        UserResponse response = new UserResponse(1L, "Ioshi", "rafa.ioshi@gmail.com");

        when(listUsersUseCase.execute()).thenReturn(List.of(user));
        when(userDtoMapper.toResponseList(List.of(user))).thenReturn(List.of(response));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Ioshi"))
                .andExpect(jsonPath("$[0].email").value("rafa.ioshi@gmail.com"));
    }

    @Test
    void createUser_validPayload_returns201() throws Exception {
        User domainUser = new User();
        domainUser.setName("Ioshi");
        domainUser.setEmail("rafa.ioshi@gmail.com");

        User saved = new User();
        saved.setId(1L);
        saved.setName("Ioshi");
        saved.setEmail("rafa.ioshi@gmail.com");

        UserResponse response = new UserResponse(1L, "Ioshi", "rafa.ioshi@gmail.com");

        when(userDtoMapper.toDomain(any())).thenReturn(domainUser);
        when(createUserUseCase.execute(domainUser)).thenReturn(saved);
        when(userDtoMapper.toResponse(saved)).thenReturn(response);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Ioshi\",\"email\":\"rafa.ioshi@gmail.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("rafa.ioshi@gmail.com"));
    }

    @Test
    void createUser_invalidEmail_returns400AndSkipsUseCase() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Ioshi\",\"email\":\"not-an-email\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("email")));

        verifyNoInteractions(createUserUseCase);
    }

    @Test
    void createUser_blankName_returns400() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"email\":\"valid@example.com\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_duplicateEmail_returns409() throws Exception {
        User domainUser = new User();
        domainUser.setName("Ioshi");
        domainUser.setEmail("rafa.ioshi@gmail.com");

        when(userDtoMapper.toDomain(any())).thenReturn(domainUser);
        when(createUserUseCase.execute(domainUser)).thenThrow(new DataIntegrityViolationException("duplicate"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Ioshi\",\"email\":\"rafa.ioshi@gmail.com\"}"))
                .andExpect(status().isConflict())
                .andExpect(content().string("Email already in use"));
    }
}
