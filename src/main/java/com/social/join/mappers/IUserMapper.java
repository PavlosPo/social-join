package com.social.join.mappers;

import com.social.join.dtos.UserCreateRequest;
import com.social.join.dtos.UserDTO;
import com.social.join.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserMapper {

    User userDTOToUser(UserDTO userDTO);
    UserDTO userToUserDTO(User user);
    User userCreateRequestToUser(UserCreateRequest userCreateRequest);
    UserCreateRequest userToUserCreateRequest(User user);
    UserCreateRequest userDTOToUserCreateRequest(UserDTO userDTO);
}
