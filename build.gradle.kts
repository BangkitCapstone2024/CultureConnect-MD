// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.googleGmsGoogleServices) apply false
    alias(libs.plugins.googleAndroidLibrariesMapsplatformSecretsGradlePlugin) apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.11" apply false
}

buildscript {
    dependencies {
        classpath(libs.secrets.gradle.plugin)
        classpath(libs.google.services)
    }
}