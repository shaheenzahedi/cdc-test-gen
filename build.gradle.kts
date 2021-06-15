plugins {
    kotlin("jvm") version "1.4.32"
    application
}
repositories {
    mavenCentral()
}
val koinVersion: String = "3.0.1"

dependencies {
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("com.squareup:javapoet:1.13.0")
    implementation("com.squareup:kotlinpoet:1.8.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.11.3")

    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-core-ext:$koinVersion")
//    testImplementation("org.koin:koin-test:$koinVersion")
//    testImplementation("org.koin:koin-test-junit4:$koinVersion")
    testImplementation("io.insert-koin:koin-test-junit5:$koinVersion")
}

configurations {
    "implementation" {
        resolutionStrategy.failOnVersionConflict()
    }
}

group = "me.shaheen"
version = "1.0-SNAPSHOT"
description = "cdc-test-gen"


tasks.test {
    useJUnitPlatform()
}