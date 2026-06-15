plugins {
    alias(libs.plugins.android.library)
}
apply(from = "$rootDir/base-module.gradle")
android {
    namespace = "com.sayatcode.compass_data"
}