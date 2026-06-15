
plugins {
    alias(libs.plugins.android.library)
    id("com.google.devtools.ksp")
}
apply(from = "$rootDir/compose-module.gradle")
android {
    namespace = "com.sayatcode.core_ui"
}
dependencies{
    api(Lyritics.LYRICIST)
    ksp(Lyritics.LYRICIST_PROCESSOR)
}
ksp {
    arg("lyricist.packageName", "com.sayatcode.core_ui")
    // НЕ ставь internalVisibility = true — иначе другие модули не увидят
}
