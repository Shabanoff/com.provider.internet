package com.provider.internet.model.mapper;

import com.provider.internet.model.dto.IncludedOptionDto;
import com.provider.internet.model.entity.IncludedOption;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface OptionMapper {

    IncludedOption includedOptionDtoToIncludedOption(IncludedOptionDto includedOptionDto);

    IncludedOptionDto includedOptionToIncludedOptionDto(IncludedOption includedOption);

    List<IncludedOptionDto> includedOptionsToIncludedOptionsDtoList(List<IncludedOption> includedOption);
}
