import org.jetbrains.gradle.ext.runConfigurations
import org.jetbrains.gradle.ext.settings

plugins {
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.4"
    kotlin("jvm") version "2.3.0"
    id("com.gradleup.shadow") version "9.3.1"
}

group = "fun.hygames"
version = "1.0.2"

val hytaleHome = properties["hytaleHome"]
val patchline = properties["patchline"]
val includesPack = properties["includes_pack"]
val loadUserMods = properties["load_user_mods"]

repositories {
    mavenCentral()
    maven {
        name = "hytale"
        url = uri("https://maven.hytale.com/release")
    }
}

configurations {
    shadow
}

val kotlinLib = "org.jetbrains.kotlin:kotlin-stdlib:2.3.0"
val libraries = listOf(
    kotlinLib,
    "org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.3.0",
    "org.jetbrains.kotlin:kotlin-stdlib-jdk7:2.3.0",
    "org.jetbrains.kotlin:kotlin-reflect:2.3.0",
    "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2",
    "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.10.2",
    "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.10.2",
    "org.jetbrains.kotlinx:kotlinx-serialization-core-jvm:1.9.0",
    "org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:1.9.0",
    "org.jetbrains.kotlinx:kotlinx-serialization-cbor-jvm:1.9.0",
    "org.jetbrains.kotlinx:atomicfu-jvm:0.29.0",
    "org.jetbrains.kotlinx:kotlinx-datetime-jvm:0.7.1",
    "org.jetbrains.kotlinx:kotlinx-io-core-jvm:0.8.2",
    "org.jetbrains.kotlinx:kotlinx-io-bytestring-jvm:0.8.2"
)

dependencies {
    compileOnly("com.hypixel.hytale:Server:+")
    libraries.forEach {
        implementation(it)
        shadow(it)
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(25)
}

tasks.shadowJar {
        archiveClassifier.set("")
    }

tasks.build {
    dependsOn(tasks.shadowJar)
}


