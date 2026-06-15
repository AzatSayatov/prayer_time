plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}
configurations.configureEach {
    resolutionStrategy {
        force("org.jetbrains:annotations:23.0.0")
    }
    exclude(group = "com.intellij", module = "annotations")
}
android {
    namespace = "com.sayatcode.prayer"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.sayatcode.prayer"
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(Compose.ui)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.hiltNavigationCompose)
    implementation(Compose.material)
    implementation(Compose.runtime)
    implementation(Compose.navigation)
    implementation(Compose.viewModelCompose)
    implementation(Compose.activityCompose)

    implementation(DaggerHilt.hiltAndroid)
    implementation(libs.androidx.material3)
    ksp(DaggerHilt.hiltCompiler)

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)

    implementation(Coil.coilCompose)

    implementation(Google.material)
    implementation(Compose.materialIconsExtended)

    implementation(Retrofit.okHttp)
    implementation(Retrofit.retrofit)
    implementation(Retrofit.okHttpLoggingInterceptor)
    implementation(Retrofit.moshiConverter)

//    implementation("com.google.code.gson:gson:2.10.1")
    ksp(Room.roomCompiler)
    implementation(Room.roomKtx)
    implementation(Room.roomRuntime)
    implementation(Adhan.library)

    testImplementation(Testing.junit4)
    testImplementation(Testing.junitAndroidExt)
    testImplementation(Testing.truth)
    testImplementation(Testing.coroutines)
    testImplementation(Testing.turbine)
    testImplementation(Testing.composeUiTest)
    testImplementation(Testing.mockk)
    testImplementation(Testing.mockWebServer)

    androidTestImplementation(Testing.junit4)
    androidTestImplementation(Testing.junitAndroidExt)
    androidTestImplementation(Testing.truth)
    androidTestImplementation(Testing.coroutines)
    androidTestImplementation(Testing.turbine)
    androidTestImplementation(Testing.composeUiTest)
    androidTestImplementation(Testing.mockkAndroid)
    androidTestImplementation(Testing.mockWebServer)
    androidTestImplementation(Testing.hiltTesting)
    kspAndroidTest(DaggerHilt.hiltCompiler)
    androidTestImplementation(Testing.testRunner)
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.activity.compose)
//    implementation(libs.androidx.compose.material3)
//    implementation(libs.androidx.compose.ui)
//    implementation(libs.androidx.compose.ui.graphics)
//    implementation(libs.androidx.compose.ui.tooling.preview)
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    testImplementation(libs.junit)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(libs.androidx.junit)
//    debugImplementation(libs.androidx.compose.ui.test.manifest)
//    debugImplementation(libs.androidx.compose.ui.tooling)
//
//    implementation(libs.versions.hilt)

    //Modules
    implementation(project(Modules.core))
    implementation(project(Modules.coreUi))
    implementation(project(Modules.calendarData))
    implementation(project(Modules.calendarDomain))
    implementation(project(Modules.calendarPresentation))
    implementation(project(Modules.compassData))
    implementation(project(Modules.compassDomain))
    implementation(project(Modules.compassPresentation))
    implementation(project(Modules.onboardingDomain))
    implementation(project(Modules.onboardingPresentation))
    implementation(project(Modules.perdayData))
    implementation(project(Modules.perdayDomain))
    implementation(project(Modules.perdayPresentation))
    implementation(project(Modules.settingsData))
    implementation(project(Modules.settingsDomain))
    implementation(project(Modules.settingsPresentation))

//    implementation(Lyritics.LYRICIST)
//    ksp(Lyritics.LYRICIST_PROCESSOR)


}
//ksp {
//    arg("lyricist.packageName", "com.sayatcode.prayer")
////    arg("lyricist.moduleName", "core")       // sgeneriruet LocalStringsStrings -> luchshe nazvat "core" ili podobnoe
//    arg("lyricist.internalVisibility", "true")  // esli ne nado eksportirovat naruzhу
//}