package com.provider.internet.model.dto;

import lombok.*;

@Data
public class IncludedOptionDto {
    private long id;
    private String definition;

    public IncludedOptionDto() {
    }


    protected boolean canEqual(final Object other) {
        return other instanceof IncludedOptionDto;
    }

}
