object Build {
    private const val androidBuildToolsVersion = "9.2.1"
    const val androidBuildTools = "com.android.tools.build:gradle:$androidBuildToolsVersion"

    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"

    private const val hiltAndroidGradlePluginVersion = "2.57.1"
    const val hiltAndroidGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltAndroidGradlePluginVersion"

    private const val kspVersion = "2.3.7"
    const val ksp = "com.google.devtools.ksp:symbol-processing-gradle-plugin:$kspVersion"
}