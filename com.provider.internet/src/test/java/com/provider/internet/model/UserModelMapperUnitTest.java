package com.provider.internet.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.provider.internet.model.dto.UserDto;
import com.provider.internet.model.entity.Role;
import com.provider.internet.model.entity.User;
import com.provider.internet.model.enums.Status;
import com.provider.internet.model.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Collections;

@SpringBootTest
class UserModelMapperUnitTest {

    @Autowired
    private UserMapper mapper;

    @Test
    void mapUserToUserDto_shouldWorkCorrect() {
        User user = new User();
        user.setBalance(BigDecimal.ZERO);
        user.setStatus(Status.ACTIVE);
        user.setId(1L);
        user.setLogin("test");
        user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));

        UserDto actual = mapper.userToUserDto(user);

        assertThat(actual.getId()).isEqualTo(user.getId());
        assertThat(actual.getLogin()).isEqualTo(user.getLogin());
        assertThat(actual.getBalance()).isEqualTo(user.getBalance());
        assertThat(actual.getStatus()).isEqualTo(user.getStatus());
        assertThat(actual.getRole()).isEqualTo(user.getRoles());
    }

    @Test
    void mapUserToUserDto_withNullValue_shouldWorkCorrect() {
        User user = new User();

        UserDto actual = mapper.userToUserDto(user);

        assertThat(actual.getLogin()).isNull();
        assertThat(actual.getBalance()).isNull();
        assertThat(actual.getStatus()).isNull();
        assertThat(actual.getRole()).isNull();
    }

    @Test
    void mapUserDtoToUser_shouldWorkCorrect() {
        UserDto userDto = new UserDto();
        userDto.setBalance(BigDecimal.ZERO);
        userDto.setStatus(Status.ACTIVE);
        userDto.setId(1L);
        userDto.setLogin("test");
        userDto.setRole(Collections.singleton(new Role(2L, "ROLE_USER")));

        User actual = mapper.userDtoToUser(userDto);

        assertThat(actual.getId()).isEqualTo(userDto.getId());
        assertThat(actual.getLogin()).isEqualTo(userDto.getLogin());
        assertThat(actual.getBalance()).isEqualTo(userDto.getBalance());
        assertThat(actual.getStatus()).isEqualTo(userDto.getStatus());
        assertThat(actual.getRoles()).isEqualTo(userDto.getRole());
    }

}

