package com.example.sqlite.adapters.in.web.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.sqlite.adapters.in.web.dto.CreateUserRequest;
import com.example.sqlite.adapters.in.web.dto.UserResponse;
import com.example.sqlite.domain.User;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    UserResponse toResponse(User user);

    List<UserResponse> toResponseList(List<User> users);

    User toDomain(CreateUserRequest request);
}
