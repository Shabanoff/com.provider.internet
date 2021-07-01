package com.provider.internet.model.mapper;

import com.provider.internet.model.dto.IncludedPackageDto;
import com.provider.internet.model.entity.IncludedPackage;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TariffMapper.class, ServiceMapper.class}, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface IncludedPackageMapper {

    IncludedPackage includedPackageDtoToIncludedPackage(IncludedPackageDto includedPackageDto);

    IncludedPackageDto includedPackageToIncludedPackageDto(IncludedPackage includedPackage);

    List<IncludedPackageDto> includedPackagesToIncludedPackagesDtoList(List<IncludedPackage> includedPackage);


}
