import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType
import org.jlleitschuh.gradle.ktlint.tasks.GenerateReportsTask


plugins {
    val kotlinVersion = "1.9.24"
    val springBootVersion = "3.3.2"

    id("org.springframework.boot") version springBootVersion apply false
    id("io.spring.dependency-management") version "1.1.6" apply false
    id("org.jlleitschuh.gradle.ktlint").version("12.1.1")

    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion apply false
    kotlin("plugin.jpa") version kotlinVersion apply false
}

allprojects {
    val jdkVersion = "21"

    group = "com.wiyb"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    apply {
        plugin("org.jlleitschuh.gradle.ktlint")
    }

    ktlint {
        reporters {
            reporter(ReporterType.PLAIN)
            reporter(ReporterType.JSON)
        }
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = jdkVersion
        targetCompatibility = jdkVersion
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = jdkVersion
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        jvmArgs(
            "-Xshare:off",
            "-XX:+EnableDynamicAgentLoading",
            "-Dspring.profiles.active=test"
        )
    }

    tasks.withType<GenerateReportsTask> {
        reportsOutputDirectory.set(
            rootProject.layout.buildDirectory.dir("reports/ktlint/${project.name}")
        )
    }
}

subprojects {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

    dependencies {
        // Implementation
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("com.github.f4b6a3:tsid-creator:5.2.6")

        // Kapt
        kapt("org.springframework.boot:spring-boot-configuration-processor")

        // Test
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "com.vaadin.external.google", module = "android-json")
        }

        testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
        testImplementation("org.mockito:mockito-core:5.12.0")

        testImplementation("org.testcontainers:testcontainers:1.20.1")
        testImplementation("org.testcontainers:junit-jupiter:1.20.1")
        testImplementation("org.testcontainers:mysql:1.20.1")
        testImplementation("org.testcontainers:jdbc:1.20.1")

        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    tasks.test {
        useJUnitPlatform {
            excludeTags("develop")
        }
    }

    tasks.register<Test>("unitTest") {
        group = "verification"
        useJUnitPlatform {
            excludeTags("develop", "context")
        }
    }

    tasks.register<Test>("contextTest") {
        group = "verification"
        useJUnitPlatform {
            includeTags("context")
        }
    }

    tasks.register<Test>("developTest") {
        group = "verification"
        useJUnitPlatform {
            includeTags("develop")
        }
    }
}
