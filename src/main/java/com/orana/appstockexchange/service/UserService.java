package com.orana.appstockexchange.service;

import com.orana.appstockexchange.config.GlobalExceptionHandler;
import com.orana.appstockexchange.model.entity.AppUser;
import com.orana.appstockexchange.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Set<AppUser> findUsers() {
        return userRepository.findUsers();
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(GlobalExceptionHandler.MESSAGE_USER_NOT_FOUND));
    }
}

