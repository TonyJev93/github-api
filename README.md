# GitHub API

## 의존성

- build.gradle

```
dependencies {
    implementation group: 'org.kohsuke', name: 'github-api', version: '1.301'
}
```

## GitHub Connection

- application-github.properties 파일 생성필요
    ```properties
    spring.github.user-name=User-Name
    spring.github.token=Your-Token
    ```
    - GitHub 사용자 이름과 Access-Token을 각자 로컬 환경에서 작성한다.
    - (주의) 해당 파일은 `.gitignore`에 포함 되어 있어 배포되지 않으며, 만약이라도 배포될 시 Access-Token이 외부에 공개되어 자동으로 gitHub에서 해당 토큰을 만료 시켜버린다.

## 제공 기능

### push

- Method : POST
- url : localhost:8080/push
- RequestBody
    ```json
    {
      "baseDirectory": "/baseDirectory",
      "targetDirectory": "/baseDirectory/targetDirectory",
      "commitMessage": "commit message",
      "repositoryName": "repository name",
      "branchName": "branch name"
    }
    ```
- Response
    ```
    commit 완료된 url 링크
    ```
- 설명
    - `targetDirectory` 내에 존재하는 변경된 모든 파일을 원격 저장소(`repositoryName` > `branchName`)에 `commitMessage`와 함께 저장함.
        - 주의 사항
            - 제거된 파일은 감지 못함.
            - `.gitignore` 무시하고 해당 경로 내부에 있는 모든 파일이 push 대상이 됨.

# 고찰

- 재미로 한 번 사용해본 API이며, 정식으로 사용하기 위해 다뤄야 하는 예외처리가 굉장히 많을 것 같다.
- 이미 잘 만들어진 Tool을 사용하는 것을 적극 권장한다.