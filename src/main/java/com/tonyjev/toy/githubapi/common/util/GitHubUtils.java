package com.tonyjev.toy.githubapi.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.kohsuke.github.GHTreeBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GitHubUtils {

  public static void addFilesToTree(
      GHTreeBuilder treeBuilder, File baseDirectory, File targetDirectory) throws IOException {
    for (File file : targetDirectory.listFiles()) {
      if (file.isFile()) {
        treeBuilder.add(
            GitHubUtils.getRelativePath(baseDirectory, file),
            FileUtils.readFileToString(file, StandardCharsets.UTF_8),
            file.canExecute());
      } else {
        addFilesToTree(treeBuilder, baseDirectory, file);
      }
    }
  }

  private static String getRelativePath(File baseDirectory, File file) {
    return baseDirectory.toURI().relativize(file.toURI()).getPath();
  }
}
