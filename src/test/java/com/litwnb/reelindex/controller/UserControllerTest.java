package com.litwnb.reelindex.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.litwnb.reelindex.config.SecurityConfiguration;
import com.litwnb.reelindex.model.UserDTO;
import com.litwnb.reelindex.service.UserService;
import com.litwnb.reelindex.util.TestSecurityUtil;
import com.litwnb.reelindex.util.UsernameOccupiedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(SecurityConfiguration.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Test
    void register_validUser_returnsUser() throws Exception {
        UserDTO dto = UserDTO.builder().username("john").password("password").build();
        when(userService.register(any(UserDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/api/user/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("john"));
    }

    @Test
    void register_duplicateUser_returnsBadRequest() throws Exception {
        UserDTO dto = UserDTO.builder().username("john").password("password").build();
        when(userService.register(any(UserDTO.class))).thenThrow(new UsernameOccupiedException());

        mockMvc.perform(post("/api/user/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCurrentUser_returnsUser() throws Exception {
        UserDTO dto = UserDTO.builder().username("john").password("password").build();
        when(userService.getUser("john")).thenReturn(dto);

        mockMvc.perform(get("/api/user")
                        .with(TestSecurityUtil.testUser()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john"));
    }

    @Test
    void changePassword_success() throws Exception {
        String requestJson = """
                {"currentPassword":"old123","newPassword":"new123","confirmNewPassword":"new123"}
                """;

        mockMvc.perform(post("/api/user/change-password")
                        .with(TestSecurityUtil.testUser())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Password successfully changed."));
    }
}
