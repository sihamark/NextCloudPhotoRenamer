pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("jvm").version(extra["kotlin.version"] as String)
        id("org.jetbrains.compose").version(extra["compose.version"] as String)
        id ("org.jetbrains.kotlin.plugin.serialization").version(extra["kotlin.version"] as String)
    }
}

rootProject.name = "NextCloudPhotoRenamer"
