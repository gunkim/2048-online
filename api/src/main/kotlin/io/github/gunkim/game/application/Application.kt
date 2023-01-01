package io.github.gunkim.game.application

import io.github.gunkim.game.application.socket.room.CreateController
import io.github.gunkim.game.domain.User
import io.github.gunkim.game.domain.Users
import io.github.gunkim.game.domain.vo.Social
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Component
import java.util.*

@ComponentScan("io.github.gunkim.game.data")
@ComponentScan("io.github.gunkim.game.application.config")
@SpringBootApplication
open class Application {
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@Component
class initData(
    val users: Users
): CommandLineRunner {
    override fun run(vararg args: String?) {
        CreateController.userId = users.save(User(id = UUID.randomUUID(), name = "gunkim", email = "gunkim.dev@gmail.com", social = Social.GOOGLE)).id
    }

}
