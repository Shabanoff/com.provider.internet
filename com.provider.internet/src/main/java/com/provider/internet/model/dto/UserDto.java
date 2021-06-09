package com.provider.internet.model.dto;

import com.provider.internet.model.enums.Role;
import com.provider.internet.model.enums.Status;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UserDto {
    private long id;
    private String login;
    private String password;
    private BigDecimal balance;
    private List<IncludedPackageDto> includedPackages;
    private Status status;
    private Role role;

    public boolean isManager() {
        return Role.MANAGER.equals(role);
    }

    public boolean isUser() {
        return Role.USER.equals(role);
    }


    public boolean isActive() {
        return Status.ACTIVE.equals(status);
    }

    public boolean isBlocked() {
        return Status.BLOCK.equals(status);
    }
}
