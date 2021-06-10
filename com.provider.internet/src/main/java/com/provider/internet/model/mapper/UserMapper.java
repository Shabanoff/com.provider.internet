package com.provider.internet.model.mapper;

import com.provider.internet.model.dto.ServiceDto;
import com.provider.internet.model.dto.UserDto;
import com.provider.internet.model.entity.Service;
import com.provider.internet.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IncludedPackageMapper.class},nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {

    User userDtoToUser(UserDto userDto);

    UserDto userToUserDto(User user);

    List<UserDto> usersToUsersDtoList(List<User> user);



}
