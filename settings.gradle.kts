rootProject.name = "server"

include(
    "core",
    "storage",
    "support:logging"
)

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
