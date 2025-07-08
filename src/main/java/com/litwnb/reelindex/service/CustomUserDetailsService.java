package com.litwnb.reelindex.service;

import com.litwnb.reelindex.entity.User;
import com.litwnb.reelindex.model.UserPrincipal;
import com.litwnb.reelindex.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException("User not found");

        return new UserPrincipal(user);
    }
}
