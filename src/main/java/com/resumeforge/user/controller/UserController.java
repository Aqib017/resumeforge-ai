package com.resumeforge.user.controller;
import com.resumeforge.common.response.ApiResponse;
import com.resumeforge.resume.entity.Resume;
import com.resumeforge.user.dto.UserRequest;
import com.resumeforge.user.dto.UserResponse;
import com.resumeforge.user.entity.User;
import com.resumeforge.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController (UserService userService){
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse registerUser(@Valid @RequestBody UserRequest request){
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        User savedUser = userService.registerUser(user);

        UserResponse response = new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
        return new ApiResponse(
                true,
                "User registered successfully",
                response
        );
    }


}
