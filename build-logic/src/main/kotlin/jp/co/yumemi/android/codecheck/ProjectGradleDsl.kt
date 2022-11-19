package jp.co.yumemi.android.codecheck

import java.util.Optional
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.getByType

fun DependencyHandlerScope.implementation(
    artifact: Optional<Provider<MinimalExternalModuleDependency>>
) {
    add("implementation", artifact.get())
}

fun DependencyHandlerScope.kapt(
    artifact: Optional<Provider<MinimalExternalModuleDependency>>
) {
    add("kapt", artifact.get())
}

val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")
