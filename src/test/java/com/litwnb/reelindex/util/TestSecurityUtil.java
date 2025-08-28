package com.litwnb.reelindex.util;

import com.litwnb.reelindex.entity.User;
import com.litwnb.reelindex.model.UserPrincipal;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.UUID;

public class TestSecurityUtil {
    public static RequestPostProcessor testUser() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .username("john")
                .password("password")
                .build();
        UserPrincipal principal = new UserPrincipal(user);
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(principal,
                        principal.getPassword(),
                        principal.getAuthorities()
                );

        return SecurityMockMvcRequestPostProcessors.authentication(auth);
    }
}
