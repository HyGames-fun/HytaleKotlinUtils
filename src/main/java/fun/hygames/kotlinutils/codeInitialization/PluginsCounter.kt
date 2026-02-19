package `fun`.hygames.kotlinutils.codeInitialization

import java.io.File
import java.io.IOException
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

object PluginsCounter {

    fun countJarsWithCodeInitializer(dir: File) : Int {
        var count = 0

        val files = dir.listFiles() ?: return 0

        for (file in files) {
            if (file.isFile &&
                file.name.lowercase().endsWith(".jar") &&
                containsCodeInitializer(file)
            ) {
                count++
            }
        }

        println("Counted $count plugins!")

        return count
    }


    // TODO Rewrite
    private fun containsCodeInitializer(jarFile: File): Boolean {
        try {
            ZipFile(jarFile).use { zip ->
                val entries = zip.entries()
                while (entries.hasMoreElements()) {
                    val entry: ZipEntry = entries.nextElement()
                    val name = entry.getName()
                    if (name.equals(".codeinitializer", ignoreCase = true) || name.equals(
                            ".codeinit",
                            ignoreCase = true
                        )
                    ) {
                        return true
                    }
                }
            }
        } catch (e: IOException) {
            System.err.println("Error on reading JAR file: " + jarFile.getPath())
            e.printStackTrace()
        }

        return false
    }

}