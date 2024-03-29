import org.jetbrains.intellij.ideaDir

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.6.20"
    id("org.jetbrains.intellij") version "1.5.2"
}

group = "com.dust.small"
version = "1.0.2-SNAPSHOT"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    version.set("2021.2")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf("android"))


}


tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    patchPluginXml {
        sinceBuild.set("212")
        untilBuild.set("222.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }

    runIde{
//        ideaDir("/Applications/Android Studio.app/Contents")
//        ideaDir ("/Applications/Android Studio.app/Contents")
//        ideDir.set(File("/Applications/Android Studio.app/Contents"))
//        autoReloadPlugins.set(true)
//        ideDirectory '/Applications/Android Studio.app/Contents'
//        ideDir.set(file("/Applications/Android Studio.app/Contents"))

//        ideDir = '/Applications/Android Studio.app/Contents'
//        autoReloadPlugins = true
    }


}
