buildscript {
    repositories {
        google()
    }
    dependencies {
        val nav_version = "2.8.4"
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
    }

}


plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.devtools.ksp") version "2.1.0-1.0.29" apply false
    alias(libs.plugins.google.gms.google.services) apply false
    id("com.google.firebase.crashlytics") version "3.0.2" apply false
}