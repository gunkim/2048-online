package io.github.gunkim.game.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("io.github.gunkim.game.data")
@ComponentScan("io.github.gunkim.game.application.config")
open class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
