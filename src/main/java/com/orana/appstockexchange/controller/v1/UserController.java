package com.orana.appstockexchange.controller.v1;

import com.orana.appstockexchange.model.dto.ResponseModel;
import com.orana.appstockexchange.model.dto.UserDTO;
import com.orana.appstockexchange.service.UserService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<ResponseModel<Set<UserDTO>>> findUsers() {
        Set<UserDTO> users = userService.findUsers().stream().map(UserDTO::convert).collect(Collectors.toSet());
        return ResponseEntity.ok(new ResponseModel<>("Success", users));
    }

    @GetMapping("/{username}")
    public ResponseEntity<ResponseModel<UserDTO>> findUserByUsername(@PathVariable @NotBlank String username) {
        UserDTO user = UserDTO.convert(userService.loadUserByUsername(username));
        return ResponseEntity.ok(new ResponseModel<>("Success", user));
    }
}
