plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "io.github.ahmedmani.pairipfix"
    compileSdk = 36

    defaultConfig {
        applicationId = "io.github.ahmedmani.pairipfix"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    compileOnly(files("libs/api-82.jar"))
}