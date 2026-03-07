package `fun`.hygames.kotlinutils.codeInitialization.typeProcessor

import com.hypixel.hytale.server.core.plugin.JavaPlugin
import `fun`.hygames.kotlinutils.HytaleKotlinUtils.Companion.infoLogger
import `fun`.hygames.kotlinutils.HytaleKotlinUtils.Companion.logger
import `fun`.hygames.kotlinutils.internal.ErrorReport
import `fun`.hygames.kotlinutils.invoke
import java.util.logging.Level

object TypeProcessors {
    private val typeProcessorByName = HashMap<String, TypeProcessor>()
    private val pluginsByTypeProcessorName = HashMap<String, JavaPlugin>()

    fun <T : JavaPlugin> register(name: String, typeProcessor: TypeProcessor, plugin: T) {
        if (pluginsByTypeProcessorName.contains(name)){
            val conflictPluginName = pluginsByTypeProcessorName[name]!!.name
            val error = "Type processors conflict in plugins: [\"$conflictPluginName\", \"${plugin.name}\"]. Type processor with name \"$name\" already registered by \"$conflictPluginName\"!"

            logger.at(Level.SEVERE)(error)
            ErrorReport(error, 1, 1)
            return
        }

        typeProcessorByName[name] = typeProcessor
        pluginsByTypeProcessorName[name] = plugin
        infoLogger("Registered \"${name}\" type processor by ${plugin.name}")
    }

    operator fun get(name: String) : TypeProcessor? {
        return typeProcessorByName[name]
    }
}