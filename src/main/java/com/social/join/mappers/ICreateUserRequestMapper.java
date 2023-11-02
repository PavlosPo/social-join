package com.social.join.mappers;

import com.social.join.dtos.UserCreateRequest;
import com.social.join.dtos.UserDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ICreateUserRequestMapper {

    UserDTO userCreateRequestToUserDTO(UserCreateRequest userCreateRequest);
    UserCreateRequest UserDTOToUserCreateRequest(UserDTO userDTO);

}
