plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    kotlin("parcelize")
}

android {
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.androidx.junit)
    // testImplementation(libs.truth)
    // testImplementation(libs.coroutines.test)
    // testImplementation(libs.turbine)
    // testImplementation(libs.compose.ui.test.junit4)
    // testImplementation(libs.mockk)
    // testImplementation(libs.mockwebserver)

    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    // androidTestImplementation(libs.truth)
    // androidTestImplementation(libs.coroutines.test)
    // androidTestImplementation(libs.turbine)
    // androidTestImplementation(libs.compose.ui.test.junit4)
    // androidTestImplementation(libs.mockk)
    // androidTestImplementation(libs.mockwebserver)
    // androidTestImplementation(libs.hilt.testing)
}