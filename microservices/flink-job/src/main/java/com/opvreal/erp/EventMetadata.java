package com.opvreal.erp;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventMetadata {
  private String username;
  private Boolean like;
  private String imageDescription;
  private Integer historicLikes;
  private Integer historicDownloads;
  private List<String> tags;
}
