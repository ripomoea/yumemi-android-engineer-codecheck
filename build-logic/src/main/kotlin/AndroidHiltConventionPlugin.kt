import jp.co.yumemi.android.codecheck.implementation
import jp.co.yumemi.android.codecheck.kapt
import jp.co.yumemi.android.codecheck.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

@Suppress("unused")
class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.kapt")
                apply("dagger.hilt.android.plugin")
            }

            dependencies {
                implementation(libs.findLibrary("hilt.android"))
                kapt(libs.findLibrary("hilt.compiler"))
            }
            extensions.configure<KaptExtension> {
                correctErrorTypes = true
            }
        }
    }
}