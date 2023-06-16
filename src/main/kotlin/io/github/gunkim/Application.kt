package io.github.gunkim

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableEncryptableProperties
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
