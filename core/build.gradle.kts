import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":storage"))
    implementation(project(":support:logging"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
}

tasks.named<BootJar>("bootJar") {
    enabled = true
}

tasks.named<Jar>("jar") {
    enabled = false
}
