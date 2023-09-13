rootProject.name = "2048-app"

pluginManagement {
    val kotlinVersion: String by settings
    val ktlintVersion: String by settings
    val springVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val kotlinSpringVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        id("org.jlleitschuh.gradle.ktlint") version ktlintVersion
        id("org.springframework.boot") version springVersion
        id("io.spring.dependency-management") version springDependencyManagementVersion
        id("org.jetbrains.kotlin.plugin.spring") version kotlinSpringVersion
    }
}
