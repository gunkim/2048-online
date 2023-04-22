package io.github.gunkim.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("io.github.gunkim.data")
@ComponentScan("io.github.gunkim.application.config")
open class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
