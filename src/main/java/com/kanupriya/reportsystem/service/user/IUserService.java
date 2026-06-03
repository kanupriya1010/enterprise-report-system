package com.kanupriya.reportsystem.service.user;

import com.kanupriya.reportsystem.dto.UserDto;
import com.kanupriya.reportsystem.model.User;
import com.kanupriya.reportsystem.request.CreateUserRequest;
import com.kanupriya.reportsystem.request.UserUpdateRequest;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}
