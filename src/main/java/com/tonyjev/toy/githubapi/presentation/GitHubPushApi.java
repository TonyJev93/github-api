package com.tonyjev.toy.githubapi.presentation;

import com.tonyjev.toy.githubapi.application.GitHubPushService;
import com.tonyjev.toy.githubapi.presentation.dto.PushDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        return gitHubPushService.pushFiles(baseDirectory, targetDirectory, request.getCommitMessage(), request.getRepositoryName(), request.getBranchName());

    }
}
