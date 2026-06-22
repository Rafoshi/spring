package com.example.sqlite.adapters.out.persistence.user.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.sqlite.domain.user.User;
import com.example.sqlite.infra.persistence.user.UserEntity;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    User toDomain(UserEntity entity);

    List<User> toDomainList(List<UserEntity> entities);

    UserEntity toEntity(User user);
}
