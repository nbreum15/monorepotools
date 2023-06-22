fun properties(key: String) = providers.gradleProperty(key)

plugins {
    application
    id("org.graalvm.buildtools.native") version "0.9.12"
}

group = properties("pluginGroup").get()
version = properties("pluginVersion").get()

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

tasks.test {
    systemProperty("cli.version", version)
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
        resources.includedPatterns.add(".*txt$")
    }
    toolchainDetection.set(false)
}

abstract class WriteVersionTxtTask : DefaultTask() {
    @get:Input
    abstract val versionNumber: Property<String>

    @get:Input
    abstract val projectDirectory: Property<File>

    @TaskAction
    fun writeFile() {
        val f = File(projectDirectory.get(), "src/main/resources/version.txt")
        f.createNewFile()
        f.writeText(versionNumber.get())
    }
}

tasks.register<WriteVersionTxtTask>("writeVersionFile") {
    versionNumber.set(version.toString())
    projectDirectory.set(projectDir)
}

tasks.getByName("processResources").dependsOn("writeVersionFile")