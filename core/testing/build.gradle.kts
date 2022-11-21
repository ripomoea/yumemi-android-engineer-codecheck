plugins {
    id("jp.co.yumemi.android.codecheck.android.library")
    id("jp.co.yumemi.android.codecheck.android.hilt")
}

android.namespace = "jp.co.yumemi.android.codecheck.core.testing"

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:data"))

    api(libs.junit4)
    api(libs.androidx.test.core)
    api(libs.kotlinx.coroutines.test)

    api(libs.androidx.test.espresso.core)
    api(libs.androidx.test.runner)
    api(libs.androidx.test.rules)
    api(libs.hilt.android.testing)
}
