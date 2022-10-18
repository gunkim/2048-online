import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val applicationVersion: String by project
val kotlinVersion: String by project
val jdkVersion: String by project
val junitVersion: String by project
val assertJVersion: String by project

plugins {
    java
    kotlin("jvm")
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")

    group = "io.github.gunkim"
    version = applicationVersion

    repositories {
        mavenCentral()
    }
}

subprojects {
    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib:${kotlinVersion}")
        testImplementation("org.junit.jupiter:junit-jupiter:${junitVersion}")
        testImplementation("org.assertj:assertj-core:${assertJVersion}")
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = jdkVersion
    }
    tasks.test {
        useJUnitPlatform()
    }
}