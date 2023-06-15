package com.github.nbreum15.monorepotools.actions;

import com.github.nbreum15.monorepotools.config.dto.CommitMessageConfigDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ConfigMapper {

    ConfigMapper INSTANCE = Mappers.getMapper(ConfigMapper.class);

    CommitMessageConfigDTO map(com.github.nbreum15.monorepotools.settings.commitmessage.dto.CommitMessageConfigDTO settings);
}
