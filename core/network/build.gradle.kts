plugins {
    id("jp.co.yumemi.android.codecheck.android.library")
    id("jp.co.yumemi.android.codecheck.android.hilt")
    id("jp.co.yumemi.android.codecheck.kotlin.serialization")
}

android {
    namespace = "jp.co.yumemi.android.codecheck.core.network"

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okHttp)
    implementation(libs.ktor.client.contentNegotiation)
    implementation(libs.ktor.kotlinx.serialization)
    implementation(libs.okHttp.loggingInterceptor)
}