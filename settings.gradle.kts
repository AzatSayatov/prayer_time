pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Prayer"
include(":app")
include(":core")
include(":core_ui")
include(":onboarding")
include(":onboarding:onboarding_presentation")
include(":onboarding:onboarding_domain")
include(":compass")
include(":compass:compass_presentation")
include(":compass:compass_domain")
include(":compass:compass_data")
include(":perday")
include(":perday:perday_presentation")
include(":perday:perday_domain")
include(":perday:perday_data")
include(":calendar")
include(":calendar:calendar_presentaion")
include(":calendar:calendar_domain")
include(":calendar:calendar_data")
include(":prayertimes")
include(":settings")
include(":settings:settings_presentatioin")
include(":settings:settings_domain")
include(":settings:settings_data")
