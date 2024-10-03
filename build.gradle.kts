import java.util.*

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.intellij.platform") version "2.1.0"
}

group = "dev.ja.localize"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        intellijIdeaCommunity("2024.2.3")
        // intellijIdeaCommunity("243.18137.10")

        instrumentationTools()
        pluginVerifier()
    }
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "242.0"
            untilBuild = "243.0"
        }
    }

    pluginVerification {
        ides {
            recommended()
        }
    }
}

// Additional tasks to help testing the localized plugin
tasks {
    val runIdeEnglish by intellijPlatformTesting.runIde.registering {
        task {
            systemProperty("user.language", "en")
            systemProperty("user.region", "US")
            systemProperty("i18n.locale", Locale.ENGLISH.toLanguageTag())
        }
    }

    val runIdeChinese by intellijPlatformTesting.runIde.registering {
        task {
            systemProperty("i18n.locale", Locale.SIMPLIFIED_CHINESE.toLanguageTag())
        }
    }

    val runIdeJapanese by intellijPlatformTesting.runIde.registering {
        task {
            systemProperty("i18n.locale", Locale.JAPANESE.toLanguageTag())
        }
    }

    val runIdeKorean by intellijPlatformTesting.runIde.registering {
        task {
            systemProperty("i18n.locale", Locale.KOREAN.toLanguageTag())
        }
    }
}