# Spring Cloud GCP with Cloud Identity-Aware Proxy (IAP) Authentication

Cloud Identity-Aware Proxy (IAP) provides a security layer over applications deployed to Google Cloud.

## Description
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
