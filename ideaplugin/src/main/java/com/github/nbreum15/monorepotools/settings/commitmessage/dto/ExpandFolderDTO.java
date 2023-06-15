package com.github.nbreum15.monorepotools.settings.commitmessage.dto;

import com.intellij.util.xmlb.annotations.Tag;
import com.intellij.util.xmlb.annotations.XCollection;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ExpandFolderDTO {
    @Tag("folderParts")
    @XCollection(style = XCollection.Style.v2, elementName = "folderParts", valueAttributeName = "")
    @Builder.Default
    private List<String> folderParts = List.of("path/to/folder");
}
