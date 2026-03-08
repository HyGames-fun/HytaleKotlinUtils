package `fun`.hygames.kotlinutils.command

import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import `fun`.hygames.kotlinutils.HytaleKotlinUtils
import `fun`.hygames.kotlinutils.codeInitialization.typeProcessor.TypeProcessors
import `fun`.hygames.kotlinutils.invoke
import `fun`.hygames.kotlinutils.sendMessage
import kotlin.collections.iterator

class HKUTypeProcessorsCommand : AbstractPlayerCommand {

    constructor() : super("typeProcessors", "List of all type processors")

    override fun execute(
        context: CommandContext,
        store: Store<EntityStore?>,
        ref: Ref<EntityStore?>,
        player: PlayerRef,
        world: World
    ) {
        val classes = HashMap<String, HashMap<String, List<String>>>() // Plugin -> TypeProcessor -> Classes

        for (entry in TypeProcessors.registeredByTypeProcessor){
            val plugin = TypeProcessors.getPlugin(entry.key)!!.name

            if (classes[plugin] == null) classes[plugin] = HashMap()
            classes[plugin]!![entry.key] = entry.value.stream().map { clazz ->
                return@map clazz.simpleName
            }.toList()
        }

        val string = StringBuilder()

        for (plugin in classes) {
            string.append("г  ${plugin.key}\n")
            val typeProcessor = plugin.value
            for (typeProcessor in typeProcessor) {
                string.append("|--  ${typeProcessor.key}\n")
                for (clazz in typeProcessor.value) {
                    string.append("|----  ${clazz}\n")
                }

            }
        }

        val result = string.toString()

        HytaleKotlinUtils.logger(result)

        player.sendMessage(result)
    }
}