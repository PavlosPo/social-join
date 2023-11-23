package com.social.join.mappers;

import com.social.join.dtos.UserCreateRequest;
import com.social.join.dtos.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICreateUserRequestMapper {

    UserDTO userCreateRequestToUserDTO(UserCreateRequest userCreateRequest);
    UserCreateRequest UserDTOToUserCreateRequest(UserDTO userDTO);

}
