plugins {
    application
    id("org.graalvm.buildtools.native") version "0.9.12"
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    implementation("info.picocli:picocli:4.7.4")
    compileOnly("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
    annotationProcessor("info.picocli:picocli-codegen:4.1.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.15.2")

    testCompileOnly("org.projectlombok:lombok:1.18.28")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.28")
    implementation(project(":lib"))
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainClass.set("com.github.nbreum15.monorepotools.CommitMessageCLI")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

graalvmNative {
    binaries.all {
        resources.autodetect()
    }
    toolchainDetection.set(false)
}