package com.litwnb.reelindex.mapper;

import com.litwnb.reelindex.entity.User;
import com.litwnb.reelindex.model.UserDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User userDtoToUser(UserDTO dto);

    UserDTO userToUserDto(User user);
}
