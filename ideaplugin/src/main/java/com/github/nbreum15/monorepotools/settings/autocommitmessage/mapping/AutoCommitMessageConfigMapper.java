package com.github.nbreum15.monorepotools.settings.autocommitmessage.mapping;

import com.github.nbreum15.monorepotools.settings.autocommitmessage.AutoCommitMessageSettingsState;
import com.github.nbreum15.monorepotools.settings.autocommitmessage.dto.AutoCommitMessageConfigDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AutoCommitMessageConfigMapper {

    AutoCommitMessageConfigMapper INSTANCE = Mappers.getMapper(AutoCommitMessageConfigMapper.class);

    AutoCommitMessageConfigDTO map(AutoCommitMessageSettingsState state);

}
