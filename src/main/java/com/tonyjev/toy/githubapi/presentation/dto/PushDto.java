package com.tonyjev.toy.githubapi.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PushDto {

    @Getter
    public static class Request {
        private String baseDirectory;
        private String targetDirectory;
        private String commitMessage;
        private String repositoryName;
        private String branchName;
    }

}
