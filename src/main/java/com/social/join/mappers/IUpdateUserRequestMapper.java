package com.social.join.mappers;

import com.social.join.dtos.UserCreateRequest;
import com.social.join.dtos.UserDTO;
import com.social.join.dtos.UserUpdateRequest;
import org.mapstruct.Mapper;

@Mapper
public interface IUpdateUserRequestMapper {

    UserDTO userUpdateRequestToUserDTO(UserUpdateRequest userUpdateRequest);
    UserUpdateRequest UserDTOToUserUpdateRequest(UserDTO userDTO);

}
