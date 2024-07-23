package com.wiyb.server.core.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/auth")
class AuthController {
    @GetMapping("/sign/success")
    fun signSuccess(): String {
        println("Gooooooooooooood")
        return "sign success"
    }

    @GetMapping("/sign/good")
    fun signGood(): String {
        println("Gooooooooooooood123123")
        return "sign good"
    }

    @GetMapping("/sign/failure")
    fun signFailure(): String {
        println("Faaaaaaaaaaaaail")
        return "sign failure"
    }
}
