package com.bigdata.app.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLikesMessage {
  private String username;
  private Integer totalLikes;
}
