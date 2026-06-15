plugins {
    alias(libs.plugins.android.library)
}
apply(from = "$rootDir/compose-module.gradle")
android {
    namespace = "com.sayatcode.calendar_presentaion"
}