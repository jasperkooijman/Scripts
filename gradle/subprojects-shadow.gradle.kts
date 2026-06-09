import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val shadowProjects = if (rootProject.extensions.extraProperties.has("shadowProjects")) {
    @Suppress("UNCHECKED_CAST")
    rootProject.extensions.extraProperties["shadowProjects"] as Iterable<Project>
} else {
    subprojects
}

configure(shadowProjects) {
    apply(plugin = "java-library")
    apply(plugin = "com.gradleup.shadow")

    tasks.named<ShadowJar>("shadowJar") {
        archiveClassifier.set("")
        archiveAppendix.set(project.name)

        // Needed for libraries like Ebean that use META-INF/services.
        mergeServiceFiles()
    }

    tasks.named("build") {
        dependsOn(tasks.named("shadowJar"))
    }
}
