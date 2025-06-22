package com.bigdata.app.http.mapper;

import com.bigdata.app.domain.model.EventMetadata;
import com.bigdata.app.http.dto.EventMetadataDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EventMetadataDTOMapper {
  public static EventMetadata mapToModel(EventMetadataDTO event) {
    return EventMetadata.builder()
      .username(event.getUsername())
      .like(event.getLike())
      .imageDescription(event.getImageDescription())
      .historicLikes(event.getHistoricLikes())
      .historicDownloads(event.getHistoricDownloads())
      .tags(event.getTags())
      .build();
  }
}
