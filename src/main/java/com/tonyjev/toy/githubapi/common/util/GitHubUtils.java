package com.tonyjev.toy.githubapi.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.kohsuke.github.GHTreeBuilder;

import java.io.File;
import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GitHubUtils {
    private static final String CHARSET_NAME = "UTF-8";

    public static void addFilesToTree(GHTreeBuilder treeBuilder, File baseDirectory, File targetDirectory) throws IOException {
        for (File file : targetDirectory.listFiles()) {
            if (file.isFile()) {
                treeBuilder.add(
                        GitHubUtils.getRelativePath(baseDirectory, file),
                        FileUtils.readFileToString(file, CHARSET_NAME),
                        file.canExecute()
                );
            } else {
                addFilesToTree(treeBuilder, baseDirectory, file);
            }
        }
    }

    private static String getRelativePath(File baseDirectory, File file) throws IOException {
        return baseDirectory.toURI().relativize(file.toURI()).getPath();
    }

}
