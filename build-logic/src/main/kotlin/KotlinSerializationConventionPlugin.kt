import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class KotlinSerializationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.serialization")
            }
        }
    }
}

