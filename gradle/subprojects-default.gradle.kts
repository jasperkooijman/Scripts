subprojects {
    apply(plugin = "java")

    group = rootProject.group
    version = rootProject.version

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        add("compileOnly", "org.jetbrains:annotations:23.0.0")
    }
}
