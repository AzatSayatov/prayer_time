plugins {
    alias(libs.plugins.android.library)
}
apply(from = "$rootDir/base-module.gradle")
android {
    namespace = "com.sayatcode.perday_data"
}
dependencies{
    implementation(project(Modules.core))
    implementation(Adhan.kotlinxDateTime)
}