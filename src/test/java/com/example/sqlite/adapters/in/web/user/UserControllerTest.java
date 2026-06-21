package com.example.sqlite.adapters.in.web.user;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.sqlite.adapters.in.web.dto.PagedResponse;
import com.example.sqlite.adapters.in.web.user.dto.CreateUserRequest;
import com.example.sqlite.adapters.in.web.user.dto.UpdateUserRequest;
import com.example.sqlite.adapters.in.web.user.dto.UserResponse;
import com.example.sqlite.adapters.in.web.user.mapper.UserDtoMapper;
import com.example.sqlite.application.portin.user.CreateUserUseCase;
import com.example.sqlite.application.portin.user.DeleteUserUseCase;
import com.example.sqlite.application.portin.user.ListUsersUseCase;
import com.example.sqlite.application.portin.user.UpdateUserUseCase;
import com.example.sqlite.domain.user.User;
import com.example.sqlite.domain.user.UserNotFoundException;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ListUsersUseCase listUsersUseCase;

    @MockitoBean
    private CreateUserUseCase createUserUseCase;

    @MockitoBean
    private UpdateUserUseCase updateUserUseCase;

    @MockitoBean
    private DeleteUserUseCase deleteUserUseCase;

    @MockitoBean
    private UserDtoMapper userDtoMapper;

    @Test
    void getAllUsers_returnsPagedResponse() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("Ioshi");
        user.setEmail("rafa.ioshi@gmail.com");
        UserResponse response = new UserResponse(1L, "Ioshi", "rafa.ioshi@gmail.com");

        Pageable pageable = PageRequest.of(0, 20);
        Page<User> page = new PageImpl<>(List.of(user), pageable, 1);
        PagedResponse<UserResponse> pagedResponse = new PagedResponse<>(List.of(response), 0, 20, 1, 1);

        when(listUsersUseCase.execute(any(Pageable.class))).thenReturn(page);
        when(userDtoMapper.toPagedResponse(page)).thenReturn(pagedResponse);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Ioshi"))
                .andExpect(jsonPath("$.content[0].email").value("rafa.ioshi@gmail.com"))
                .andExpect(jsonPath("$.totalElements").value(1));
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

        when(userDtoMapper.toDomain(any(CreateUserRequest.class))).thenReturn(domainUser);
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

        when(userDtoMapper.toDomain(any(CreateUserRequest.class))).thenReturn(domainUser);
        when(createUserUseCase.execute(domainUser)).thenThrow(new DataIntegrityViolationException("duplicate"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Ioshi\",\"email\":\"rafa.ioshi@gmail.com\"}"))
                .andExpect(status().isConflict())
                .andExpect(content().string("Email already in use"));
    }

    @Test
    void updateUser_validPayload_returns200() throws Exception {
        User domainUser = new User();
        domainUser.setName("Ioshi2");
        domainUser.setEmail("ioshi2@example.com");

        User updated = new User();
        updated.setId(1L);
        updated.setName("Ioshi2");
        updated.setEmail("ioshi2@example.com");

        UserResponse response = new UserResponse(1L, "Ioshi2", "ioshi2@example.com");

        when(userDtoMapper.toDomain(any(UpdateUserRequest.class))).thenReturn(domainUser);
        when(updateUserUseCase.execute(eq(1L), eq(domainUser))).thenReturn(updated);
        when(userDtoMapper.toResponse(updated)).thenReturn(response);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Ioshi2\",\"email\":\"ioshi2@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ioshi2"))
                .andExpect(jsonPath("$.email").value("ioshi2@example.com"));
    }

    @Test
    void updateUser_invalidEmail_returns400AndSkipsUseCase() throws Exception {
        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Ioshi\",\"email\":\"not-an-email\"}"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(updateUserUseCase);
    }

    @Test
    void updateUser_userNotFound_returns404() throws Exception {
        User domainUser = new User();
        domainUser.setName("Ioshi");
        domainUser.setEmail("ioshi@example.com");

        when(userDtoMapper.toDomain(any(UpdateUserRequest.class))).thenReturn(domainUser);
        when(updateUserUseCase.execute(eq(99L), eq(domainUser))).thenThrow(new UserNotFoundException(99L));

        mockMvc.perform(put("/users/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Ioshi\",\"email\":\"ioshi@example.com\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_existingUser_returns204() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUser_userNotFound_returns404() throws Exception {
        org.mockito.Mockito.doThrow(new UserNotFoundException(99L)).when(deleteUserUseCase).execute(99L);

        mockMvc.perform(delete("/users/99"))
                .andExpect(status().isNotFound());
    }
}
