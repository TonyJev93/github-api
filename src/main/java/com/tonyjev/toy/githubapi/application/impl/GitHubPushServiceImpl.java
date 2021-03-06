package com.tonyjev.toy.githubapi.application.impl;

import com.tonyjev.toy.githubapi.application.GitHubPushService;
import com.tonyjev.toy.githubapi.common.util.GitHubUtils;
import com.tonyjev.toy.githubapi.domain.PushRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class GitHubPushServiceImpl implements GitHubPushService {
  private static final String REF_NAME_PREFIX = "heads/";

  private final GitHub gitHub;

  @Value("${spring.github.user-name}")
  private String gitHubUserName;

  @Override
  public String pushFiles(File baseDirectory, File targetDirectory, PushRequest pushRequest)
      throws IOException {
    GHRepository repo = gitHub.getRepository(gitHubUserName + "/" + pushRequest.getRepositoryName());

    GHRef ref = repo.getRef(REF_NAME_PREFIX + pushRequest.getBranchName());

    GHCommit latestCommit = repo.getCommit(ref.getObject().getSha());

    GHTree tree = getGitHubTree(baseDirectory, targetDirectory, repo, latestCommit);

    GHCommit commit =
        repo.createCommit()
            .parent(latestCommit.getSHA1())
            .tree(tree.getSha())
            .message(pushRequest.getCommitMessage())
            .create();

    ref.updateTo(commit.getSHA1());

    log.info(
        "Commit created with on branch "
            + pushRequest.getBranchName()
            + " and SHA "
            + commit.getSHA1()
            + " and URL "
            + commit.getHtmlUrl());

    return commit.getHtmlUrl().getPath();
  }

  private GHTree getGitHubTree(File baseDirectory, File targetDirectory, GHRepository repo, GHCommit latestCommit) throws IOException {
    GHTreeBuilder treeBuilder = repo.createTree().baseTree(latestCommit.getTree().getSha());
    GitHubUtils.addFilesToTree(treeBuilder, baseDirectory, targetDirectory);
    GHTree tree = treeBuilder.create();
    return tree;
  }
}
