package com.lordscave.societyxapi.core.security;

import com.lordscave.societyxapi.core.entity.enums.UserStatus;
import com.lordscave.societyxapi.core.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import com.lordscave.societyxapi.core.entity.User;

@Service
@RequiredArgsConstructor
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        if (!user.getStatus().equals(UserStatus.ACTIVE)) {
            throw new UsernameNotFoundException("User account is not active");
        }

        return new CustomUserDetails(user);
    }
}
