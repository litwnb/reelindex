package com.litwnb.reelindex.service;

import com.litwnb.reelindex.entity.User;
import com.litwnb.reelindex.mapper.UserMapper;
import com.litwnb.reelindex.model.UserDTO;
import com.litwnb.reelindex.repository.UserRepository;
import com.litwnb.reelindex.util.UsernameOccupiedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    @Transactional
    public UserDTO register(UserDTO user) {
        String username = user.getUsername();
        User userInDb = userRepository.findByUsername(username);
        if (userInDb != null && userInDb.getUsername().equals(username))
            throw new UsernameOccupiedException();

        user.setPassword(encoder.encode(user.getPassword()));
        return userMapper.userToUserDto(userRepository.save(userMapper.userDtoToUser(user)));
    }

    public UserDTO getUser(String username) {
        return userMapper.userToUserDto(userRepository.findByUsername(username));
    }

    @Transactional
    public void changePassword(String username, String currentPassword, String newPassword, String confirmNewPassword) {
        User user = userRepository.findByUsername(username);

        if (!encoder.matches(currentPassword, user.getPassword()))
            throw new IllegalArgumentException("Current password is incorrect");

        if (!newPassword.equals(confirmNewPassword))
            throw new IllegalArgumentException("New password confirmation doesn't match");

        if (encoder.matches(newPassword, user.getPassword()))
            throw new IllegalArgumentException("New password must be different from the current one");

        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }
}
