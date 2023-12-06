package com.social.join.mappers;

import com.social.join.dtos.SignUpDTO;
import com.social.join.dtos.UserCreateRequest;
import com.social.join.dtos.UserDTO;
import com.social.join.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IUserMapper {

    User userDTOToUser(UserDTO userDTO);
    UserDTO userToUserDTO(User user);
    User userCreateRequestToUser(UserCreateRequest userCreateRequest);
    UserCreateRequest userToUserCreateRequest(User user);
    UserCreateRequest userDTOToUserCreateRequest(UserDTO userDTO);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDTO signUpDTO);
}
