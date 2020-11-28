package com.google.shinyay.controller

import com.google.shinyay.logger
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RestController
class HelloController {

    @GetMapping("/")
    fun defaultAccess(): String {
        val dateAndTime = ZonedDateTime.now(ZoneId.of("Japan")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd: HH:mm:ss"))
        logger.info(dateAndTime)
        return "Hello at $dateAndTime"
    }

    @GetMapping("/security")
    fun securedAccess() {
        val authentication = SecurityContextHolder.getContext().authentication
    }
}