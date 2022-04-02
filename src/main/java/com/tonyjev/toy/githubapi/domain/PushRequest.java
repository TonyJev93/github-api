package com.tonyjev.toy.githubapi.domain;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class PushRequest {
  private String commitMessage;
  private String repositoryName;
  private String branchName;

  public static PushRequest of(String commitMessage, String repositoryName, String branchName) {
    return PushRequest.builder()
        .commitMessage(commitMessage)
        .repositoryName(repositoryName)
        .branchName(branchName)
        .build();
  }
}
