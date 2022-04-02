package com.tonyjev.toy.githubapi.presentation;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.kohsuke.github.*;

import java.io.File;
import java.io.IOException;

@Slf4j
public class GithubApi {
    static public String pushFiles(File baseDirectory, File currentDirectory, String message,
                                   String userName, String token,
                                   String repositoryName, String branchName) throws IOException {
        GitHub github = new GitHubBuilder().withOAuthToken(token).build();
        GHRepository repo = github.getRepository(userName + "/" + repositoryName);
        // get current branch
        GHRef ref = repo.getRef("heads/" + branchName);
        GHCommit latestCommit = repo.getCommit(ref.getObject().getSha());
        GHTreeBuilder treeBuilder = repo.createTree().baseTree(latestCommit.getTree().getSha());
        addFilesToTree(treeBuilder, baseDirectory, currentDirectory);
        GHTree tree = treeBuilder.create();
        GHCommit commit = repo.createCommit()
                .parent(latestCommit.getSHA1())
                .tree(tree.getSha())
                .message(message)
                .create();
        ref.updateTo(commit.getSHA1());

        log.info("Commit created with on branch " + branchName + " and SHA " + commit.getSHA1() + " and URL " + commit.getHtmlUrl());
        return commit.getSHA1();
    }

    private static void addFilesToTree(GHTreeBuilder treeBuilder, File baseDirectory, File currentDirectory) throws IOException {
        for (File file : currentDirectory.listFiles()) {
            String relativePath = baseDirectory.toURI().relativize(file.toURI()).getPath();
            if (file.isFile()) {
//                treeBuilder.textEntry(relativePath, FileUtils.readFileToString(file), false);
                treeBuilder.add(relativePath, FileUtils.readFileToString(file, "UTF-8"), file.canExecute());
            } else {
                addFilesToTree(treeBuilder, baseDirectory, file);
            }
        }
    }

    public void connectGithub(String token) throws IOException {
        GitHub github = new GitHubBuilder().withOAuthToken(token).build();
        github.checkApiUrlValidity();
    }
}
