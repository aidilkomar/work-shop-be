package com.asein.workshop.user;

import com.asein.workshop.common.ApiRequest;
import com.asein.workshop.user.dto.*;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {
    UserResponse create(CreateUserRequest request);

    UserResponse update(@Valid UpdateUserRequest req);

    UserResponse changePassword(@Valid ChangePasswordRequest req);

    void delete(@Valid ApiRequest req);

    List<UserListResponse> getUsers(UserListRequest req);

    UserResponse getUser(@Valid ApiRequest req);
}
