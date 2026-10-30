plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    `maven-publish`
}

group = "com.sayatcode"
version = "0.1.0"

kotlin {
    jvmToolchain(21) // Adhan2 is compiled for Java 21
    explicitApi()
}

java {
    withSourcesJar()
}

dependencies {
    // Worldwide calculation. Exposed as `api` is NOT needed: its types never appear in our public API.
    implementation(Adhan.library)
    implementation(Adhan.kotlinxDateTime)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
}

publishing {
    publications {
        create<MavenPublication>("release") {
            from(components["java"])
            artifactId = "prayer-times"
            pom {
                name.set("Prayer Times")
                description.set("Prayer times from two explicit sources: official Turkmenistan table or worldwide astronomical calculation (Adhan).")
            }
        }
    }
}
