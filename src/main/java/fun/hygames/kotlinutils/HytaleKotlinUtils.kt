package `fun`.hygames.kotlinutils

import com.hypixel.hytale.logger.HytaleLogger
import com.hypixel.hytale.server.core.event.events.BootEvent
import com.hypixel.hytale.server.core.event.events.ShutdownEvent
import com.hypixel.hytale.server.core.plugin.JavaPlugin
import com.hypixel.hytale.server.core.plugin.JavaPluginInit
import `fun`.hygames.kotlinutils.codeInitialization.CodeInitializer
import `fun`.hygames.kotlinutils.codeInitialization.RunNodeManager
import `fun`.hygames.kotlinutils.codeInitialization.typeProcessor.TypeProcessors
import `fun`.hygames.kotlinutils.codeInitialization.typeProcessor.builtin.*
import `fun`.hygames.kotlinutils.internal.ErrorReport
import `fun`.hygames.kotlinutils.internal.PluginsCounter
import javax.annotation.Nonnull

class HytaleKotlinUtils(@Nonnull init: JavaPluginInit) : JavaPlugin(init) {
    override fun setup() {
        ErrorReport.setLogger(Companion.logger)
        val countOfPlugins = PluginsCounter.countPlugins(identifier) + 1 // + 1 Because HytaleKotlinUtils using HytaleKotlinUtils, but cant count self

        infoLogger("Counted $countOfPlugins plugins")

        CodeInitializer.setPluginsCount(countOfPlugins)

        TypeProcessors.register(COMMAND, CommandTypeProcessor(), this)
        TypeProcessors.register(ENTITY_SYSTEM, EntitySystemTypeProcessor(), this)
        TypeProcessors.register(CHUNK_SYSTEM, ChunkSystemTypeProcessor(), this)

        CodeInitializer.addPlugin(this)

        eventRegistry.register(ShutdownEvent::class.java, ::shutdown)
        eventRegistry.register(BootEvent::class.java, ::boot)
    }

    fun shutdown(event: ShutdownEvent?){
        RunNodeManager.stopNode.run()
    }

    fun boot(event: BootEvent?){
        ErrorReport.log()
    }

    companion object {
        val logger = HytaleLogger.forEnclosingClass()
        val infoLogger: HytaleLogger.Api = logger.atInfo()
    }
}