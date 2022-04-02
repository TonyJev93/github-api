package com.tonyjev.toy.githubapi.application;

import java.io.File;
import java.io.IOException;

public interface GitHubPushService {
    String pushFiles(File baseDirectory, File targetDirectory, String commitMessage, String repositoryName, String branchName) throws IOException;
}
