package `fun`.hygames.kotlinutils

import com.hypixel.hytale.server.core.plugin.JavaPlugin
import com.hypixel.hytale.server.core.plugin.JavaPluginInit
import `fun`.hygames.kotlinutils.codeInitialization.CodeInitializer
import `fun`.hygames.kotlinutils.codeInitialization.PluginsCounter
import `fun`.hygames.kotlinutils.codeInitialization.Run
import javax.annotation.Nonnull

class HytaleKotlinUtils(@Nonnull init: JavaPluginInit) : JavaPlugin(init) {
    override fun setup() {
        val countOfPlugins = PluginsCounter.countPlugins(identifier) + 1 // + 1 Because HytaleKotlinUtils using HytaleKotlinUtils, but cant count self

        CodeInitializer.setPluginsCount(countOfPlugins)

        CodeInitializer.addPlugin(this)
    }

    companion object {
        @Run
        fun test() {
            println("Test from HKU!")
        }
    }
}