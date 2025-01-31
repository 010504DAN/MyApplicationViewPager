plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp")
    alias(libs.plugins.google.gms.google.services)
    id("com.google.firebase.crashlytics")
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
    //splashscreen
    implementation(libs.androidx.core.splashscreen)
    //circleindicator
    implementation (libs.circleindicator)
    //lottie
    implementation (libs.lottie)
    //glide
    implementation (libs.glide)
    //navigation
    implementation (libs.androidx.navigation.fragment.ktx)
    implementation (libs.androidx.navigation.ui)
    //room
    implementation (libs.androidx.room.ktx)
    ksp (libs.androidx.room.compiler)
    implementation(libs.androidx.gridlayout)
    //firebase
    implementation(libs.firebase.messaging)
    implementation(platform(libs.firebase.bom))
    implementation(libs.play.services.auth)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.material.v1110)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}