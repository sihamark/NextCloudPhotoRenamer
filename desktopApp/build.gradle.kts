import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    jvmToolchain(17)
    sourceSets {
        jvmMain.dependencies {
//            implementation(project(":shared"))
            implementation(compose.desktop.currentOs)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.sardine)
            implementation(libs.napier)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "NextCloudPhotoRenamer"
            packageVersion = "1.0.0"
        }
    }
}