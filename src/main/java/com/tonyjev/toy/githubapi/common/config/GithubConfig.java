package com.tonyjev.toy.githubapi.common.config;

import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@Slf4j
public class GithubConfig {
    @Value("${spring.github.token}")
    private String token;

    @Bean
    public GitHub gitHubPool() throws IOException {
        GitHub gitHub = new GitHubBuilder().withOAuthToken(token).build();

        try {
            gitHub.checkApiUrlValidity();
        } catch (Exception e) {
            log.error("Error occurred! Cannot not connect to GitHub...");
        }

        return gitHub;
    }
}
