plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "com.szwester"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation(kotlin("test"))
    implementation(kotlin("script-runtime"))
    // https://mvnrepository.com/artifact/org.jgrapht/jgrapht-core
    implementation("org.jgrapht:jgrapht-core:1.5.2")

}

tasks.test {
    useJUnitPlatform()
}
