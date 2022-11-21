plugins {
    id("jp.co.yumemi.android.codecheck.android.application")
    id("jp.co.yumemi.android.codecheck.android.hilt")
}

android {
    defaultConfig {
        applicationId = "jp.co.yumemi.android.codecheck"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "jp.co.yumemi.android.codecheck.core.testing.YumemiAndroidCodeCheckTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(project(":core:data"))
    implementation(project(":core:styleguide"))
    implementation(project(":feature:github"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    implementation(libs.androidx.constraintLayout)

    testImplementation(kotlin("test"))
    testImplementation(project(":core:testing"))
    androidTestImplementation(kotlin("test"))
    androidTestImplementation(project(":core:testing"))
}
