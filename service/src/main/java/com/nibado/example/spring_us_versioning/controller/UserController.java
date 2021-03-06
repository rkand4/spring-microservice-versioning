package com.nibado.example.spring_us_versioning.controller;

import com.nibado.example.spring_us_versioning.controller.dto.UserDTO;
import com.nibado.example.spring_us_versioning.controller.dto.UserListDTO;
import com.nibado.example.spring_us_versioning.service.UserService;
import com.nibado.example.spring_us_versioning.service.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(final UserService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserDTO get(@PathVariable final int id) {
        User user = service.get(id).orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDTO(user.getFirstName(), user.getLastName(), user.getEMail());
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public UserListDTO getAll() {
        List<UserDTO> users = service.getAll()
                .stream()
                .map(u -> new UserDTO(u.getFirstName(), u.getLastName(), u.getEMail()))
                .collect(toList());

        return new UserListDTO(users);
    }
}
