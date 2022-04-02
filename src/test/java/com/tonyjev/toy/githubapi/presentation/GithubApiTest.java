package com.tonyjev.toy.githubapi.presentation;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.fail;

public class GithubApiTest {

    GithubApi githubApi = new GithubApi();

    @Test
    void test() {
        String token = "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"; // 생성한 토큰
        try {
            githubApi.connectGithub(token);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void commitTest() throws IOException {
        String currDir = System.getProperty("user.dir");

        File baseDir = new File(currDir);
        File currentDir = new File(currDir + "/src");

        String response = GithubApi.pushFiles(
                baseDir,
                currentDir,
                "feat: GithubApi commit 소스 구현 - committed by github api java",
                "TonyJev93",
                "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",
                "github-api",
                "master");

        System.out.println("response = " + response);
    }

    @Test
    void test2() {
        String currDir = System.getProperty("user.dir");

//        File dir = new File(currDir);
        File dir = new File(currDir + "/commitTestFile.txt");
        File[] files = dir.listFiles();

        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            String name = f.getName();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mma");

            String attribute = "";
            String size = "";

            if (files[i].isDirectory()) {
                attribute = "DIR";
            } else {
                size = f.length() + "";
                attribute = f.canRead() ? "R" : " ";
                attribute += f.canWrite() ? "W" : " ";
                attribute += f.isHidden() ? "H" : " ";
            }

            System.out.printf("%s %3s %6s %s\n", df.format(new Date(f.lastModified())), attribute, size, name);
        }

    }
}
