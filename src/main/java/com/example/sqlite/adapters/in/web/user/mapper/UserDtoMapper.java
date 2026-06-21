package com.example.sqlite.adapters.in.web.user.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.sqlite.adapters.in.web.user.dto.CreateUserRequest;
import com.example.sqlite.adapters.in.web.user.dto.UpdateUserRequest;
import com.example.sqlite.adapters.in.web.user.dto.UserResponse;
import com.example.sqlite.domain.user.User;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    UserResponse toResponse(User user);

    List<UserResponse> toResponseList(List<User> users);

    User toDomain(CreateUserRequest request);

    User toDomain(UpdateUserRequest request);
}
