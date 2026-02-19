package `fun`.hygames.kotlinutils

import com.hypixel.hytale.server.core.plugin.JavaPlugin
import com.hypixel.hytale.server.core.plugin.JavaPluginInit
import `fun`.hygames.kotlinutils.codeInitialization.CodeInitializer
import `fun`.hygames.kotlinutils.codeInitialization.PluginsCounter
import `fun`.hygames.kotlinutils.codeInitialization.Run
import javax.annotation.Nonnull

class HytaleKotlinUtils(@Nonnull init: JavaPluginInit) : JavaPlugin(init) {
    override fun setup() {
        PluginsCounter.countJarsWithCodeInitializer(dataDirectory.parent.toFile())

        CodeInitializer.addPlugin("fun.hygames.kotlinutils", HytaleKotlinUtils::class.java)
    }

    companion object {
        @Run
        fun test() {
            println("Test from HKU!")
        }
    }
}