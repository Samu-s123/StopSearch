// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.android) apply false

    //plugin ksp
    alias(libs.plugins.google.devtools.ksp) apply false

    //plugin hilt
    alias(libs.plugins.dagger.hilt.android) apply false

    //plugin serializable
    alias(libs.plugins.jetbrains.serialization) apply false
}

buildscript {
    dependencies {
        classpath(libs.secrets.gradle.plugin)
    }
}

