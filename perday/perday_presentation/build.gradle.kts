plugins {
    alias(libs.plugins.android.library)
}
apply(from = "$rootDir/compose-module.gradle")
android {
    namespace = "com.sayatcode.perday_presentation"
}

dependencies{
    implementation(project(Modules.core))
    implementation(project(Modules.coreUi))
}