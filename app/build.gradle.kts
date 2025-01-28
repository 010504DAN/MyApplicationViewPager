plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp")
    id ("kotlin-kapt")
}


android {
    namespace = "com.example.myapplicationviewpager"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myapplicationviewpager"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures{
        viewBinding = true
    }
}

val nav_version = "2.8.4"
val lottieVersion = "6.6.2"
val room_version = "2.6.1"
dependencies {

    implementation ("me.relex:circleindicator:2.1.6")

    implementation ("com.airbnb.android:lottie:$lottieVersion")

    implementation ("com.github.bumptech.glide:glide:4.16.0")

    implementation ("androidx.navigation:navigation-fragment:$nav_version")
    implementation ("androidx.navigation:navigation-ui:$nav_version")

//    implementation ("androidx.room:room-runtime:2.5.0-alpha02")
    implementation ("androidx.room:room-ktx:2.5.0-alpha02")
    kapt ("androidx.room:room-compiler:2.5.0-alpha02")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}