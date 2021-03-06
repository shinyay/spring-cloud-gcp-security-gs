# Spring Cloud GCP with Cloud Identity-Aware Proxy (IAP) Authentication

Cloud Identity-Aware Proxy (IAP) provides a security layer over applications deployed to Google Cloud.
Cloud IAP inject `x-goog-iap-jwt-assertion` to HTTP Header.
Spring Security extracts user identity from **x-goog-iap-jwt-assertion**.

- [SetUp Identity-Aware Proxy](https://github.com/shinyay/gcp-authentication-gke-with-iap-gs/blob/main/README.md)

## Description
![oauth2](https://user-images.githubusercontent.com/3072734/100515715-7701fc00-31c1-11eb-9bef-65dd5c16d7fc.png)

### Dependencies
- org.springframework.cloud
  - spring-cloud-gcp-starter-security-iap

### Spring Security Configuration
#### Enable Web Security with **WebSecurityConfigurerAdapter**
Extend `WebSecurityConfigurerAdapter` to provide a default configuration.

- `WebSecurityConfigurerAdapter`
- `@EnableWebSecurity`

```kotlin
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {}
```

#### Enable HTTP Security
Override `configure(http: HttpSecurity?)` funtion

The following is very basic sample to authenticate request for ANY.

- anyrequest(): Any Request to this HTTP Access
- authenticated(): Need to access with Authentication

```kotlin
override fun configure(http: HttpSecurity?) {
    http?.authorizeRequests()
            ?.anyrequest()?.authenticated()
}
```

#### Specify secured access path

- `antMatchers("/<ACCESS_PATH>")`

```kotlin
override fun configure(http: HttpSecurity?) {
    http?.authorizeRequests()
            ?.antMatchers("/security")?.authenticated()
}
```

#### Configure OAuth 2.0 Resource Server

- `oauth2ResourceServer()`
  - `jwt()`
  - `opaqueToken()`

```kotlin
override fun configure(http: HttpSecurity?) {
    http?.authorizeRequests()
            ?.antMatchers("/security")?.authenticated()
            ?.and()
            ?.oauth2ResourceServer()?.jwt()
}
```

#### Handle when Authentication is failed

- `authenticationEntryPoint()`

|AuthenticationEntryPoint implemented Class|Explanation|
|------------------------------------------|-----------|
|BasicAuthenticationEntryPoint|401 Error for Basic Authentication|
|BearerTokenAuthenticationEntryPoint||
|DelegatingAuthenticationEntryPoint||
|DigestAuthenticationEntryPoint|401 Error for Digest Authentication|
|Http403ForbiddenEntryPoint|403 Error|
|LoginUrlAuthenticationEntryPoint|Display Login Form|

```kotlin
override fun configure(http: HttpSecurity?) {
    http?.authorizeRequests()
            ?.antMatchers("/security")?.authenticated()
            ?.and()
            ?.oauth2ResourceServer()?.jwt()
            ?.and()
            ?.authenticationEntryPoint(Http403ForbiddenEntryPoint())
}
```

### Retrieve authentication for the current request

```kotlin
val authentication = SecurityContextHolder.getContext().authentication
```

### Retrieve Principal which contains Authenticated user information

```kotlin
val jwt: Jwt = authentication.principal as Jwt
```

### Configure Audience from Identity-Aware Proxy
- [Identity-Aware Proxy](https://console.cloud.google.com/security/iap/?_ga=2.114350786.495858175.1606693674-983599867.1599137884&_gac=1.247115382.1604543893.CjwKCAiAv4n9BRA9EiwA30WND9tYKNMuLjYNlsSBrI4JO3KyW7Wkyj7T5SL10VmdwDs8jNxCe6vRoxoChh0QAvD_BwE)

![Signed Header JWT Audience](https://user-images.githubusercontent.com/3072734/100560482-43c28880-32f9-11eb-8ffb-34179ca00808.png)

```yaml
spring:
  cloud:
    gcp:
      security:
        iap:
          audience: /projects/1094611386598/global/backendServices/323794236536435905
```

## Demo

### Create Artifact Registry
```shell script
$ gcloud artifacts repositories create shinyay-container --location us-central1 --repository-format docker
```

```shell script
$ gcloud artifacts repositories list
```

### Authenticate for Jib
```shell script
$ gcloud auth configure-docker --include-artifact-registry
```

### Generate Gradle Property for Jib Build
```shell script
$ ./script/generate-gradle-props.fish
```

### Build with Jib
```shell script
$ ./gradlew clean jib
```

### Deploy App to GKE
```shell script
$ sed -e "s|GCP_PROJECT|"(gcloud config get-value project)"|g" k8s/deploy-app.yml | kubectl apply -f -
```

## Features

- feature:1
- feature:2

## Requirement

## Usage

## Installation

## Licence

Released under the [MIT license](https://gist.githubusercontent.com/shinyay/56e54ee4c0e22db8211e05e70a63247e/raw/34c6fdd50d54aa8e23560c296424aeb61599aa71/LICENSE)

## Author

[shinyay](https://github.com/shinyay)
