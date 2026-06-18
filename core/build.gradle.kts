plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}
apply(from = "$rootDir/base-module.gradle")
android {
    namespace = "com.sayatcode.core"
}
dependencies{
    implementation(libs.androidx.runtime)
    ksp(Room.roomCompiler)
    implementation(Room.roomKtx)
    implementation(Room.roomRuntime)

    implementation(Lyritics.LYRICIST)
//    ksp(Lyritics.LYRICIST_PROCESSOR)
//    implementation("com.google.code.gson:gson:2.10.1")
//    implementation(libs.kotlinx.serialization.json)
        implementation(project(Modules.calendarData))

}

//ksp {
//    arg("lyricist.packageName", "com.sayatcode.prayer")
//    arg("lyricist.moduleName", "strings")       // sgeneriruet LocalStringsStrings -> luchshe nazvat "core" ili podobnoe
//    arg("lyricist.internalVisibility", "true")  // esli ne nado eksportirovat naruzhу
//}