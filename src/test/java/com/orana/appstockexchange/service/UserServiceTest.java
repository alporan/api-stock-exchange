package com.orana.appstockexchange.service;

import com.orana.appstockexchange.model.entity.AppUser;
import com.orana.appstockexchange.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void givenCorrectUsername_whenLoadUserByUsername_thenReturnAppUser() {
        // given
        when(userRepository.findUserByUsername(anyString())).thenReturn(Optional.of(new AppUser()));

        // when
        userService.loadUserByUsername("USER");

        // then
        verify(userRepository, times(1)).findUserByUsername("USER");
    }

    @Test
    void givenMissingUser_whenLoadUserByUsername_thenThrowUsernameNotFoundEx() {
        // given
        when(userRepository.findUserByUsername(anyString())).thenReturn(Optional.empty());

        // when
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("USER"));

        // then
        assertNotNull(exception);
        verify(userRepository, times(1)).findUserByUsername(anyString());
    }

    @Test
    void whenFindUsers_thenReturnAppUsers() {
        // when
        userService.findUsers();

        // then
        verify(userRepository, times(1)).findUsers();
    }
}