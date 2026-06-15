plugins {
    alias(libs.plugins.android.library)
}
apply(from = "$rootDir/compose-module.gradle")
android {
    namespace = "com.sayatcode.compass_ui"
}