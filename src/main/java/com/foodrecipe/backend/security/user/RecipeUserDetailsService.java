package com.foodrecipe.backend.security.user;

import com.foodrecipe.backend.model.User;
import com.foodrecipe.backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class RecipeUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public RecipeUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        return RecipeUserDetails.buildUserDetails(user);
    }
}
