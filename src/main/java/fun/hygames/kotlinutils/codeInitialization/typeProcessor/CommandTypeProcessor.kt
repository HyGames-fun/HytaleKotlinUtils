package `fun`.hygames.kotlinutils.codeInitialization.typeProcessor

import com.hypixel.hytale.server.core.command.system.AbstractCommand
import com.hypixel.hytale.server.core.plugin.JavaPlugin
import `fun`.hygames.kotlinutils.codeInitialization.Register
import `fun`.hygames.kotlinutils.inheritsFrom
import java.lang.reflect.Constructor

class CommandTypeProcessor : TypeProcessor {
    override fun run(register: Register, plugin: JavaPlugin, clazz: Class<*>) {
        if (!clazz.inheritsFrom(AbstractCommand::class.java)) {
            throw CommandTypeProcessorException(clazz)
        }

        val constructor: Constructor<*> = clazz.getDeclaredConstructor()
        constructor.setAccessible(true)
        val command = constructor.newInstance() as AbstractCommand

        plugin.commandRegistry.registerCommand(command)
    }
}

class CommandTypeProcessorException(clazz: Class<*>) : Exception(
    """"
Class "${clazz.name}" does not inherit from AbstractCommand!
Try rewrite class to 
    "class ${clazz.simpleName} : AbstractCommandCollection" (if command has sub-commands),
    "class ${clazz.simpleName} : AbstractPlayerCommand",
    "class ${clazz.simpleName} : AbstractAsyncCommand",
    "class ${clazz.simpleName} : AbstractCommand"
       """)