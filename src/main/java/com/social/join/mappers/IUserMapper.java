package com.social.join.mappers;

import com.social.join.dtos.UserDTO;
import com.social.join.entities.User;
import org.mapstruct.Mapper;

@Mapper
public interface IUserMapper {

    User userDTOToUser(UserDTO userDTO);
    UserDTO userToUserDTO(User user);
}
