apply(plugin = "java")

val javadocProjects = if (rootProject.extra.has("javadocProjects")) {
    @Suppress("UNCHECKED_CAST")
    rootProject.extra["javadocProjects"] as Iterable<Project>
} else {
    subprojects.filter { it.name.contains("api") }
}

tasks.named<Javadoc>("javadoc") {
    source(javadocProjects.map { it.extensions.getByType<SourceSetContainer>()["main"].allJava })
    classpath = files(javadocProjects.map { it.extensions.getByType<SourceSetContainer>()["main"].compileClasspath })
    destinationDir = file("${buildDir}/docs/javadoc")
}

subprojects {
    tasks.named<Javadoc>("javadoc") {
        enabled = false
    }
}
