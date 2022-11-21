plugins {
    id("jp.co.yumemi.android.codecheck.android.library")
    id("jp.co.yumemi.android.codecheck.android.hilt")
}

android {
    namespace = "jp.co.yumemi.android.codecheck.core.data"

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:network"))
}