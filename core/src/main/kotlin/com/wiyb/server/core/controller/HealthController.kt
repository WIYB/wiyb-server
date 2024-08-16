package com.wiyb.server.core.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
class HealthController {
    @GetMapping
    fun healthCheck(): String = ""

    @GetMapping("/status")
    fun statusCheck(): ResponseEntity<String> = ResponseEntity.ok().body("OK")
}
