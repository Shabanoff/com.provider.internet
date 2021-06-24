package com.provider.internet.model.dto;

import com.provider.internet.model.entity.User;
import lombok.Data;

import java.util.Set;

@Data
public class RoleDto {
    private Long id;
    private String name;
    private Set<User> users;

    public RoleDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
