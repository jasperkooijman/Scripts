apply(plugin = "java")

val publishProjects = if (rootProject.extra.has("publishProjects")) {
    @Suppress("UNCHECKED_CAST")
    rootProject.extra["publishProjects"] as Iterable<Project>
} else {
    subprojects.filter { it.name.contains("api") }
}

configure(publishProjects) {
    apply(plugin = "maven-publish")

    extensions.configure<PublishingExtension> {
        repositories {
            maven {
                name = "MiloMade0GitHubPackages"
                url = uri("https://maven.pkg.github.com/jasperkooijman/MiloMade0-Database")
                credentials {
                    username = findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                    password = findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
                }
            }
        }

        publications {
            create<MavenPublication>("snapshot") {
                from(components["java"])
                artifactId = project.name
            }
        }
    }
}
