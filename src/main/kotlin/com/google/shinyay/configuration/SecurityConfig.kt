package com.google.shinyay.configuration

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint

@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http?.authorizeRequests()
                ?.antMatchers("/security")?.authenticated()
                ?.and()
                ?.oauth2ResourceServer()?.jwt()
                ?.and()
                ?.authenticationEntryPoint(Http403ForbiddenEntryPoint())
    }
}