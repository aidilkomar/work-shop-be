package com.asein.workshop.user;

import com.asein.workshop.common.ApiRequest;
import com.asein.workshop.common.exception.BadRequestException;
import com.asein.workshop.common.exception.ConflictException;
import com.asein.workshop.common.exception.NotFoundException;
import com.asein.workshop.user.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse create(CreateUserRequest request) {

        var isExist = userRepository.findByUsername(request.username()).isPresent();
        if (isExist) throw new ConflictException("username has been taken, please choose another one");

        var user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .fullName(request.fullName())
                .role(request.role())
                .isActive(request.isActive())
                .build();

        System.out.printf("built user: %s, %s, %s, %s", user.getUsername(), user.getPassword(), user.getFullName(), user.getRole());

        return userRepository.save(user);
    }

    @Override
    public UserResponse update(UpdateUserRequest req) {
        var user = userRepository.findById(req.id())
                .orElseThrow(() -> new NotFoundException("user not found!"));

        if (!Objects.equals(req.user().username(), user.getUsername())) {
            boolean exists = userRepository.findByUsername(req.user().username()).isPresent();
            if (exists) {
                throw new ConflictException("username has been taken.");
            }
        }

        user.setUsername(req.user().username());
        user.setFullName(req.user().fullName());
        user.setRole(req.user().role());
        user.setIsActive(req.user().isActive());

        return userRepository.update(user)
                .orElseThrow(() -> new RuntimeException("Failed to update user"));
    }

    @Override
    public UserResponse changePassword(ChangePasswordRequest req) {
        var u = userRepository.findById(req.id()).orElseThrow(() -> new NotFoundException("user not found!"));
        if (!Objects.equals(req.password(), req.confirmPassword())) {
            throw new BadRequestException("password doesn't match.");
        }
        u.setPassword(passwordEncoder.encode(req.password()));
        return userRepository.updatePassword(u)
                .orElseThrow(() -> new RuntimeException("Failed to changing password"));
    }

    @Override
    public void delete(ApiRequest req) {
        var hasDeleted = userRepository.delete(req.id());
        if (!hasDeleted) {
            log.info("data user with id {} not found", req.id());
            throw new NotFoundException("data not found");
        }
    }

    @Override
    public List<UserListResponse> getUsers(UserListRequest req) {
        int page = Math.max(req.pageReq().page(), 1);
        int size = req.pageReq().size();

        int offset = (page - 1) * size;
        return userRepository.findAllUsers(offset, size, req.username(), req.fullName());
    }

    @Override
    public UserResponse getUser(ApiRequest req) {
        var user = userRepository.findById(req.id()).orElseThrow(() -> new NotFoundException("user not found!"));

        return new UserResponse(user.getUsername(), user.getFullName(), user.getRole(), user.getIsActive()
        );
    }
}
