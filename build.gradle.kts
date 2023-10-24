plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    testImplementation(kotlin("test"))
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    implementation("io.github.monun:kommand-api:3.1.7")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}