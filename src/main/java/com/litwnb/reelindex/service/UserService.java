package com.litwnb.reelindex.service;

import com.litwnb.reelindex.entity.User;
import com.litwnb.reelindex.repository.UserRepository;
import com.litwnb.reelindex.util.UsernameOccupiedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public User register(User user) {
        String username = user.getUsername();
        User userInDb = userRepository.findByUsername(username);
        if (userInDb != null && userInDb.getUsername().equals(username))
            throw new UsernameOccupiedException();

        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
