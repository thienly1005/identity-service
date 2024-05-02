package com.devteria.identityservice.mapper;

import com.devteria.identityservice.dto.request.UserCreationRequest;
import com.devteria.identityservice.dto.request.UserUpdateRequest;
import com.devteria.identityservice.dto.response.UserResponse;
import com.devteria.identityservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
//    @Mapping(source = "firstName", target = "LastName")
//    mapping tới source là firstName và thay đổi lastName giống firstName
//    @Mapping(target = "lastName", ignore = true)
//    mapping tới lastName và ignore nó biến nó thành null
//    UserResponse toUserResponse(User user);
    UserResponse toUserResponse(User user);
    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
//    void getUsers(@MappingTarget User user, UserUpdateRequest request);
}
