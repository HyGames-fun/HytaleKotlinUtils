package `fun`.hygames.kotlinutils.codeInitialization.typeProcessor

import com.hypixel.hytale.server.core.plugin.JavaPlugin
import com.sun.source.util.Plugin
import `fun`.hygames.kotlinutils.codeInitialization.Register

interface TypeProcessor {
    fun run(register: Register, plugin: JavaPlugin, clazz: Class<*>)
}