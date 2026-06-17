plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
}

apply(from = "$rootDir/base-module.gradle")
android {
    namespace = "com.sayatcode.calendar_data"
}
dependencies{
    implementation(libs.androidx.runtime)
    ksp(Room.roomCompiler)
    implementation(Room.roomKtx)
    implementation(Room.roomRuntime)

    implementation(project(Modules.calendarDomain))
}