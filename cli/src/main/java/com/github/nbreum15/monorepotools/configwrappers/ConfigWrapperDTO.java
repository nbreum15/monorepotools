package com.github.nbreum15.monorepotools.configwrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.github.nbreum15.monorepotools.config.dto.CommitMessageConfigDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigWrapperDTO {
    @JacksonXmlProperty(localName = "CommitMessageConfigDTO")
    private CommitMessageConfigDTO CommitMessageConfigDTO;
}
