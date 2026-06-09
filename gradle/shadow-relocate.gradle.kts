import java.util.jar.JarFile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val excludeRelocation = if (rootProject.extra.has("excludeRelocation")) {
    @Suppress("UNCHECKED_CAST")
    rootProject.extra["excludeRelocation"] as Iterable<String>
} else {
    emptyList()
}

tasks.named<ShadowJar>("shadowJar") {
    val packages = HashSet<String>()

    project.configurations.getByName("runtimeClasspath").files.forEach { jar ->
        val apiProject = project.parent?.childProjects?.get("api")
        if (apiProject != null && apiProject.configurations.getByName("runtimeClasspath").files.contains(jar)) {
            return@forEach
        }

        JarFile(jar).use { jarFile ->
            jarFile.stream()
                .map { it.name }
                .filter { it != "module-info.class" }
                .filter { !it.startsWith("META-INF") }
                .filter { it.endsWith(".class") }
                .map { it.substring(0, it.lastIndexOf('/')).replace('/', '.') }
                .forEach { packages.add(it) }
        }
    }

    packages.removeIf { it.startsWith(project.group.toString()) }
    packages.removeIf { pkg -> excludeRelocation.any { pkg.startsWith(it) } }

    for (lib in packages) {
        relocate(lib, "${project.group}.lib.$lib")
    }
}
