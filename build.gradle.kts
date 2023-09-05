import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion: String by project
val jdkVersion: String by project
val junitVersion: String by project
val assertJVersion: String by project

plugins {
    java
    kotlin("jvm")
    id("org.jlleitschuh.gradle.ktlint-idea") version "11.0.0"
    id("org.springframework.boot") version "3.0.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.jetbrains.kotlin.plugin.spring") version "1.5.21"
}

group = "io.github.gunkim"
version = "2023.09.05"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-mustache")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.1")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    testImplementation("org.assertj:assertj-core:$assertJVersion")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.6.3")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = jdkVersion
}

tasks.test {
    useJUnitPlatform()
}
