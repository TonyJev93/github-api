package com.tonyjev.toy.githubapi.application.impl;

import com.tonyjev.toy.githubapi.application.GitHubPushService;
import com.tonyjev.toy.githubapi.common.util.GitHubUtils;
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
    private String userName;

    @Override
    public String pushFiles(File baseDirectory, File targetDirectory, String commitMessage, String repositoryName, String branchName) throws IOException {
        GHRepository repo = gitHub.getRepository(userName + "/" + repositoryName);

        GHRef ref = repo.getRef(REF_NAME_PREFIX + branchName);

        GHCommit latestCommit = repo.getCommit(ref.getObject().getSha());

        GHTreeBuilder treeBuilder = repo.createTree().baseTree(latestCommit.getTree().getSha());
        GitHubUtils.addFilesToTree(treeBuilder, baseDirectory, targetDirectory);
        GHTree tree = treeBuilder.create();

        GHCommit commit = repo.createCommit()
                .parent(latestCommit.getSHA1())
                .tree(tree.getSha())
                .message(commitMessage)
                .create();

        ref.updateTo(commit.getSHA1());

        log.info("Commit created with on branch " + branchName + " and SHA " + commit.getSHA1() + " and URL " + commit.getHtmlUrl());

        return commit.getHtmlUrl().getPath();
    }
}
