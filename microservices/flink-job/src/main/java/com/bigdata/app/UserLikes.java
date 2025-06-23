package com.bigdata.app;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLikes {
  private String username;
  private Integer totalLikes;
}
