plugins {
    id("jp.co.yumemi.android.codecheck.android.library")
}

android {
    namespace = "jp.co.yumemi.android.codecheck.core.styleguide"

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(libs.android.material)
}