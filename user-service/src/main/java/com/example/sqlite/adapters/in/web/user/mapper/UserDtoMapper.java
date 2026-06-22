package com.example.sqlite.adapters.in.web.user.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import com.example.sqlite.adapters.in.web.dto.PagedResponse;
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

    default PagedResponse<UserResponse> toPagedResponse(Page<User> page) {
        return new PagedResponse<>(
                toResponseList(page.getContent()),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }
}
