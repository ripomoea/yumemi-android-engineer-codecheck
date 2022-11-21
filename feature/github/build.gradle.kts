plugins {
    id("jp.co.yumemi.android.codecheck.android.library")
    id("jp.co.yumemi.android.codecheck.android.hilt")
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.androidx.navigation.safeArgs)
}

android {
    namespace = "jp.co.yumemi.android.codecheck.feature.github"

    defaultConfig {
        testInstrumentationRunner =
            "jp.co.yumemi.android.codecheck.core.testing.YumemiAndroidCodeCheckTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }
    buildFeatures {
        viewBinding = true
    }
    testOptions {
        unitTests {
            // TODO: android.util.Log エラー回避で導入中, Robolectric などの代替案を考える
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:data"))
    implementation(project(":core:styleguide"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    implementation(libs.androidx.constraintLayout)
    implementation(libs.androidx.recyclerView)

    implementation(libs.androidx.lifecycle.viewModel.ktx)
    implementation(libs.androidx.lifecycle.liveData.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.coil.kt)

    testImplementation(kotlin("test"))
    testImplementation(project(":core:testing"))
    androidTestImplementation(kotlin("test"))
    androidTestImplementation(project(":core:testing"))
}