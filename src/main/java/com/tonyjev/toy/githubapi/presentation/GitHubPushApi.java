package com.tonyjev.toy.githubapi.presentation;

import com.tonyjev.toy.githubapi.application.GitHubPushService;
import com.tonyjev.toy.githubapi.domain.PushRequest;
import com.tonyjev.toy.githubapi.presentation.dto.PushDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/push")
public class GitHubPushApi {

  private final GitHubPushService gitHubPushService;

  @PostMapping
  public String pushApi(@RequestBody PushDto.Request request) throws IOException {
    File baseDirectory = new File(request.getBaseDirectory());
    File targetDirectory = new File(request.getTargetDirectory());
    PushRequest pushRequest = PushRequest.of(request.getCommitMessage(), request.getRepositoryName(), request.getBranchName());
    return gitHubPushService.pushFiles(baseDirectory, targetDirectory, pushRequest);
  }
}
