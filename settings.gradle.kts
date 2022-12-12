rootProject.name = "2048-app"

pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion
    }
}

include("domain")
include("core")
