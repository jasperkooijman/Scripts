val hasChanges = "git diff-index HEAD".runCommand().trim().isNotEmpty()
val revision = "git rev-parse --short HEAD".runCommand().trim()

version = "v-$revision${if (hasChanges) "-SNAPSHOT" else ""}"

fun String.runCommand(): String =
    ProcessBuilder(split(" "))
        .redirectErrorStream(true)
        .start()
        .inputStream
        .bufferedReader()
        .readText()
