package com.litwnb.reelindex.service;

import com.litwnb.reelindex.entity.User;
import com.litwnb.reelindex.mapper.UserMapper;
import com.litwnb.reelindex.model.UserDTO;
import com.litwnb.reelindex.repository.UserRepository;
import com.litwnb.reelindex.util.UsernameOccupiedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void register_whenUsernameAlreadyExists_throwsException() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("john");
        userDTO.setPassword("password");
        User existing = new User();
        existing.setUsername("john");

        when(userRepository.findByUsername("john")).thenReturn(existing);

        assertThrows(UsernameOccupiedException.class, () -> userService.register(userDTO));
    }

    @Test
    void register_encodesPasswordAndSavesUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("john");
        userDTO.setPassword("password");

        when(userRepository.findByUsername("john")).thenReturn(null);
        when(userMapper.userDtoToUser(any(UserDTO.class))).thenReturn(new User());
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));
        when(userMapper.userToUserDto(any(User.class))).thenReturn(userDTO);

        UserDTO result = userService.register(userDTO);

        assertNotEquals("password", result.getPassword());
    }

    @Test
    void changePassword_currentPasswordIncorrect_throwsException() {
        User user = new User();
        user.setPassword(new BCryptPasswordEncoder().encode("old"));

        when(userRepository.findByUsername("john")).thenReturn(user);

        assertThrows(IllegalArgumentException.class,
                () -> userService.changePassword("john","wrong","new","new"));
    }

    @Test
    void changePassword_confirmationNotMatch_throwsException() {
        User user = new User();
        user.setPassword(new BCryptPasswordEncoder().encode("old"));

        when(userRepository.findByUsername("john")).thenReturn(user);

        assertThrows(IllegalArgumentException.class,
                () -> userService.changePassword("john","old","new","different"));
    }

    @Test
    void changePassword_success_updatesAndSaves() {
        User user = new User();
        user.setPassword(new BCryptPasswordEncoder().encode("old"));

        when(userRepository.findByUsername("john")).thenReturn(user);

        userService.changePassword("john","old","new","new");

        verify(userRepository).save(user);
        assertTrue(new BCryptPasswordEncoder().matches("new", user.getPassword()));
    }
}
