package com.asein.workshop.user;

import com.asein.workshop.common.ApiRequest;
import com.asein.workshop.common.ApiResponse;
import com.asein.workshop.user.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/get")
    public ResponseEntity<ApiResponse<UserResponse>> get(@Valid @RequestBody ApiRequest req) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "retrieve data success",
                        userService.getUser(req),
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/getAll")
    public ResponseEntity<ApiResponse<List<UserListResponse>>> get(@Valid @RequestBody UserListRequest req) {

        List<UserListResponse> users = userService.getUsers(req);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "retrieve data successfully",
                        users,
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<UserResponse>> create(@Valid @RequestBody CreateUserRequest req) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "user has created",
                        userService.create(req),
                        LocalDateTime.now()
                )
        );

    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<UserResponse>> update(@Valid @RequestBody UpdateUserRequest req) {
        var res = userService.update(req);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "user has updated",
                        res,
                        LocalDateTime.now()
                )
        );
    }

    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@Valid @RequestBody ChangePasswordRequest req) {
        userService.changePassword(req);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "password has been updated",
                        null
                )
        );
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> delete(@Valid @RequestBody ApiRequest req) {
        userService.delete(req);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "user has deleted",
                        null
                )
        );
    }
}
