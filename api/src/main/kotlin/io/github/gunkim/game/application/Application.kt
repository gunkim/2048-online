package io.github.gunkim.game.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan("io.github.gunkim.game.data")
@ComponentScan("io.github.gunkim.game.application.config")
@SpringBootApplication
open class Application {
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
