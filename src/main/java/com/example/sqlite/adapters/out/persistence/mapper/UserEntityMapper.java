package com.example.sqlite.adapters.out.persistence.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.sqlite.domain.User;
import com.example.sqlite.infra.persistence.UserEntity;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    User toDomain(UserEntity entity);

    List<User> toDomainList(List<UserEntity> entities);
}
