package com.provider.internet.model.dto;

import com.provider.internet.model.entity.Role;
import com.provider.internet.model.enums.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
public class UserDto {
    private long id;
    private String login;
    private String password;
    private BigDecimal balance;
    private List<IncludedPackageDto> includedPackages;
    private Status status;
    private Set<RoleDto> role;

    public boolean isUser(){
        return role.contains(new RoleDto(2L, "USER"));
    }
    public boolean isManager(){
        return role.contains(new RoleDto(1L, "ADMIN"));
    }

    public boolean isActive() {
        return Status.ACTIVE.equals(status);
    }

    public boolean isBlocked() {
        return Status.BLOCK.equals(status);
    }
}
