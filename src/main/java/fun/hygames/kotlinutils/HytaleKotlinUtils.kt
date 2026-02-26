package `fun`.hygames.kotlinutils

import com.hypixel.hytale.logger.HytaleLogger
import com.hypixel.hytale.server.core.event.events.ShutdownEvent
import com.hypixel.hytale.server.core.plugin.JavaPlugin
import com.hypixel.hytale.server.core.plugin.JavaPluginInit
import `fun`.hygames.kotlinutils.codeInitialization.CodeInitializer
import `fun`.hygames.kotlinutils.codeInitialization.PluginsCounter
import `fun`.hygames.kotlinutils.codeInitialization.RunNodeManager
import `fun`.hygames.kotlinutils.codeInitialization.typeProcessor.CommandTypeProcessor
import `fun`.hygames.kotlinutils.codeInitialization.typeProcessor.TypeProcessors
import javax.annotation.Nonnull

class HytaleKotlinUtils(@Nonnull init: JavaPluginInit) : JavaPlugin(init) {
    override fun setup() {
        val countOfPlugins = PluginsCounter.countPlugins(identifier) + 1 // + 1 Because HytaleKotlinUtils using HytaleKotlinUtils, but cant count self

        println("Counted $countOfPlugins plugins")

        CodeInitializer.setPluginsCount(countOfPlugins)


        TypeProcessors.register("command", CommandTypeProcessor())
        CodeInitializer.addPlugin(this)

        eventRegistry.register(ShutdownEvent::class.java, ::shutdown)
    }

    fun shutdown(event: ShutdownEvent){
        RunNodeManager.stopNode.run()
    }

    companion object {
        val logger = HytaleLogger.forEnclosingClass()
    }
}