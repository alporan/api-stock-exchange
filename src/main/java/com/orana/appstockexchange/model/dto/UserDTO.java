package com.orana.appstockexchange.model.dto;

import com.orana.appstockexchange.model.entity.AppUser;

import java.util.Set;
import java.util.stream.Collectors;

public record UserDTO(String username, Set<String> roles) {

    public static UserDTO convert(AppUser user){
        return new UserDTO(
                user.getUsername(),
                user.getAppUserRoles().stream()
                        .map(userRole -> userRole.getRole().getAuthority())
                        .collect(Collectors.toSet())
        );
    }
}
