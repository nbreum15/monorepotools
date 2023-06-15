fun properties(key: String) = providers.gradleProperty(key)
tasks {
    wrapper {
        gradleVersion = properties("gradleVersion").get()
    }
}
