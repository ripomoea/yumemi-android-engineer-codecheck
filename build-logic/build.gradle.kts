plugins {
    `kotlin-dsl`
}

group = "jp.co.yumemi.android.codecheck.buildlogic"

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.kotlin.serialization.gradlePlugin)
    implementation(libs.androidx.navigation.safeArgs.gradlePlugin)
    implementation(libs.hilt.android.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "jp.co.yumemi.android.codecheck.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "jp.co.yumemi.android.codecheck.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidHilt") {
            id = "jp.co.yumemi.android.codecheck.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("kotlinParcelize") {
            id = "jp.co.yumemi.android.codecheck.kotlin.parcelize"
            implementationClass = "KotlinParcelizeConventionPlugin"
        }
        register("kotlinSerialization") {
            id = "jp.co.yumemi.android.codecheck.kotlin.serialization"
            implementationClass = "KotlinSerializationConventionPlugin"
        }
    }
}