# Spring Cloud GCP with Cloud Identity-Aware Proxy (IAP) Authentication

Cloud Identity-Aware Proxy (IAP) provides a security layer over applications deployed to Google Cloud.
Cloud IAP inject `x-goog-iap-jwt-assertion` to HTTP Header.
Spring Security extracts user identity from **x-goog-iap-jwt-assertion**.

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

## Demo

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
