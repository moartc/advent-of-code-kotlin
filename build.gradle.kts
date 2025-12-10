plugins {
    kotlin("jvm") version "2.1.0"
    application
}

group = "com.moartc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation(kotlin("test"))
    implementation(kotlin("script-runtime"))
    // https://mvnrepository.com/artifact/org.jgrapht/jgrapht-core
    implementation("org.jgrapht:jgrapht-core:1.5.2")
    implementation("com.google.ortools:ortools-java:9.12.4544")

}

tasks.test {
    useJUnitPlatform()
}
