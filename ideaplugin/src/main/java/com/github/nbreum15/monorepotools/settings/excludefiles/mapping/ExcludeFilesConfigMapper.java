package com.github.nbreum15.monorepotools.settings.excludefiles.mapping;

import com.github.nbreum15.monorepotools.settings.excludefiles.ExcludeFilesSettingsState;
import com.github.nbreum15.monorepotools.settings.excludefiles.dto.ExcludeFilesConfigDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ExcludeFilesConfigMapper {

    ExcludeFilesConfigMapper INSTANCE = Mappers.getMapper(ExcludeFilesConfigMapper.class);

    @Mapping(target = "excludeGlobsForVcs", source = "excludeGlobsForVcs", qualifiedByName = "mapCsvToSet")
    ExcludeFilesConfigDTO map(ExcludeFilesSettingsState state);

    @Named("mapCsvToSet")
    default Set<String> mapCsvToSet(String csv) {
        return Arrays.stream(csv.split(",")).collect(Collectors.toSet());
    }

}
