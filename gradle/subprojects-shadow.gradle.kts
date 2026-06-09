val shadowProjects = if (rootProject.extra.has("shadowProjects")) {
    @Suppress("UNCHECKED_CAST")
    rootProject.extra["shadowProjects"] as Iterable<Project>
} else {
    subprojects
}

configure(shadowProjects) {
    apply(plugin = "java")
    apply(plugin = "com.gradleup.shadow")

    tasks.named("shadowJar") {
        this as com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
        archiveClassifier.set("")
        archiveAppendix.set(project.name)
        archiveBaseName.set(rootProject.name)
    }
}
