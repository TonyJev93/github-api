package com.tonyjev.toy.githubapi.application;

import com.tonyjev.toy.githubapi.domain.PushRequest;

import java.io.File;
import java.io.IOException;

public interface GitHubPushService {
  String pushFiles(File baseDirectory, File targetDirectory, PushRequest pushRequest)
      throws IOException;
}
