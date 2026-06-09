@Suppress("UNCHECKED_CAST")
val shadowCopy = rootProject.extra["shadowCopy"] as Map<Project, String>

for ((projectToCopy, targetDirectory) in shadowCopy) {
    configure(projectToCopy) {
        tasks.named("shadowJar") {
            doLast {
                val shadowJar = this@named as com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
                copy {
                    from("build/libs/${shadowJar.archiveFileName.get()}")
                    into(targetDirectory)
                }
            }
        }
    }
}
