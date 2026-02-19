package `fun`.hygames.kotlinutils.codeInitialization

import com.hypixel.hytale.common.plugin.PluginIdentifier
import com.hypixel.hytale.server.core.HytaleServer

object PluginsCounter {
    fun countPlugins(identifier: PluginIdentifier) : Int {
        var count = 0

        for (plugin in HytaleServer.get().pluginManager.plugins) {
            if (plugin.manifest.dependencies.containsKey(identifier)) count++
            else if (plugin.manifest.optionalDependencies.containsKey(identifier)) count++
        }

        return count
    }
}